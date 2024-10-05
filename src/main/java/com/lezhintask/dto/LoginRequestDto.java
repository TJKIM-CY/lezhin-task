package com.lezhintask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "로그인 요청 DTO")
@Data
public class LoginRequestDto {
    @Schema(description = "유저 ID")
    private String userId;

    @Schema(description = "유저 비밀번호")
    private String password;
}
