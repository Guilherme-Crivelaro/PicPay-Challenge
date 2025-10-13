package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.domain.user.UserType;
import com.picpaysimple.picpaysimple.exception.InsufficientBalanceException;
import com.picpaysimple.picpaysimple.exception.MerchantTransactionNotAllowedException;
import com.picpaysimple.picpaysimple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServices {

    @Autowired
    private final UserRepository userRepository;

    public void validationTransaction(User sender, BigDecimal amount){
        if(sender.getUserType() == UserType.MERCHANT){
            throw new MerchantTransactionNotAllowedException("merchant type user is not authorized to perform transaction");
        }
        if(sender.getBalance().compareTo(amount) < 0){
            throw new InsufficientBalanceException("insufficient balance");
        }

    }


}
