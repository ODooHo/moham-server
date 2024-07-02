package com.example.demo.api.user.controller;

import com.example.demo.api.response.ResponseDto;
import com.example.demo.api.user.dto.request.SignInRequest;
import com.example.demo.api.user.dto.request.SignUpRequest;
import com.example.demo.api.user.dto.response.JwtResponse;
import com.example.demo.api.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<JwtResponse> signUp(@RequestBody SignUpRequest request){
        return ResponseDto.success(authService.signUp(request));
    }

    @PostMapping("/signIn")
    public ResponseDto<JwtResponse> signIn(@RequestBody SignInRequest request){
        return ResponseDto.success(authService.signIn(request));
    }

}
