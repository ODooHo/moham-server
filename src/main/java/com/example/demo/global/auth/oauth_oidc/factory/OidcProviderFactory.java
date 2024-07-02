package com.example.demo.global.auth.oauth_oidc.factory;

import com.example.demo.api.exception.auth.AuthErrorCode;
import com.example.demo.api.exception.auth.AuthException;
import com.example.demo.global.auth.oauth_oidc.provider.OidcProvider;
import com.example.demo.global.auth.oauth_oidc.provider.Provider;
import com.example.demo.global.auth.oauth_oidc.provider.UserInfoDto;
import com.example.demo.global.auth.oauth_oidc.provider.impl.GoogleAuthProvider;
import com.example.demo.global.auth.oauth_oidc.provider.impl.KakaoAuthProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Component
public class OidcProviderFactory {

    private final Map<Provider, OidcProvider> authProviderMap;
    private final KakaoAuthProvider kakaoAuthProvider;
    private final GoogleAuthProvider googleAuthProvider;

    public OidcProviderFactory(
            KakaoAuthProvider kakaoAuthProvider,
            GoogleAuthProvider googleAuthProvider
    ){
        authProviderMap = new EnumMap<>(Provider.class);

        this.kakaoAuthProvider = kakaoAuthProvider;
        this.googleAuthProvider = googleAuthProvider;

        initialize();
    }

    private void initialize(){
        authProviderMap.put(Provider.KAKAO,kakaoAuthProvider);
        authProviderMap.put(Provider.GOOGLE,googleAuthProvider);
    }

    public UserInfoDto getProviderId(Provider provider, String idToken){
        return getProvider(provider).getInfo(idToken);
    }

    private OidcProvider getProvider(Provider provider){
        OidcProvider oidcProvider = authProviderMap.get(provider);

        if(oidcProvider == null){
            throw new AuthException(AuthErrorCode.WRONG_PROVIDER);
        }

        return oidcProvider;
    }


}
