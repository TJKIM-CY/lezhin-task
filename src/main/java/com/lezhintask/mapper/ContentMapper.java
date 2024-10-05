package com.lezhintask.mapper;

import com.lezhintask.dto.ContentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 작품 관련 매퍼 인터페이스
 */
@Mapper
public interface ContentMapper {
    /**
     * 작품 조회 이력
     *
     * @param contentId 작품 ID
     * @return 작품 조회 이력 리스트
     */
    List<ContentDto> selectViewHistoryByContentId(@Param("contentId") String contentId);

    /**
     * 인기 작품 상위 10개 조회
     *
     * @return 인기 작품 리스트
     */
    List<ContentDto> selectTopViewContent();

    /**
     * 작품 정보 저회
     *
     * @param contentId 작품 ID
     * @return 작품 정보
     */
    ContentDto getContentInfo(@Param("contentId") String contentId);

    /**
     * 작품 구매 정보 저장
     *
     * @param contentInfo 프로모션 정보
     * @param userId      유저 ID
     */
    void insertPurchase(ContentDto contentInfo, String userId);
}
