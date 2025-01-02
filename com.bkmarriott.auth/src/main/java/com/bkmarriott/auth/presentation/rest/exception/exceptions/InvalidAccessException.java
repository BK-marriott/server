package com.bkmarriott.auth.presentation.rest.exception.exceptions;

public class InvalidAccessException extends RuntimeException {

    public InvalidAccessException(String message) {
        super(message);
    }
}
