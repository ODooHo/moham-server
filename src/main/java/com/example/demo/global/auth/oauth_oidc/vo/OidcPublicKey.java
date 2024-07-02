package com.example.demo.global.auth.oauth_oidc.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OidcPublicKey{
        String kid;
        String kty;
        String alg;
        String use;
        String n;
        String e;
}