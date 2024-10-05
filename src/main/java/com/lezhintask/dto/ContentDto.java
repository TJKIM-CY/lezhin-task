package com.lezhintask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "작품 정보 DTO")
public class ContentDto {
    @Schema(description = "작품 제목")
    private String title;

    @Schema(description = "조회 일")
    private String viewedAt;

    @Schema(description = "조회 수")
    private int viewCount;

    @Schema(description = "작품 구매 금액")
    private int paymentPrice;

    @Schema(description = "작품 ID")
    private String contentId;

    @Schema(description = "프로모션 ID")
    private String promotionId;

    @Schema(description = "작품 성인 여부")
    private boolean isAdultContent;
}
