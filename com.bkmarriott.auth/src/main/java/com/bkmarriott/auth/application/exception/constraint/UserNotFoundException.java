package com.bkmarriott.auth.application.exception.constraint;


import static com.bkmarriott.auth.application.exception.UserErrorMessage.USER_NOT_EXIST;

public class UserNotFoundException extends ConstraintException {

    public UserNotFoundException() {
        super(USER_NOT_EXIST.getMessage());
    }
}
