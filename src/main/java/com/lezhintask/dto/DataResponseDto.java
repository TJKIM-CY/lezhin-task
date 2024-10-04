package com.lezhintask.dto;

import com.lezhintask.constant.Code;

import lombok.Getter;

/**
 * 데이터가 포함된 응답 DTO
 */
@Getter
public class DataResponseDto<T> extends ResponseDto {
    private final T data;   // 데이터

    public DataResponseDto(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public DataResponseDto(Code code, T data) {
        super(code.getCode(), code.getMessage());
        this.data = data;
    }
}
