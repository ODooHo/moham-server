package com.example.demo.api.user.service;

import com.example.demo.api.exception.core.CoreErrorCode;
import com.example.demo.api.exception.core.CoreException;
import com.example.demo.api.user.dto.UserDto;
import com.example.demo.api.user.dto.response.InfoResponse;
import com.example.demo.api.user.entity.UserEntity;
import com.example.demo.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public InfoResponse info(String email){
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new CoreException(CoreErrorCode.USER_NOT_FOUND, String.format("email is %s",email))
        );

        return InfoResponse.of(
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getNickname()
        );
    }
}
