package com.example.demo.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InfoResponse {
    private String email;
    private String username;
    private String nickname;


    public static InfoResponse of(String email, String username, String nickname){
        return new InfoResponse(email,username,nickname);
    }
}
