package com.bkmarriott.auth.infrastucture.persistence.entity;

import com.bkmarriott.auth.domain.vo.Role;
import java.util.Objects;

public enum UserEntityRole {

    MASTER, MANAGER, STAFF, CUSTOMER;

    public Role toDomain() {
        for (Role role : Role.values()) {
            if (Objects.equals(role.name(), this.name())) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + this.name());
    }

    public static UserEntityRole fromDomain(Role role) {
        return UserEntityRole.valueOf(role.name());
    }
}
