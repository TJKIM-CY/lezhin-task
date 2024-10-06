package com.lezhintask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lezhintask.config.RateLimiter;
import com.lezhintask.constant.Code;
import com.lezhintask.dto.ContentDto;
import com.lezhintask.dto.ContentRequestDto;
import com.lezhintask.dto.UserDto;
import com.lezhintask.service.ContentServiceImpl;
import com.lezhintask.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ContentController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1", roles = {"USER"})
public class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentServiceImpl contentServiceImpl;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RateLimiter rateLimiter;

    @BeforeEach
    public void setUp() {
        when(rateLimiter.tryConsume()).thenReturn(true);
    }

    // 작품 조회 이력 테스트
    @Test
    void testGetViewHistoryByContentId() throws Exception {
        ContentDto content1 = new ContentDto();
        content1.setTitle("Title1");
        content1.setUserId("user1");
        content1.setViewedAt("2023-10-01");

        ContentDto content2 = new ContentDto();
        content2.setTitle("Title2");
        content2.setUserId("user2");
        content2.setViewedAt("2023-10-02");

        List<ContentDto> mockContentList = Arrays.asList(content1, content2);

        when(contentServiceImpl.getViewHistoryByContentId(anyString())).thenReturn(mockContentList);

        mockMvc.perform(get("/api/content/view-history")
                        .param("contentId", "123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(Code.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].title", is("Title1")))
                .andExpect(jsonPath("$.data[0].userId", is("user1")))
                .andExpect(jsonPath("$.data[0].viewedAt", is("2023-10-01")))
                .andExpect(jsonPath("$.data[1].title", is("Title2")))
                .andExpect(jsonPath("$.data[1].userId", is("user2")))
                .andExpect(jsonPath("$.data[1].viewedAt", is("2023-10-02")));
    }

    // 인기 작품 상위 10개 조회 테스트
    @Test
    void testGetTopViewContent() throws Exception {
        ContentDto content1 = new ContentDto();
        content1.setTitle("Title1");
        content1.setViewCount(100);

        ContentDto content2 = new ContentDto();
        content2.setTitle("Title2");
        content2.setViewCount(90);

        List<ContentDto> mockContentList = Arrays.asList(content1, content2);

        when(contentServiceImpl.getTopViewContent()).thenReturn(mockContentList);

        mockMvc.perform(get("/api/content/top-view")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(Code.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].title", is("Title1")))
                .andExpect(jsonPath("$.data[0].viewCount", is(100)))
                .andExpect(jsonPath("$.data[1].title", is("Title2")))
                .andExpect(jsonPath("$.data[1].viewCount", is(90)));
    }

    // 작품 구매 성공 테스트
    @Test
    public void testPurchaseContent_Success() throws Exception {
        ContentRequestDto contentRequestDto = new ContentRequestDto();
        contentRequestDto.setContentId("content1");

        ContentDto contentInfo = new ContentDto();
        contentInfo.setAdultContent(false);

        UserDto userDto = new UserDto();
        userDto.setAdult(true);

        when(contentServiceImpl.getContentInfo(anyString())).thenReturn(contentInfo);
        when(userServiceImpl.findByUserId(anyString())).thenReturn(userDto);

        // Authentication 객체 모의
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("user1");

        mockMvc.perform(post("/api/content/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contentRequestDto))
                        .principal(authentication)) // 모의된 Authentication 객체 사용
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(Code.SUCCESS.getCode())));
    }

    // 작품 구매 실패 테스트
    @Test
    public void testPurchaseContent_AgeRestriction() throws Exception {
        ContentRequestDto contentRequestDto = new ContentRequestDto();
        contentRequestDto.setContentId("content1");

        ContentDto contentInfo = new ContentDto();
        contentInfo.setAdultContent(true);

        UserDto userDto = new UserDto();
        userDto.setAdult(false);

        when(contentServiceImpl.getContentInfo(anyString())).thenReturn(contentInfo);
        when(userServiceImpl.findByUserId(anyString())).thenReturn(userDto);

        // Authentication 객체 모의
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("user1");

        mockMvc.perform(post("/api/content/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contentRequestDto))
                        .principal(authentication)) // 모의된 Authentication 객체 사용
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(Code.AGE_RESTRICTION.getCode())));
    }

    // 구매 인기 작품 상위 10개 조회 테스트
    @Test
    void testGetTopPurchaseContent() throws Exception {
        ContentDto content1 = new ContentDto();
        content1.setTitle("Title1");
        content1.setContentId("content1");
        content1.setPurchaseCount(150);

        ContentDto content2 = new ContentDto();
        content2.setTitle("Title2");
        content2.setContentId("content2");
        content2.setPurchaseCount(120);

        List<ContentDto> mockContentList = Arrays.asList(content1, content2);

        when(contentServiceImpl.getTopPurchaseContent()).thenReturn(mockContentList);

        mockMvc.perform(get("/api/content/top-purchase")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(Code.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].title", is("Title1")))
                .andExpect(jsonPath("$.data[0].contentId", is("content1")))
                .andExpect(jsonPath("$.data[0].purchaseCount", is(150)))
                .andExpect(jsonPath("$.data[1].title", is("Title2")))
                .andExpect(jsonPath("$.data[1].contentId", is("content2")))
                .andExpect(jsonPath("$.data[1].purchaseCount", is(120)));
    }

    // 작품 및 작품 전체 조회 이력 삭제 테스트
    @Test
    void testDeleteContentAndHistory() throws Exception {
        String contentId = "content1";

        mockMvc.perform(delete("/api/content/" + contentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(Code.SUCCESS.getCode())));
    }
}