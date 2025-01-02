package com.bkmarriott.auth.infrastucture.persistence.adapter;

import com.bkmarriott.auth.domain.User;
import com.bkmarriott.auth.domain.vo.Role;
import com.bkmarriott.auth.domain.vo.UserForSignup;
import com.bkmarriott.auth.infrastucture.persistence.config.RepositoryTest;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("[Infrastructure] User Repository Unit Test")
@RepositoryTest
class UserCommandAdapterTest {

    @Autowired
    private UserCommandAdapter userCommandAdapter;

    @Test
    @DisplayName("[회원 등록 성공 테스트] 회원 등록 도메인이 주어졌을 시, 회원을 등록한 뒤 도메인 객체를 반환한다.")
    void generateUser_successTest() {
        // Given
        UserForSignup userForSignup = generateTestUser();
        // When
        User actual = userCommandAdapter.generateUser(userForSignup);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertNotNull(actual.getId()),
            () -> Assertions.assertEquals(userForSignup.getEmail(), actual.getEmail()),
            () -> Assertions.assertEquals(userForSignup.getName(), actual.getName()),
            () -> Assertions.assertEquals(Role.CUSTOMER, actual.getRole())
        );
    }

    private UserForSignup generateTestUser() {
        String email = "email@email";
        String rowPassword = "password";
        String name = "name";

        return UserForSignup.of(email, rowPassword, name);
    }

    @Test
    @DisplayName("[회원 조회 성공 테스트] 이메일로 회원을 조회한 뒤 Optional 객체에 담아 반환한다.")
    void findByEmail_successTest() {
        // Given
        UserForSignup userForSignup = generateTestUser();
        User user = userCommandAdapter.generateUser(userForSignup);
        // When
        Optional<User> optionalUser = userCommandAdapter.findByEmail(user.getEmail());
        // Then
        Assertions.assertAll(
            () -> Assertions.assertTrue(optionalUser.isPresent()),
            () -> Assertions.assertEquals(user.getEmail(), optionalUser.get().getEmail())
        );
    }

    @Test
    @DisplayName("[회원 조회 성공 테스트] 일치하는 이메일이 존재하지 않는 경우 빈 Optional 객체를 반환한다.")
    void findByEmail_successTest_notMatchedEmail() {
        // Given & When
        Optional<User> optionalUser = userCommandAdapter.findByEmail("invalid@email");
        // Then
        Assertions.assertAll(
            () -> Assertions.assertFalse(optionalUser.isPresent())
        );
    }
}