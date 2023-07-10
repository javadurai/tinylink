package com.github.jabadurai.tinylink.configs;

import com.github.jabadurai.tinylink.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    UserService userService;


    public JpaConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return () -> {
            return userService.currentLoggedInUserId();
        };
    }

}
