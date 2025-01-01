package com.bkmarriott.gateway.exception;

import com.bkmarriott.gateway.exception.ErrorResponse.Error;
import com.bkmarriott.gateway.exception.exceptions.UnAuthenticatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FilterExceptionHandler {

    @ExceptionHandler(UnAuthenticatedException.class)
    public ResponseEntity<Error> authExceptionHandle(UnAuthenticatedException exception) {
        log.error("[FilterExceptionHandler] [authExceptionHandle] exception ::: ", exception);
        return ErrorResponse.error(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exceptionHandler(Exception exception) {
        log.error("[FilterExceptionHandler] [exceptionHandler] exception ::: ", exception);
        return ErrorResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}
