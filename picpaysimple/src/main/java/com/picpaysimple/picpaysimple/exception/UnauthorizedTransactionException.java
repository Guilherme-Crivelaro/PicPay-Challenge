package com.picpaysimple.picpaysimple.exception;

public class UnauthorizedTransactionException extends RuntimeException {
    public UnauthorizedTransactionException(String message) {
        super(message);
    }

    public UnauthorizedTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
