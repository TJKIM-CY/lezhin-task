package com.lezhintask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원가입 요청 DTO")
public class SignupRequestDto {
    @Schema(description = "유저 ID", example = "user1")
    private String userId;

    @Schema(description = "유저 비밀번호", example = "aaaaaa11")
    private String password;

    @Schema(description = "성인 여부", example = "true")
    private boolean isAdult;
}