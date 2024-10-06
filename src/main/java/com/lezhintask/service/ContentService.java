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

    /**
     * 작품 정보 조회
     *
     * @param contentId 작품 ID
     * @return 작품 정보
     */
    ContentDto getContentInfo(String contentId);

    /**
     * 작품 구매 정보 저장
     *
     * @param contentInfo 프로모션 정보
     * @param userId 유저 ID
     */
    void savePurchase(ContentDto contentInfo, String userId);

    /**
     * 구매 인기 작품 상위 10개 조회
     *
     * @return 구매 인기 작품 리스트
     */
    List<ContentDto> getTopPurchaseContent();

    /**
     * 작품과 작품 전체 조회 이력 삭제
     *
     * @param contentId 작품 ID
     */
    void deleteContentAndHistory (String contentId);

    /**
     * 작품 리스트 조회
     *
     * @param userId 유저 ID
     * @param page 페이지 번호 (디폴트 : 1)
     * @param size 페이지 크기 (디폴트 : 10)
     * @return 작품 리스트
     */
    List<ContentDto> getContentsByUserId(String userId, int page, int size);
}