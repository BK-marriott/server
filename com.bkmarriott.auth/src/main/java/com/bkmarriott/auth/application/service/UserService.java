package com.bkmarriott.auth.application.service;

import com.bkmarriott.auth.application.exception.constraint.UserEmailDuplicatedException;
import com.bkmarriott.auth.application.exception.constraint.UserNotFoundException;
import com.bkmarriott.auth.domain.User;
import com.bkmarriott.auth.domain.vo.UserForSignup;
import com.bkmarriott.auth.domain.vo.UserForSingIn;
import com.bkmarriott.auth.infrastucture.persistence.adapter.UserCommandAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserCommandAdapter userCommandAdapter;
    private final AccessTokenService tokenService;

    public User signUp(UserForSignup userForSignup) {
        log.debug("[UserService] [signUp] email ::: {}, name ::: {}", userForSignup.getEmail(), userForSignup.getName());

        userCommandAdapter.findByEmail(userForSignup.getEmail()).ifPresent(user -> {
            throw new UserEmailDuplicatedException();
        });
        return userCommandAdapter.generateUser(userForSignup);
    }

    public String signIn(UserForSingIn userForSingIn) {
        log.info("[UserService] [SignIn] email ::: {}, password ::: {}", userForSingIn.email(), userForSingIn.password());
        User user = userCommandAdapter.findByEmail(userForSingIn.email())
            .orElseThrow(UserNotFoundException::new);

        user = user.tryToSignIn(userForSingIn);
        return tokenService.generateAccessToken(user);
    }
}
