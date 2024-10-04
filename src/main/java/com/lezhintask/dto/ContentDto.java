package com.lezhintask.dto;

import lombok.Data;

/**
 * 작품 관련 정보를 담는 DTO
 */
@Data
public class ContentDto {
    private String name;    // 유저 이름
    private String title;   // 작품 제목
    private String viewedAt;    // 조회 일
    private int viewCount;  // 조회 수
}
