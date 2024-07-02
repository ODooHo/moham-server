package com.example.demo.global.auth.oauth_oidc;


import com.example.demo.global.auth.oauth_oidc.vo.OidcPublicKeyList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class KakaoAuthClient {

    private final RestClient restClient;

    private final String publicKeyUrl;

    public KakaoAuthClient(
            RestClient restClient,
            @Value("${oauth.kakao.public-key-info}") String publicKeyUrl
    ) {
        this.restClient = restClient;
        this.publicKeyUrl = publicKeyUrl;
    }

    public OidcPublicKeyList getPublicKeys() {
        return restClient.get()
                .uri(publicKeyUrl)
                .retrieve()
                .body(OidcPublicKeyList.class);
    }
}