package com.picpaysimple.picpaysimple.exception;

public class MerchantTransactionNotAllowedException extends RuntimeException {
    public MerchantTransactionNotAllowedException(String message) {
        super(message);
    }

    public MerchantTransactionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
