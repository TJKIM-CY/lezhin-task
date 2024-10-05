package com.lezhintask.dto;

import com.lezhintask.constant.Code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "데이터 응답 DTO")
public class DataResponseDto<T> extends ResponseDto {
    @Schema(description = "데이터")
    private final T data;

    public DataResponseDto(Code code, T data) {
        super(code.getCode(), code.getMessage());
        this.data = data;
    }
}
