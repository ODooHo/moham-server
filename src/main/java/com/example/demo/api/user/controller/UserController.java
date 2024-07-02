package com.example.demo.api.user.controller;

import com.example.demo.api.response.ResponseDto;
import com.example.demo.api.user.dto.UserDto;
import com.example.demo.api.user.dto.response.InfoResponse;
import com.example.demo.api.user.service.UserService;
import com.example.demo.global.auth.oauth_oidc.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseDto<InfoResponse> info(@AuthenticationPrincipal CustomOAuth2User user){
        InfoResponse result = userService.info(user.getEmail());
        return ResponseDto.success(result);
    }
}
