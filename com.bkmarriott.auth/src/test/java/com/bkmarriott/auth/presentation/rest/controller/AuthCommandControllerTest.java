package com.bkmarriott.auth.presentation.rest.controller;

import com.bkmarriott.auth.application.service.UserService;
import com.bkmarriott.auth.domain.Password;
import com.bkmarriott.auth.domain.User;
import com.bkmarriott.auth.domain.vo.UserForSignup;
import com.bkmarriott.auth.domain.vo.UserForSingIn;
import com.bkmarriott.auth.presentation.rest.dto.UserSignIn;
import com.bkmarriott.auth.presentation.rest.dto.UserSignUp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("[Presentation] Auth Controller Unit Test")
@WebMvcTest(AuthCommandController.class)
class AuthCommandControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[회원 등록 성공 테스트] 회원을 등록한다.")
    void signUp_successTest() throws Exception {
        // Given
        String email = "email";
        String rowPassword = "password";
        String name = "name";

        String requestUrl = "/api/v1/auth/sign-up";
        String requestBody = objectMapper.writeValueAsString(
            new UserSignUp.Request(email, rowPassword, name)
        );

        Mockito.when(userService.signUp(ArgumentMatchers.any(UserForSignup.class)))
            .then(invocation -> {
                UserForSignup user = invocation.getArgument(0);
                Password password = Password.generateWithEncrypting(rowPassword);

                return new User(1L, user.getEmail(), password, user.getName(), user.getRole());
            });

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("[회원 이메일 조회 성공 테스트] 일치하는 이메일이 존재할 시 도메인 객체를 반환한다.")
    void signIn_successTest() throws Exception {
        // Given
        String email = "email";
        String rowPassword = "password";

        String requestUrl = "/api/v1/auth/sign-in";
        String requestBody = objectMapper.writeValueAsString(
            new UserSignIn.Request(email, rowPassword)
        );

        Mockito.when(userService.signIn(ArgumentMatchers.any(UserForSingIn.class)))
            .thenReturn("accessToken");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.header().exists("Authorization"));
    }
}