package com.bkmarriott.auth.presentation.rest.dto;

import com.bkmarriott.auth.domain.vo.UserForSignup;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserSignUp {

    @Getter
    @NoArgsConstructor
    public static class Request {

        private String email;
        private String password;
        private String name;

        public UserForSignup toDomain() {
            return UserForSignup.of(email, password, name);
        }
    }
}
