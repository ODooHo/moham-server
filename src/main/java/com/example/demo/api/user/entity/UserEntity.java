package com.example.demo.api.user.entity;

import com.example.demo.api.user.UserRole;
import com.example.demo.global.auth.oauth_oidc.provider.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

/**
 * TODO: 추후 직업군과 직업 정보와 함께 연관관계 매핑 예정 -> 수많은 직업 정보를 어떤식으로 다룰지 생각 필요
 */
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 얘를 날리고 email을 id로?

    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider", length = 10, nullable = false)
    private Provider provider;

    @Column(name = "provider_id", length = 50, nullable = false)
    private String providerId;

    @Column(name = "email")
    private String email; //로그인 용 인증 이메일. 명함에 기입할 이메일과는 별개 요소

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private UserRole role;


    public static UserEntity of(Long id, Provider provider, String providerId, String email, String password, String username, String nickname, UserRole role) {
        return new UserEntity(id, provider, providerId, email, password, username, nickname, role);
    }

    public static UserEntity of(Provider provider, String providerId, String email, String username, String nickname, UserRole role) {
        return UserEntity.of(
                null,
                provider,
                providerId,
                email,
                null,
                username,
                nickname,
                role
        );
    }

}
