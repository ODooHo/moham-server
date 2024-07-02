package com.example.demo.global.auth.oauth_oidc.provider;

import com.example.demo.api.exception.auth.AuthErrorCode;
import com.example.demo.api.exception.auth.AuthException;
import com.example.demo.global.auth.oauth_oidc.vo.OidcPublicKey;
import com.example.demo.global.auth.oauth_oidc.vo.OidcPublicKeyList;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

@Component
public class PublicKeyProvider {

    public PublicKey generatePublicKey(Map<String, String> tokenHeaders, OidcPublicKeyList publicKeys) {
        OidcPublicKey publicKey = publicKeys.getMatchedKey(tokenHeaders.get("kid"), tokenHeaders.get("alg"));

        return getPublicKey(publicKey);
    }

    private PublicKey getPublicKey(OidcPublicKey publicKey) {
        byte[] nBytes = decodeBase64(publicKey.getN());
        byte[] eBytes = decodeBase64(publicKey.getE());

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, nBytes), new BigInteger(1, eBytes));

        try {
            return KeyFactory.getInstance(publicKey.getKty()).generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }
}