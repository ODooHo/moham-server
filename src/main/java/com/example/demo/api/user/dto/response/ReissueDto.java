package com.example.demo.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueDto {
    private String accessToken;
    private String refreshToken;



    public static ReissueDto of(String accessToken, String refreshToken){
        return new ReissueDto(accessToken,refreshToken);
    }

}
