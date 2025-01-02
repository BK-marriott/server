package com.bkmarriott.auth.domain.vo;

import com.bkmarriott.auth.domain.Password;

public class UserForSignup {

    private final String email;
    private final Password password;
    private final String name;
    private final Role role;

    private UserForSignup(String email, Password password, String name, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public static UserForSignup of(String email, String rowPassword, String name) {
        Password encryptedPassword = Password.generateWithEncrypting(rowPassword);
        return new UserForSignup(email, encryptedPassword, name, Role.CUSTOMER);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password.getEncryptedPassword();
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
}
