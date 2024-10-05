package com.lezhintask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "유저 정보 DTO")
public class UserDto {
    @Schema(description = "유저 ID")
    private String userId;

    @Schema(description = "유저 비밀번호")
    private String password;

    @Schema(description = "성인 여부")
    private boolean isAdult;
}
