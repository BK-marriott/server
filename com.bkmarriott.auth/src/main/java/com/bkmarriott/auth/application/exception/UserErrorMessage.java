package com.bkmarriott.auth.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorMessage {

    USER_EMAIL_DUPLICATED("중복된 이메일 입니다."),
    USER_NOT_EXIST("회원 정보가 존재하지 않습니다.");

    private final String message;
}
