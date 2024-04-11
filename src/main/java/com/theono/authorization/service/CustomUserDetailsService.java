package com.theono.authorization.service;

import com.theono.authorization.model.dto.CustomUserDetails;
import com.theono.authorization.model.entity.UserEntity;
import com.theono.authorization.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUserId(username);
        if (userEntity != null) {
            return new CustomUserDetails(userEntity);
        }

        return null;
    }
}
