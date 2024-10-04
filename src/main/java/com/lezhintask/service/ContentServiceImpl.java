package com.lezhintask.service;

import com.lezhintask.dto.ContentDto;
import com.lezhintask.mapper.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ContentService 인터페이스 구현
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    /**
     * 작품 조회 이력
     *
     * @param contentId 작품 ID
     * @return 작품 조회 이력 리스트
     */
    @Override
    public List<ContentDto> getViewHistoryByContentId(String contentId) {
        return contentMapper.selectViewHistoryByContentId(contentId);
    }

    /**
     * 인기 작품 상위 10개 조회
     *
     * @return 인기 작품 리스트
     */
    @Override
    public List<ContentDto> getTopViewContent() {
        return contentMapper.selectTopViewContent();
    }
}
