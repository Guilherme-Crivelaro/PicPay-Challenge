package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.domain.user.UserType;
import com.picpaysimple.picpaysimple.dto.UserDTO;
import com.picpaysimple.picpaysimple.exception.InsufficientBalanceException;
import com.picpaysimple.picpaysimple.exception.MerchantTransactionNotAllowedException;
import com.picpaysimple.picpaysimple.exception.UserNotFoundException;
import com.picpaysimple.picpaysimple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

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

    public User findUserById(Long id){
        return this.userRepository.findUserById(id).orElseThrow(()
                -> new UserNotFoundException("User not found"));
    }

    public User createUser(UserDTO userDTO){
        User newUser = new User(userDTO);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

}
