package com.bkmarriott.auth.presentation.rest.dto;

import com.bkmarriott.auth.domain.vo.UserForSignup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserSignUp {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String email;
        private String password;
        private String name;

        public UserForSignup toDomain() {
            return UserForSignup.of(email, password, name);
        }
    }
}
