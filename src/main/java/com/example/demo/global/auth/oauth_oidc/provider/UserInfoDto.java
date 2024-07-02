package com.example.demo.global.auth.oauth_oidc.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
        String providerId;
        String email;

    public static UserInfoDto of(String providerId, String email){
        return new UserInfoDto(providerId,email);
    }
}
