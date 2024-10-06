package com.lezhintask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lezhintask.constant.Code;
import com.lezhintask.dto.LoginRequestDto;
import com.lezhintask.dto.SignupRequestDto;
import com.lezhintask.security.JwtTokenProvider;
import com.lezhintask.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1", roles = {"USER"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    // 회원가입 테스트
    @Test
    public void testSignupUser() throws Exception {
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUserId("newUser");
        signupRequestDto.setPassword("newPassword");

        doNothing().when(userServiceImpl).signupUser(signupRequestDto);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(Code.SUCCESS.getCode())));
    }

    // 로그인 성공 테스트
    @Test
    public void testLoginUser_Success() throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUserId("user1");
        loginRequestDto.setPassword("password");

        Authentication authentication = new UsernamePasswordAuthenticationToken("user1", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        String token = "mockToken";
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60); // 1 hour later
        when(tokenProvider.generateToken(authentication)).thenReturn(token);
        when(tokenProvider.getExpirationDateFromToken(token)).thenReturn(expirationDate);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.data.token").value(token))
                .andExpect(jsonPath("$.data.expirationDate").exists());
    }

    // 로그인 메소드 실패 테스트
    @Test
    public void testLoginUser_Unauthorized() throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUserId("user1");
        loginRequestDto.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {
                });

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("4000"));
    }
}