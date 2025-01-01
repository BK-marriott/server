package com.bkmarriott.gateway.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {

    public static ResponseEntity<Error> error(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(Error.of(message));
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Error {

        private String message;

        public static Error of(@NonNull String errorMessage) {
            return new Error(errorMessage);
        }
    }
}
