package com.bkmarriott.auth.presentation.rest.dto;

import com.bkmarriott.auth.domain.vo.UserForSingIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserSignIn {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String email;
        private String password;

        public UserForSingIn toDomain() {
            return new UserForSingIn(email, password);
        }
    }
}
