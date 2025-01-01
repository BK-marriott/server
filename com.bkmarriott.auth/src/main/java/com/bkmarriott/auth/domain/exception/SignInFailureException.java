package com.bkmarriott.auth.domain.exception;

import static com.bkmarriott.auth.domain.exception.ErrorMessage.LOGIN_FAILED;

public class SignInFailureException extends UserAuthException {

    public SignInFailureException() {
        super(LOGIN_FAILED);
    }
}