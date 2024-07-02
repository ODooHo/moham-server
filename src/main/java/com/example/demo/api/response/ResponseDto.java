package com.example.demo.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto <T>{

    private String resultCode;
    private T result;


    public static <T> ResponseDto<T> success(){
        return new ResponseDto<>("SUCCESS",null);
    }

    public static <T> ResponseDto<T> success(T result){
        return new ResponseDto<>("SUCCESS",result);
    }

    public static ResponseDto<Void> error(String resultCode){
        return new ResponseDto<>(resultCode,null);
    }

}
