package com.example.demo.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueDto {
    String accessToken;
    String refreshToken;



    public static ReissueDto of(String accessToken, String refreshToken){
        return new ReissueDto(accessToken,refreshToken);
    }

}
