package com.example.demo.api.exception.auth;

import com.example.demo.api.exception.core.CoreErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException{
    private AuthErrorCode errorCode;
    private String message;

    public AuthException(AuthErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;

    }

    @Override
    public String getMessage(){
        if(message == null){
            return errorCode.getMessage();
        }else{
            return String.format("%s. %s", errorCode.getMessage(),message);
        }
    }
}
