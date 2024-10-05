package com.lezhintask.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * API 응답 코드와 메시지 enum 코드
 */
@Getter
@RequiredArgsConstructor
public enum Code {
    SUCCESS("0000", "성공"),
    FAIL("1000", "실패"),
    UNAUTHORIZED("4000", "인증 에러");

    private final String code;
    private final String message;
}