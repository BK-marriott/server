package com.bkmarriott.auth.infrastucture.persistence.config;

import com.bkmarriott.auth.infrastucture.persistence.adapter.UserCommandAdapter;
import com.bkmarriott.auth.infrastucture.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class PersistenceConfig {

    @Bean
    public UserCommandAdapter userCommandAdapter(@Autowired UserRepository userRepository) {
        return new UserCommandAdapter(userRepository);
    }
}
