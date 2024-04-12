package com.theono.authorization.service;

import com.theono.authorization.model.dto.UserDto;
import com.theono.authorization.model.entity.UserEntity;
import com.theono.authorization.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<UserDto> getUserInfo(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(UserDto.builder().userId(userEntity.getUserId()).build());
    }
}
