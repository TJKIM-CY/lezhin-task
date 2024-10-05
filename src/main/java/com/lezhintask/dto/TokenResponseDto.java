package com.lezhintask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "토큰 응답 DTO")
public class TokenResponseDto {
    @Schema(description = "토큰")
    private String token;

    @Schema(description = "토큰 만료 날짜")
    private Date expirationDate;

    public TokenResponseDto(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }
}