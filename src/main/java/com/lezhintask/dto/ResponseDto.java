package com.lezhintask.dto;

import com.lezhintask.constant.Code;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@Schema(description = "응답 DTO")
public class ResponseDto {
    @Schema(description = "응답 코드")
    private final String code;

    @Schema(description = "응답 메시지")
    private final String message;

    public ResponseDto(Code code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
