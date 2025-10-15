package com.picpaysimple.picpaysimple.controllers;

import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.dto.UserDTO;
import com.picpaysimple.picpaysimple.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> newUser = this.userService.getAllUsers();
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
