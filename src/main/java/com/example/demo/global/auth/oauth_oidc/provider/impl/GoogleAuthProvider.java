package com.example.demo.global.auth.oauth_oidc.provider.impl;

import com.example.demo.api.exception.auth.AuthErrorCode;
import com.example.demo.api.exception.auth.AuthException;
import com.example.demo.global.auth.oauth_oidc.provider.OidcProvider;
import com.example.demo.global.auth.oauth_oidc.provider.UserInfoDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleAuthProvider implements OidcProvider {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    @Override
    public UserInfoDto getInfo(String idToken) {

        String providerId = getGoogleIdToken(idToken).getPayload().getSubject();
        String email = getGoogleIdToken(idToken).getPayload().getEmail();// 이메일 반환할거면 이거 쓰고

        return UserInfoDto.of(providerId,email);
    }




    private GoogleIdToken getGoogleIdToken(String idToken) {
        try {
            GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(idToken);
            if (googleIdToken == null) {
                throw new AuthException(AuthErrorCode.INTERNAL_SERVER_ERROR);
            }

            return googleIdToken;
        } catch (GeneralSecurityException | IOException e) {
            throw new AuthException(AuthErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}