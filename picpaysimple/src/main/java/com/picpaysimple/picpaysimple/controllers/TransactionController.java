package com.picpaysimple.picpaysimple.controllers;

import com.picpaysimple.picpaysimple.domain.transaction.Transaction;
import com.picpaysimple.picpaysimple.dto.TransactionDTO;
import com.picpaysimple.picpaysimple.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception{
            Transaction newTrasanction = this.transactionService.createTransaction(transactionDTO);
            return new ResponseEntity<>(newTrasanction, HttpStatus.OK);

    }
}
