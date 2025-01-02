package com.bkmarriott.auth.domain.exception;

public class PasswordConstraintException extends UserAuthException {

    public PasswordConstraintException() {
        super(ErrorMessage.CREATE_PASSWORD_FAILED);
    }
}
