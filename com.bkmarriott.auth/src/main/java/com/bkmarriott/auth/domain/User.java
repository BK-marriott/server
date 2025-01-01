package com.bkmarriott.auth.domain;

import com.bkmarriott.auth.domain.exception.SignInFailureException;
import com.bkmarriott.auth.domain.vo.Role;
import com.bkmarriott.auth.domain.vo.UserForSingIn;
import java.util.Objects;

public class User {

    private Long id;
    private String email;
    private Password password;
    private String name;
    private Role role;

    public User(Long id, String email, Password password, String name, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public User tryToSignIn(UserForSingIn singIn) {
        if (isMatchingEmail(singIn.email()) && isMatchingPassword(singIn.password())) {
            return this;
        }
        throw new SignInFailureException();
    }

    private boolean isMatchingEmail(String email) {
        return Objects.equals(this.email, email);
    }

    private boolean isMatchingPassword(String rowPassword) {
        return password.isMatch(rowPassword);
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}
