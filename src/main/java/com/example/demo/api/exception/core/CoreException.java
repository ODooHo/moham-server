package com.example.demo.api.exception.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoreException extends RuntimeException{
    private CoreErrorCode errorCode;
    private String message;

    public CoreException(CoreErrorCode errorCode) {
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
