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
     * 작품 정보 조회
     *
     * @param contentId 작품 ID
     * @return 작품 정보
     */
    ContentDto selectContentInfo(@Param("contentId") String contentId);

    /**
     * 작품 구매 정보 저장
     *
     * @param contentInfo 프로모션 정보
     * @param userId 유저 ID
     */
    void insertPurchase(ContentDto contentInfo, String userId);

    /**
     * 구매 인기 작품 상위 10개 조회
     *
     * @return 구매 인기 작품 리스트
     */
    List<ContentDto> selectTopPurchaseContent();

    /**
     * 작품 삭제
     *
     * @param contentId 작품 ID
     */
    void deleteContentById(@Param("contentId") String contentId);

    /**
     * 작품 전체 조회 이력 삭제
     *
     * @param contentId 작품 ID
     */
    void deleteViewHistoryByContentId(@Param("contentId") String contentId);

    /**
     * 작품 리스트 조회
     *
     * @param userId 유저 ID
     * @param limit 데이터 갯수
     * @param offset 조회 시작 위치
     * @return 작품 리스트
     */
    List<ContentDto> selectContentsByUserId(@Param("userId") String userId, @Param("limit") int limit, @Param("offset") int offset);
}
