package com.picpaysimple.picpaysimple.infra;

import com.picpaysimple.picpaysimple.dto.ExceptionHandlerDTO;
import com.picpaysimple.picpaysimple.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threatDuplicationEntry(DataIntegrityViolationException exception) {
        ExceptionHandlerDTO newException = new ExceptionHandlerDTO("user already registered", "400");
        return ResponseEntity.badRequest().body(newException);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity threat404(UserNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ExternalServiceUnavailableException.class)
    public ResponseEntity threatExternalServiceUnavailable(ExternalServiceUnavailableException exception) {
        ExceptionHandlerDTO newException = new ExceptionHandlerDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(newException);
    }

    @ExceptionHandler(UnauthorizedTransactionException.class)
    public ResponseEntity handleUnauthorizedTransaction(UnauthorizedTransactionException exception) {
        ExceptionHandlerDTO error = new ExceptionHandlerDTO(exception.getMessage(), "403");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(MerchantTransactionNotAllowedException.class)
    public ResponseEntity handleMerchantTransactionNotAllowed(MerchantTransactionNotAllowedException exception) {
        ExceptionHandlerDTO error = new ExceptionHandlerDTO(exception.getMessage(), "400");
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity handleInsufficientBalance(InsufficientBalanceException exception) {
        ExceptionHandlerDTO error = new ExceptionHandlerDTO(exception.getMessage(), "400");
        return ResponseEntity.badRequest().body(error);
    }


}
