package com.bkmarriott.auth.presentation.rest.dto;

import com.bkmarriott.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {

    private Long userId;
    private String email;
    private String name;
    private String role;

    public static UserDetailResponse from(User user) {
        return new UserDetailResponse(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getRole().name()
        );
    }
}