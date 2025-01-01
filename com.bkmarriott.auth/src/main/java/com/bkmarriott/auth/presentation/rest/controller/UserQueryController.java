package com.bkmarriott.auth.presentation.rest.controller;

import com.bkmarriott.auth.domain.User;
import com.bkmarriott.auth.infrastucture.persistence.adapter.UserQueryAdapter;
import com.bkmarriott.auth.presentation.rest.dto.UserDetailResponse;
import com.bkmarriott.auth.presentation.rest.exception.exceptions.ResourceNotFoundException;
import com.bkmarriott.auth.presentation.rest.util.reponse.ApiResponse;
import com.bkmarriott.auth.presentation.rest.util.reponse.ApiResponse.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/users")
@RestController
public class UserQueryController {

    private final UserQueryAdapter userQueryAdapter;

    @GetMapping("/{userId}")
    public ResponseEntity<Success<UserDetailResponse>> findUser(
        @PathVariable("userId") Long userId
    ) {
        User user = userQueryAdapter.findById(userId).orElseThrow(() ->
            new ResourceNotFoundException("존재하지 않는 회원입니다."));

        UserDetailResponse response = UserDetailResponse.from(user);
        return ApiResponse.success(response, HttpStatus.OK);
    }

}
