package com.lezhintask.controller;

import com.lezhintask.constant.Code;
import com.lezhintask.dto.ContentDto;
import com.lezhintask.service.ContentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ContentController.class)
public class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentServiceImpl contentServiceImpl;

    // getViewHistory 메소드 테스트
    @Test
    void getViewHistoryByContentId() throws Exception {
        ContentDto content1 = new ContentDto();
        content1.setTitle("Title1");
        content1.setName("User1");
        content1.setViewedAt("2023-10-01");

        ContentDto content2 = new ContentDto();
        content2.setTitle("Title2");
        content2.setName("User2");
        content2.setViewedAt("2023-10-02");

        List<ContentDto> mockContentList = Arrays.asList(content1, content2);

        Mockito.when(contentServiceImpl.getViewHistoryByContentId(anyString())).thenReturn(mockContentList);

        mockMvc.perform(get("/api/content/view-history")
                        .param("contentId", "123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(Code.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].title", is("Title1")))
                .andExpect(jsonPath("$.data[0].name", is("User1")))
                .andExpect(jsonPath("$.data[0].viewedAt", is("2023-10-01")))
                .andExpect(jsonPath("$.data[1].title", is("Title2")))
                .andExpect(jsonPath("$.data[1].name", is("User2")))
                .andExpect(jsonPath("$.data[1].viewedAt", is("2023-10-02")));
    }

    // getTopViewContent 메소드에 테스트
    @Test
    void getTopViewContent() throws Exception {
        ContentDto content1 = new ContentDto();
        content1.setTitle("Title1");
        content1.setViewCount(100);

        ContentDto content2 = new ContentDto();
        content2.setTitle("Title2");
        content2.setViewCount(90);

        List<ContentDto> mockContentList = Arrays.asList(content1, content2);

        Mockito.when(contentServiceImpl.getTopViewContent()).thenReturn(mockContentList);

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
}