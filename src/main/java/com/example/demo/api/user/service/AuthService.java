package com.example.demo.api.user.service;

import com.example.demo.api.exception.auth.AuthErrorCode;
import com.example.demo.api.exception.auth.AuthException;
import com.example.demo.api.exception.core.CoreErrorCode;
import com.example.demo.api.exception.core.CoreException;
import com.example.demo.api.user.UserRole;
import com.example.demo.api.user.dto.request.SignInRequest;
import com.example.demo.api.user.dto.request.SignUpRequest;
import com.example.demo.api.user.dto.response.JwtResponse;
import com.example.demo.api.user.entity.UserEntity;
import com.example.demo.api.user.repository.UserRepository;
import com.example.demo.global.auth.oauth_oidc.JsonWebToken;
import com.example.demo.global.auth.oauth_oidc.factory.OidcProviderFactory;
import com.example.demo.global.auth.oauth_oidc.provider.UserInfoDto;
import com.example.demo.global.config.util.JwtProvider;
import com.example.demo.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OidcProviderFactory oidcProviderFactory;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;



    @Value("${jwt.access-expired-time}")
    Long accessExpiredTime;

    @Value("${jwt.refresh-expired-time}")
    Long refreshExpiredTime;


    public JwtResponse signUp(SignUpRequest dto) {
        UserInfoDto userInfo = oidcProviderFactory.getProviderId(dto.getProvider(), dto.getIdToken());

        UserEntity user = UserEntity.of(dto.getProvider(), userInfo.getProviderId(), userInfo.getEmail(), dto.getUsername(), dto.getNickname(), UserRole.USER);

        userRepository.save(user);

        JwtResponse jwt = JwtResponse.from(jwtProvider.generateToken(user));

        redisService.setValues(user.getEmail(),jwt.getRefreshToken(), Duration.ofMillis(refreshExpiredTime));
        return jwt;
    }

    public JwtResponse signIn(SignInRequest dto) {

        UserInfoDto userInfo = oidcProviderFactory.getProviderId(dto.getProvider(), dto.getIdToken());

        UserEntity user = userRepository.findByProviderId(userInfo.getProviderId()).orElseThrow(
                () -> new CoreException(CoreErrorCode.USER_NOT_FOUND)
        );

        return JwtResponse.from(jwtProvider.generateToken(user));
    }

    public JwtResponse reissue(String refreshToken){

        UserEntity user = jwtProvider.getUser(refreshToken);


        String key = user.getEmail();
        String redisRefreshToken = redisService.getValues(key);

        if(redisService.checkExistsValue(redisRefreshToken)){
            throw new AuthException(AuthErrorCode.NOT_EXISTS_REDIS_REFRESH_TOKEN);
        }

        JwtResponse jwt = JwtResponse.from(jwtProvider.generateToken(user));

        redisService.setValues(key,jwt.getRefreshToken(),Duration.ofMillis(refreshExpiredTime));

        return jwt;
    }


    public void logout(String refreshToken){
        UserEntity user = jwtProvider.getUser(refreshToken);
        String key = user.getEmail();

        if(redisService.getValues(key) == null){
            throw new AuthException(AuthErrorCode.NOT_EXISTS_REDIS_REFRESH_TOKEN);
        }

        redisService.deleteValues(key);
    }

}
