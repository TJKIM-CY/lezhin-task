package com.lezhintask.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "작품 정보 DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Schema(description = "작품 구매 수")
    private int purchaseCount;

    @Schema(description = "유저 ID")
    private String userId;

    @Schema(description = "작품 금액")
    private int price;

    @Schema(description = "작품 설명")
    private String description;

    @Schema(description = "작품 유/무료 여부")
    private boolean isPaid;
}
