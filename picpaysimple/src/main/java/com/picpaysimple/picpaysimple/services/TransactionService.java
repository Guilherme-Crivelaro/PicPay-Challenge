package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.transaction.Transaction;
import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.dto.TransactionDTO;
import com.picpaysimple.picpaysimple.exception.UnauthorizedTransactionException;
import com.picpaysimple.picpaysimple.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final NotificationService notificationService;
    @Autowired
    private final RestTemplate restTemplate;
    @Value("${https://util.devi.tools/api/v2/authorize}")
    private String url;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception{
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        userService.validationTransaction(sender, transactionDTO.value());

        Boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.value());
        if(!isAuthorized){
            throw new UnauthorizedTransactionException("Transaction Unauthorized");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.transactionRepository.save(transaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transaction completed successfully");
        this.notificationService.sendNotification(receiver, "Transaction received successfully");

        return new Transaction();
    }

    public Boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(url, Map.class);
        if(authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("message");
            return "authorization".equalsIgnoreCase(message);
        }else return false;
    }
}
