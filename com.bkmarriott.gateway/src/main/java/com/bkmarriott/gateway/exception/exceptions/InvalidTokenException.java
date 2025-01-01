package com.bkmarriott.gateway.exception.exceptions;

public class InvalidTokenException extends UnAuthenticatedException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
