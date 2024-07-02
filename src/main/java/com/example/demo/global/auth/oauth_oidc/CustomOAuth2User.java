package com.example.demo.global.auth.oauth_oidc;

import com.example.demo.api.user.dto.UserDto;
import com.example.demo.api.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ToString
@RequiredArgsConstructor
public class CustomOAuth2User implements UserDetails,OAuth2User, OidcUser {

    private final UserDto userDto;

    private final Map<String, Object> attribute;


    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    public CustomOAuth2User(UserEntity user){
        this.userDto = UserDto.from(user);
        this.attribute = Map.of("id",user.getUsername());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDto.getRole().name();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userDto.getUsername();
    }

    @Override
    public String getName() {
        return userDto.getUsername();
    }


    public String getUserName() {
        return userDto.getUsername();
    }


    @Override
    public Map<String, Object> getClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDto.getUsername());
        claims.put("email", userDto.getEmail());
        claims.put("role", userDto.getRole().name());
        return claims;
    }
    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }
}
