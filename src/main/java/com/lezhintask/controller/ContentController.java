package com.lezhintask.controller;

import com.lezhintask.constant.Code;
import com.lezhintask.constant.Path;
import com.lezhintask.dto.*;
import com.lezhintask.service.ContentServiceImpl;
import com.lezhintask.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 작품 관련 API 컨트롤러
 */
@RestController
@Tag(name = "Content", description = "작품 관련 API")
public class ContentController {

    @Autowired
    private ContentServiceImpl contentServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 작품 조회 이력 API
     *
     * @param contentId 작품 ID
     * @return 작품 조회 이력 리스트
     */
    @GetMapping(Path.CONTENT_VIEW_HISTORY)
    @Operation(summary = "작품 조회 이력", description = "작품 ID로 조회 이력")
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
    @Operation(summary = "인기 작품 조회", description = "인기 작품 상위 10개 조회")
    public ResponseEntity<DataResponseDto<List<ContentDto>>> getTopViewContent() {
        List<ContentDto> topViewContent = contentServiceImpl.getTopViewContent();
        DataResponseDto<List<ContentDto>> response = new DataResponseDto<>(Code.SUCCESS, topViewContent);

        return ResponseEntity.ok(response);
    }

    /**
     * 작품 구매 API
     *
     * @param contentRequestDto 작품 관련 요청 DTO
     * @param authentication 인증 정보
     * @return 작품 구매 성공
     */
    @PostMapping(Path.CONTENT_PURCHASE)
    @Operation(summary = "작품 구매", description = "작품 구매")
    public ResponseEntity<ResponseDto> purchaseContent(@RequestBody ContentRequestDto contentRequestDto, Authentication authentication) {
        // 작품 정보 조회
        String contentId = contentRequestDto.getContentId();
        ContentDto contentInfo = contentServiceImpl.getContentInfo(contentId);

        // contentInfo가 null 처리
        if (contentInfo == null) {
            ResponseDto response = new ResponseDto(Code.FAIL);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // 유저 정보 조회
        String userId = authentication.getName();
        UserDto userDto = userServiceImpl.findByUserId(userId);
        boolean isAdult = userDto.isAdult();

        // 성인 작품 여부와 유저 성인 여부 비교
        if (contentInfo.isAdultContent() && !isAdult) {
            ResponseDto response = new ResponseDto(Code.AGE_RESTRICTION);
            return ResponseEntity.ok(response);
        }

        // 작품 구매 정보 저장
        contentServiceImpl.savePurchase(contentInfo, userId);

        ResponseDto response = new ResponseDto(Code.SUCCESS);

        return ResponseEntity.ok(response);
    }

    /**
     * 구매 인기 작품 상위 10개 조회 API
     *
     * @return 구매 인기 작품 리스트
     */
    @GetMapping(Path.TOP_PURCHASE_CONTENT)
    @Operation(summary = "구매 인기 작품 조회", description = "구매 인기 작품 상위 10개 조회")
    public ResponseEntity<DataResponseDto<List<ContentDto>>> getTopPurchaseContent() {
        List<ContentDto> topPurchaseContent = contentServiceImpl.getTopPurchaseContent();
        DataResponseDto<List<ContentDto>> response = new DataResponseDto<>(Code.SUCCESS, topPurchaseContent);

        return ResponseEntity.ok(response);
    }
}