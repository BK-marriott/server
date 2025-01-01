package com.bkmarriott.gateway.exception.messages;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InvalidTokenErrorMessage {

    TOKEN_INVALID("유효하지 않은 토큰 정보입니다."),
    TOKEN_EXPIRED("기한이 만료된 토큰 입니다.");

    private final String message;

    public String message() {
        return message;
    }
}
