package com.example.demo.api.user.dto.request;

import com.example.demo.global.auth.oauth_oidc.provider.Provider;
import lombok.Getter;

@Getter
public class SignInRequest {
    private Provider provider;
    private String idToken;
}

