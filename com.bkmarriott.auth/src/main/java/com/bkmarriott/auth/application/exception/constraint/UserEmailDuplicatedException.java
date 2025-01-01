package com.bkmarriott.auth.application.exception.constraint;


import static com.bkmarriott.auth.application.exception.UserErrorMessage.USER_EMAIL_DUPLICATED;

public class UserEmailDuplicatedException extends ConstraintException {

    public UserEmailDuplicatedException() {
        super(USER_EMAIL_DUPLICATED.getMessage());
    }
}
