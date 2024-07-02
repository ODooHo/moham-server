package com.example.demo.global.auth.oauth_oidc.provider;

import com.example.demo.api.exception.auth.AuthErrorCode;
import com.example.demo.api.exception.auth.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;


public interface OidcProvider {

    default Map<String, String> parseHeaders(String token){
        String header = token.split("\\.")[0];
        try{
            return new ObjectMapper().readValue(decodeBase64(header), Map.class);
        }catch (IOException e){
            throw new AuthException(AuthErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    UserInfoDto getInfo(String idToken);
}
