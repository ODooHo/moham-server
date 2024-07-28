package com.example.demo.global.auth.oauth_oidc.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OidcPublicKey {
    private String kid;
    private String kty;
    private String alg;
    private String use;
    private String n;
    private String e;
}