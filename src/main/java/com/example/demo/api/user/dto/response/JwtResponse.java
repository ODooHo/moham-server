package com.example.demo.api.user.dto.response;

import com.example.demo.global.auth.oauth_oidc.JsonWebToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String grantType;

    public static JwtResponse from(JsonWebToken jwt) {
        return new JwtResponse(jwt.getAccessToken(), jwt.getRefreshToken(), jwt.getGrantType());
    }
}

