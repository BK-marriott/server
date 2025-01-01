package com.bkmarriott.gateway.exception.exceptions;

public class UnAuthenticatedException extends RuntimeException {

    public UnAuthenticatedException(String message) {
        super(message);
    }
}
