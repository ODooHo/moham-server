package com.example.demo.global.auth.oauth_oidc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JsonWebToken {
    private String accessToken;
    private String refreshToken;
    private String grantType;

    public static JsonWebToken of(String accessToken, String refreshToken, String grantType){
        return new JsonWebToken(accessToken, refreshToken, grantType);
    }
}
