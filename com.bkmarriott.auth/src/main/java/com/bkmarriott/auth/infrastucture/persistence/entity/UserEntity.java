package com.bkmarriott.auth.infrastucture.persistence.entity;

import com.bkmarriott.auth.domain.Password;
import com.bkmarriott.auth.domain.User;
import com.bkmarriott.auth.domain.vo.UserForSignup;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "m_user")
@Entity(name = "user")
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserEntityRole role;

    public UserEntity(Long id, String email, String password, String name, UserEntityRole role) {
        super.createdBySystem();
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public User toDomain() {
        return new User(id, email, Password.valueOf(password), name, role.toDomain());
    }

    public static UserEntity from(UserForSignup user) {
        return new UserEntity(
            null,
            user.getEmail(),
            user.getPassword(),
            user.getName(),
            UserEntityRole.fromDomain(user.getRole())
        );
    }
}
