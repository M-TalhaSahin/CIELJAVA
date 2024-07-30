package com.example.postgresdemo.exception;

public class InvalidLoyaltyOperationException extends RuntimeException {
    public InvalidLoyaltyOperationException(String message) {
        super(message);
    }
}
