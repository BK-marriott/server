package com.bkmarriott.auth.domain;

import static org.assertj.core.api.Assertions.*;

import com.bkmarriott.auth.domain.exception.ErrorMessage;
import com.bkmarriott.auth.domain.exception.SignInFailureException;
import com.bkmarriott.auth.domain.vo.Role;
import com.bkmarriott.auth.domain.vo.UserForSingIn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Domain] User Unit Test")
class UserTest {

    private final Long userId = 1L;
    private final String email = "test@test.com";
    private final String rowPassword = "test";
    private final Password password = Password.generateWithEncrypting(rowPassword);
    private final String name = "test";
    private final Role role = Role.CUSTOMER;

    @Test
    @DisplayName("[User 생성 성공 테스트] User 도메인 객체를 생성한다.")
    void createUser_successTest() {
        // Given & When
        User user = new User(userId, email, password, name, role);

        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(userId, user.getId()),
            () -> Assertions.assertEquals(email, user.getEmail()),
            () -> Assertions.assertEquals(name, user.getName()),
            () -> Assertions.assertEquals(role, user.getRole())
        );
    }

    @Test
    @DisplayName("[User 로그인 성공 테스트] 일치하는 데이터가 주어진 경우 도메인 객체를 반환한다.")
    void tryToSignIn_successTest() {
        // Given & When
        User user = new User(userId, email, password, name, role);
        UserForSingIn userForSingIn = new UserForSingIn(email, rowPassword);

        // Then
        Assertions.assertAll(
            () -> Assertions.assertDoesNotThrow(() -> {
                user.tryToSignIn(userForSingIn);
            })
        );
    }

    @Test
    @DisplayName("[User 로그인 실패 테스트] 이메일이 일치하지 않는 경우 예외를 발생한다")
    void tryToSignIn_failureTest_mismatchEmail() {
        // Given & When
        User user = new User(userId, email, password, name, role);
        UserForSingIn userForSingIn = new UserForSingIn("mismatch", rowPassword);

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> user.tryToSignIn(userForSingIn))
                .isInstanceOf(SignInFailureException.class)
                .hasMessage(ErrorMessage.LOGIN_FAILED.getMessage())
        );
    }

    @Test
    @DisplayName("[User 로그인 실패 테스트] 패스워드가 일치하지 않는 경우 예외를 발생한다.")
    void tryToSignIn_failureTest_mismatchPassword() {
        // Given & When
        User user = new User(userId, email, password, name, role);
        UserForSingIn userForSingIn = new UserForSingIn(email, "mismatch");

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> user.tryToSignIn(userForSingIn))
                .isInstanceOf(SignInFailureException.class)
                .hasMessage(ErrorMessage.LOGIN_FAILED.getMessage())
        );
    }
}