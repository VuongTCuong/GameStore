package com.gamestore.gamestore.exception;

import java.security.SignatureException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MainErrorException.class)
    public ResponseEntity<Map<String,Object>> handleMainErrorException(MainErrorException meExcep){
        Map<String,Object> responsebody = Map.of(
            "status","error",
            "message",meExcep.getMessage()
        );
        return ResponseEntity.badRequest().body(responsebody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentException(MethodArgumentNotValidException methodArguExcep){
        Map<String,Object> responsebody = Map.of(
            "status","error",
            "message",methodArguExcep.getFieldError().getDefaultMessage()
        );
        return ResponseEntity.badRequest().body(responsebody);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String,Object>> handleExpiredJwtException(ExpiredJwtException expiredJwtExcep){
        Map<String,Object> responsebody = Map.of(
            "status","error",
            "message", "Token đã hết hạn, vui lòng đăng nhập lại"
        );
        return ResponseEntity.badRequest().body(responsebody);
    }
    
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String,Object>> handleSignatureException(SignatureException signatureExcep){
        Map<String,Object> responsebody = Map.of(
            "status","error",
            "message", "Token không hợp lệ"
        );
        return ResponseEntity.badRequest().body(responsebody);
    }
}
