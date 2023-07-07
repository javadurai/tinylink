package com.github.jabadurai.go.urlshortner.config;

import com.github.jabadurai.go.urlshortner.entities.CustomUserDetails;
import com.github.jabadurai.go.urlshortner.repositories.UserRepository;
import com.sun.security.auth.UserPrincipal;
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

    @Autowired
    UserRepository userRepository;

    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return null;
            }

            if (authentication.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                return Optional.ofNullable(userDetails.getUser().getId());
            }

//            // Assuming that your principal's ID is of type Integer
//            Optional<String> username = Optional.of(((UserPrincipal) authentication.getPrincipal()).getName());
//            if(username.isPresent()){
//                return Optional.ofNullable((userRepository.findByUsername(username.get())).getId());
//            } else {
//                return null;
//            }
            return null;
        };
    }

}
