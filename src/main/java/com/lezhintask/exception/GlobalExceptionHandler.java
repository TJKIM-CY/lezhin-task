package com.lezhintask.exception;

import com.lezhintask.constant.Code;
import com.lezhintask.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.dao.DataAccessException;

/**
 * 전역 예외 처리
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 인증 예외 처리
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDto> handleAuthenticationException() {
        ResponseDto response = new ResponseDto(Code.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * 데이터 접근 예외 처리
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseDto> handleDataAccessException() {
        ResponseDto response = new ResponseDto(Code.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * 일반 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGlobalException() {
        ResponseDto response = new ResponseDto(Code.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}