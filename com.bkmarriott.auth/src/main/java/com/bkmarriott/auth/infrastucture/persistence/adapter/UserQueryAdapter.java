package com.bkmarriott.auth.infrastucture.persistence.adapter;

import com.bkmarriott.auth.domain.User;
import com.bkmarriott.auth.infrastucture.persistence.entity.UserEntity;
import com.bkmarriott.auth.infrastucture.persistence.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserQueryAdapter {

    private final UserRepository userRepository;

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId)
            .map(UserEntity::toDomain)
            .or(Optional::empty);
    }
}
