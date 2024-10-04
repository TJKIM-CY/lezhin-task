package com.lezhintask.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * API 응답 기본 DTO
 */
@Getter
@ToString
@RequiredArgsConstructor
public class ResponseDto {
    private final String code;  // 응답 코드
    private final String message;   // 응답 메시지
}
