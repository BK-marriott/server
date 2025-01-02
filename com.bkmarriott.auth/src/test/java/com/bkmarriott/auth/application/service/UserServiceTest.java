package com.bkmarriott.auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bkmarriott.auth.application.exception.UserErrorMessage;
import com.bkmarriott.auth.application.exception.constraint.UserEmailDuplicatedException;
import com.bkmarriott.auth.application.exception.constraint.UserNotFoundException;
import com.bkmarriott.auth.domain.Password;
import com.bkmarriott.auth.domain.User;
import com.bkmarriott.auth.domain.exception.ErrorMessage;
import com.bkmarriott.auth.domain.exception.SignInFailureException;
import com.bkmarriott.auth.domain.vo.Role;
import com.bkmarriott.auth.domain.vo.UserForSignup;
import com.bkmarriott.auth.domain.vo.UserForSingIn;
import com.bkmarriott.auth.infrastucture.persistence.adapter.UserCommandAdapter;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application] UserService Unit Test")
class UserServiceTest {

    @InjectMocks private UserService userService;
    @Mock private UserCommandAdapter userCommandAdapter;
    @Mock private AccessTokenService accessTokenService;

    @Test
    @DisplayName("[회원 가입 성공 테스트] 회원 가입 후 회원 정보를 반환한다.")
    void signUp_successTest() {
        // Given
        UserForSignup signup = UserForSignup.of("email", "password", "name");

        Mockito.when(userCommandAdapter.findByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());

        Mockito.when(userCommandAdapter.generateUser(ArgumentMatchers.any(UserForSignup.class)))
            .thenReturn(Mockito.mock(User.class));
        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertDoesNotThrow(() -> userService.signUp(signup))
        );
    }

    @Test
    @DisplayName("[회원 가입 실패 테스트] 이메일이 중복되는 경우 예외를 발생한다.")
    void signUp_failureTest_duplicateEmail() {
        // Given
        UserForSignup signup = UserForSignup.of("email", "password", "name");

        Mockito.when(userCommandAdapter.findByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(Mockito.mock(User.class)));

        // When & Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> userService.signUp(signup))
                .isInstanceOf(UserEmailDuplicatedException.class)
                .hasMessage(UserErrorMessage.USER_EMAIL_DUPLICATED.getMessage())
        );
    }

    @Test
    @DisplayName("[로그인 성공 테스트] 로그인 시도 후 토큰을 반환한다.")
    void signIn_successTest() {
        // Given
        String email = "email";
        String password = "password";
        User mockUser = new User(0L, email, Password.generateWithEncrypting(password), "name", Role.CUSTOMER);
        UserForSingIn userForSingIn = new UserForSingIn(email, password);

        Mockito.when(userCommandAdapter.findByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(mockUser));

        Mockito.when(accessTokenService.generateAccessToken(ArgumentMatchers.any(User.class)))
            .thenReturn("access_token");

        // When & Then
        Assertions.assertAll(
            () -> Assertions.assertDoesNotThrow(() -> userService.signIn(userForSingIn))
        );
    }

    @Test
    @DisplayName("[로그인 실패 테스트] 존재하지 않는 이메일인 경우 예외를 발생한다.")
    void signIn_failureTest_invalidEmail() {
        // Given
        UserForSingIn userForSingIn = new UserForSingIn("email", "password");
        Mockito.when(userCommandAdapter.findByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());

        // When & Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> userService.signIn(userForSingIn))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(UserErrorMessage.USER_NOT_EXIST.getMessage())
        );
    }

    @Test
    @DisplayName("[로그인 실패 테스트] 일치하지 않는 패스워드를 전달한 경우 예외를 발생한다.")
    void signIn_failureTest_invalidPassword() {
        // Given
        UserForSingIn userForSingIn = new UserForSingIn("email", "invalid");
        User user = new User(1L, "email", Password.generateWithEncrypting("password"), "name",
            Role.CUSTOMER);

        Mockito.when(userCommandAdapter.findByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(user));

        // When & Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> userService.signIn(userForSingIn))
                .isInstanceOf(SignInFailureException.class)
                .hasMessage(ErrorMessage.LOGIN_FAILED.getMessage())
        );
    }
}