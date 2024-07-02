package com.example.demo.global.auth.oauth_oidc.provider.impl;

import com.example.demo.global.auth.oauth_oidc.KakaoAuthClient;
import com.example.demo.global.auth.oauth_oidc.provider.OidcProvider;
import com.example.demo.global.auth.oauth_oidc.provider.PublicKeyProvider;
import com.example.demo.global.auth.oauth_oidc.provider.UserInfoDto;
import com.example.demo.global.auth.oauth_oidc.vo.OidcPublicKeyList;
import com.example.demo.global.config.util.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.PublicKey;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoAuthProvider implements OidcProvider {

    private final KakaoAuthClient kakaoAuthClient;
    private final PublicKeyProvider publicKeyProvider;
    private final JwtProvider jwtProvider;

    @Override
    public UserInfoDto getInfo(String idToken) {
        final OidcPublicKeyList oidcPublicKeyList = kakaoAuthClient.getPublicKeys();
        final PublicKey publicKey = publicKeyProvider.generatePublicKey(parseHeaders(idToken), oidcPublicKeyList);
        Claims claims = jwtProvider.parseClaims(idToken, publicKey);
        String providerId = claims.getSubject();
        String email = claims.get("email").toString();

        return UserInfoDto.of(providerId,email);
    }
}