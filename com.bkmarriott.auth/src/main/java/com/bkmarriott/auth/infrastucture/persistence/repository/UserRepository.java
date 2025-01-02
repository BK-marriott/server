package com.bkmarriott.auth.infrastucture.persistence.repository;

import com.bkmarriott.auth.infrastucture.persistence.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
