package com.bkmarriott.auth.infrastucture.persistence.adapter;

import com.bkmarriott.auth.domain.User;
import com.bkmarriott.auth.domain.vo.UserForSignup;
import com.bkmarriott.auth.infrastucture.persistence.entity.UserEntity;
import com.bkmarriott.auth.infrastucture.persistence.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserCommandAdapter {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(UserEntity::toDomain)
            .or(Optional::empty);
    }

    public User generateUser(UserForSignup userForSignup) {
        UserEntity userEntity = UserEntity.from(userForSignup);
        return userRepository.save(userEntity).toDomain();
    }
}
