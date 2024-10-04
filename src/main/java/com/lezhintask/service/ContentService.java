package com.lezhintask.service;

import com.lezhintask.dto.ContentDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 작품 관련 서비스 인터페이스
 */
@Service
public interface ContentService {
    /**
     * 작품 조회 이력
     *
     * @param contentId 작품 ID
     * @return 작품 조회 이력 리스트
     */
    List<ContentDto> getViewHistoryByContentId(String contentId);

    /**
     * 인기 작품 상위 10개 조회
     *
     * @return 인기 작품 리스트
     */
    List<ContentDto> getTopViewContent();
}