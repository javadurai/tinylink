package com.github.jabadurai.go.urlshortner.config;

import com.github.jabadurai.go.urlshortner.entities.CustomUserDetails;
import com.github.jabadurai.go.urlshortner.entities.User;
import com.github.jabadurai.go.urlshortner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return null;
        }
        return Optional.ofNullable(((CustomUserDetails) authentication.getPrincipal()).getUsername());
    }
}
