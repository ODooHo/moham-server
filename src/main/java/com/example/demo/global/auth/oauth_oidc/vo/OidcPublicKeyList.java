package com.example.demo.global.auth.oauth_oidc.vo;

import com.example.demo.api.exception.auth.AuthErrorCode;
import com.example.demo.api.exception.auth.AuthException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OidcPublicKeyList{
        List<OidcPublicKey> keys;

    public OidcPublicKey getMatchedKey(String kid, String alg) {
        return keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findAny()
                .orElseThrow(() -> new AuthException(AuthErrorCode.INTERNAL_SERVER_ERROR));
    }

}