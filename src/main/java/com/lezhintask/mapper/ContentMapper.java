package com.lezhintask.mapper;

import com.lezhintask.dto.ContentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MyBatis 매퍼 인터페이스
 */
@Mapper
public interface ContentMapper {
    /**
     * 작품 조회 이력 쿼리
     *
     * @param contentId 작품 ID
     * @return 작품 조회 이력 리스트
     */
    List<ContentDto> selectViewHistoryByContentId(@Param("contentId") String contentId);

    /**
     * 인기 작품 상위 10개 조회 쿼리
     *
     * @return 인기 작품 리스트
     */
    List<ContentDto> selectTopViewContent();
}
