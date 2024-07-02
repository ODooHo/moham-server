package com.example.demo.api.exception;

import com.example.demo.api.exception.auth.AuthException;
import com.example.demo.api.exception.core.CoreException;
import com.example.demo.api.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.demo.api.exception.core.CoreErrorCode.DATABASE_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<?> errorHandler(CoreException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ResponseDto.error(e.getErrorCode().name()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> errorHandler(AuthException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ResponseDto.error(e.getErrorCode().name()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> databaseErrorHandler(IllegalArgumentException e) {
        log.error(e.getMessage());
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(DATABASE_ERROR.getStatus())
                .body(ResponseDto.error(DATABASE_ERROR.name()));
    }

}