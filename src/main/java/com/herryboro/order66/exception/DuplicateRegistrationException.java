package com.herryboro.order66.exception;

public class DuplicateRegistrationException extends RuntimeException {
    public DuplicateRegistrationException(String message) {
        super(message);
    }
}
