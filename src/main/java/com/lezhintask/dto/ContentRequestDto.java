package com.lezhintask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "작품 요청 DTO")
public class ContentRequestDto {
    @Schema(description = "작품 ID")
    private String contentId;
}