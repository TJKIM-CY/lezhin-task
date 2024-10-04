package com.lezhintask.controller;

import com.lezhintask.constant.Code;
import com.lezhintask.constant.Path;
import com.lezhintask.dto.ContentDto;
import com.lezhintask.dto.DataResponseDto;
import com.lezhintask.service.ContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 작품 관련 API 컨트롤러
 */
@RestController
public class ContentController {

    @Autowired
    private ContentServiceImpl contentServiceImpl;

    /**
     * 작품 조회 이력 API
     *
     * @param contentId 작품 ID
     * @return 작품 조회 이력 리스트
     */
    @GetMapping(Path.CONTENT_VIEW_HISTORY)
    public ResponseEntity<DataResponseDto<List<ContentDto>>> getViewHistoryByContentId(@RequestParam(required = false) String contentId) {
        List<ContentDto> history = contentServiceImpl.getViewHistoryByContentId(contentId);
        DataResponseDto<List<ContentDto>> response = new DataResponseDto<>(Code.SUCCESS, history);
        return ResponseEntity.ok(response);
    }

    /**
     * 인기 작품 상위 10개 조회 API
     *
     * @return 인기 작품 리스트
     */
    @GetMapping(Path.TOP_VIEW_CONTENT)
    public ResponseEntity<DataResponseDto<List<ContentDto>>> getTopViewContent() {
        List<ContentDto> topContents = contentServiceImpl.getTopViewContent();
        DataResponseDto<List<ContentDto>> response = new DataResponseDto<>(Code.SUCCESS, topContents);
        return ResponseEntity.ok(response);
    }
}