package com.bkmarriott.auth.application.service;

import static org.junit.jupiter.api.Assertions.*;

import com.bkmarriott.auth.application.util.dategnerator.AccessTokenDateGenerator;
import com.bkmarriott.auth.domain.Password;
import com.bkmarriott.auth.domain.User;
import com.bkmarriott.auth.domain.vo.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("[application] AccessTokenService Unit Test")
class AccessTokenServiceTest {

    AccessTokenService accessTokenService;

    @BeforeEach
    void setUp() {
        accessTokenService = new AccessTokenService(
            new AccessTokenDateGenerator(),
            1000L,
            "401b09eab3c013d4cwq4922bfk02bec8fd5318192b0a75f201d8b3727429080fb337591abd3e44453b954555b7a0812e1081c39b740293f765eae731f5a65ed1"
        );
    }

    @Test
    @DisplayName("[토큰 생성 성공 테스트] 유저 정보로 토큰을 생성한다.")
    void generateAccessToken_successTest() {
        // Given
        User user = new User(1L, "email", Password.generateWithEncrypting("password"), "name", Role.CUSTOMER);
        // When
        String token = accessTokenService.generateAccessToken(user);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertTrue(token.startsWith("Bearer"))
        );
    }
}