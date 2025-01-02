package com.bkmarriott.auth.presentation.rest.controller;

import com.bkmarriott.auth.application.service.UserService;
import com.bkmarriott.auth.presentation.rest.dto.UserSignIn;
import com.bkmarriott.auth.presentation.rest.dto.UserSignUp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
@RestController
public class AuthCommandController {

    private static final String HEADER_AUTH = "Authorization";

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody UserSignUp.Request request) {
        log.debug("[userCommandController] [signUp] request ::: {}", request);

        userService.signUp(request.toDomain());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Void> signIn(@Valid @RequestBody UserSignIn.Request request) {
        log.info("[userCommandController] [signIn] request ::: {}", request);

        String accessToken = userService.signIn(request.toDomain());
        return ResponseEntity.ok().header(HEADER_AUTH, accessToken).build();
    }
}
