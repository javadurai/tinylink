package com.github.jabadurai.go.urlshortner.service;

import com.github.jabadurai.go.urlshortner.entities.CustomUserDetails;
import com.github.jabadurai.go.urlshortner.entities.User;
import com.github.jabadurai.go.urlshortner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUserid(username);
        System.out.println(user);
        if(user != null){
            return new CustomUserDetails(user);
        }
        throw new UsernameNotFoundException("Couldn't find user");
    }
}
