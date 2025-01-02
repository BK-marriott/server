package com.bkmarriott.auth.domain;

import com.bkmarriott.auth.domain.encryptor.Encryptor;
import com.bkmarriott.auth.domain.encryptor.PasswordEncryptor;
import com.bkmarriott.auth.domain.exception.PasswordConstraintException;
import java.util.Objects;

public class Password {

    private static final Encryptor ENCRYPTOR = PasswordEncryptor.getInstance();

    private final String encryptedPassword;

    private Password(String encryptedPassword) {
        validatePassword(encryptedPassword);
        this.encryptedPassword = encryptedPassword;
    }

    private static void validatePassword(String encryptedPassword) {
        if (encryptedPassword.isBlank()) {
            throw new PasswordConstraintException();
        }
    }

    public static Password valueOf(String encryptedPassword) {
        return new Password(encryptedPassword);
    }

    public static Password generateWithEncrypting(String rowPassword) {
        validatePassword(rowPassword);
        String encryptedPassword = ENCRYPTOR.encrypt(rowPassword);
        return new Password(encryptedPassword);
    }

    public boolean isMatch(String rowPassword) {;
        String encryptedPassword = ENCRYPTOR.encrypt(rowPassword);
        return Objects.equals(encryptedPassword, this.encryptedPassword);
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }
}
