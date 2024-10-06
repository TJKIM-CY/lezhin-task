package com.lezhintask.service;

import com.lezhintask.dto.ContentDto;
import com.lezhintask.mapper.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 작품 관련 서비스 구현
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
    @Cacheable("viewHistory")
    @Override
    public List<ContentDto> getViewHistoryByContentId(String contentId) {
        return contentMapper.selectViewHistoryByContentId(contentId);
    }

    /**
     * 인기 작품 상위 10개 조회
     *
     * @return 인기 작품 리스트
     */
    @Cacheable("topViewContent")
    @Override
    public List<ContentDto> getTopViewContent() {
        return contentMapper.selectTopViewContent();
    }

    /**
     * 작품 정보 조회
     *
     * @param contentId 작품 ID
     * @return 작품 정보
     */
    @Override
    public ContentDto getContentInfo(String contentId) {
        return contentMapper.selectContentInfo(contentId);
    }

    /**
     * 작품 구매 정보 저장
     *
     * @param contentInfo 프로모션 정보
     * @param userId 유저 ID
     */
    @Override
    public void savePurchase(ContentDto contentInfo, String userId) {
        contentMapper.insertPurchase(contentInfo, userId);
    }

    /**
     * 구매 인기 작품 상위 10개 조회
     *
     * @return 구매 인기 작품 리스트
     */
    @Cacheable("topPurchaseContent")
    @Override
    public List<ContentDto> getTopPurchaseContent() {
        return contentMapper.selectTopPurchaseContent();
    }

    /**
     * 작품과 작품 전체 조회 이력 삭제
     *
     * @param contentId 작품 ID
     */
    @Transactional
    public void deleteContentAndHistory(String contentId) {
        contentMapper.deleteViewHistoryByContentId(contentId);
        contentMapper.deleteContentById(contentId);
    }
}
