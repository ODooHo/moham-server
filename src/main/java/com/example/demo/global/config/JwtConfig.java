package com.example.demo.global.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;


    @Bean
    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
