package com.github.jabadurai.tinylink.config;

import com.github.jabadurai.tinylink.entities.CustomUserDetails;
import com.github.jabadurai.tinylink.repositories.UserRepository;
import com.github.jabadurai.tinylink.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    UserDetailsServiceImpl userDetailsServiceImpl;


    public JpaConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return () -> {
            return userDetailsServiceImpl.currentLoggedInUserId();
        };
    }

}
