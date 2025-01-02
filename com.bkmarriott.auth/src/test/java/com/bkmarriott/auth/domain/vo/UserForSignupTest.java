package com.bkmarriott.auth.domain.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("[Domain] UserForSignup Unit Test")
class UserForSignupTest {

    @ParameterizedTest
    @ValueSource(strings = {"test1", "password", "qwer1234!@#$"})
    @DisplayName("[회원 회원가입 데이터 생성 성공 테스트] 회원 가입을 위해 필요한 도메인 객체를 생성한다.")
    void createUserForSignUp_successTest(String rowPassword) {
        // Given & When
        String email = "test@test.com";
        String name = "test";
        UserForSignup userForSignup = UserForSignup.of(email, rowPassword, name);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertNotEquals(rowPassword, userForSignup.getPassword()),
            () -> Assertions.assertEquals(email, userForSignup.getEmail()),
            () -> Assertions.assertEquals(name, userForSignup.getName()),
            () -> Assertions.assertEquals(Role.CUSTOMER, userForSignup.getRole())
        );
    }
}