package com.example.demo.global.config.util;

import com.example.demo.api.exception.auth.AuthErrorCode;
import com.example.demo.api.exception.auth.AuthException;
import com.example.demo.api.user.entity.UserEntity;
import com.example.demo.global.auth.oauth_oidc.JsonWebToken;
import com.example.demo.global.auth.oauth_oidc.CustomOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.PublicKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;

    private final Long accessExpiredTime;
    private final Long refreshExpiredTime;
    private final String grantType;
    private final String userKey;

    public JwtProvider(
            Key key,
            @Value("${jwt.access-expired-time}")
            Long accessExpiredTime,
            @Value("${jwt.refresh-expired-time}")
            Long refreshExpiredTime,
            @Value("${jwt.grant-type}")
            String grantType,
            @Value(("${jwt.userKey}"))
            String userKey
    ) {
        this.key = key;
        this.accessExpiredTime = accessExpiredTime;
        this.refreshExpiredTime = refreshExpiredTime;
        this.grantType = grantType;
        this.userKey = userKey;
    }

    public JsonWebToken generateToken(UserEntity user) {
        final String accessToken = Jwts.builder()
                .claim(userKey, user)
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiredTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        final String refreshToken = Jwts.builder()
                .claim(userKey, user)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiredTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return JsonWebToken.of(accessToken,refreshToken,grantType);
    }

    public Authentication getAuthentication(String token) {
        UserEntity user = getUser(token);
        CustomOAuth2User oAuth2User = new CustomOAuth2User(user);

        return new UsernamePasswordAuthenticationToken(oAuth2User, "", oAuth2User.getAuthorities());
    }

    public UserEntity getUser(String token) {
        Claims claims = parseClaims(token);

        if (claims.get(userKey) == null) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }

        return new ObjectMapper().convertValue(claims.get(userKey), UserEntity.class);
    }

    public long getRefreshExpiredTime() {
        return this.refreshExpiredTime;
    }

    public Claims parseClaims(String token) {
        try {
            log.info(token);
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    public Claims parseClaims(String token, PublicKey publicKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }
}