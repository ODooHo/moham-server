package com.example.demo.api.exception.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode {
    NOT_EXISTS_COOKIE(HttpStatus.BAD_REQUEST,"Cookie not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IO or Token Parsing Error"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "Access Token expired"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.BAD_REQUEST,"Refresh Token expired"),
    NOT_EXISTS_REFRESH_TOKEN(HttpStatus.BAD_REQUEST,"Refresh Token not exists"),
    NOT_EXISTS_REDIS_REFRESH_TOKEN(HttpStatus.NOT_FOUND,"Not exists Redis refresh token"),
    WRONG_PROVIDER(HttpStatus.BAD_REQUEST,"Wrong provider, exists provider is google, kakao"),
    ;

    private final HttpStatus status;
    private final String message;
}

