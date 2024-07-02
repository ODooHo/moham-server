package com.example.demo.api.user.dto;

import com.example.demo.api.user.UserRole;
import com.example.demo.api.user.entity.UserEntity;
import com.example.demo.global.auth.oauth_oidc.provider.Provider;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Provider provider;
    private String providerId;
    private String email;
    private String password;
    private String username;
    private String nickname;
    private UserRole role;



    public static UserDto of(Provider provider, String providerId, String email, String password, String username, String nickname,UserRole role){
        return new UserDto(
                provider,
                providerId,
                email,
                password,
                username,
                nickname,
                role
        );
    }

    public static UserDto from(UserEntity entity){
        return UserDto.of(
                entity.getProvider(),
                entity.getProviderId(),
                entity.getEmail(),
                null,
                entity.getUsername(),
                entity.getNickname(),
                entity.getRole()
        );
    }
}
