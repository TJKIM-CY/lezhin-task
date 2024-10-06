package com.lezhintask.controller;

import com.lezhintask.constant.Code;
import com.lezhintask.constant.Path;
import com.lezhintask.dto.*;
import com.lezhintask.security.JwtTokenProvider;
import com.lezhintask.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 유저 관련 API 컨트롤러
 */
@RestController
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 유저 로그인 API
     *
     * @param loginRequestDto 로그인 요청 DTO
     * @return JWT 토큰 및 만료 날짜
     */
    @PostMapping(Path.LOGIN)
    @Operation(summary = "유저 로그인", description = "유저 ID와 비밀번호로 로그인하여 JWT 토큰 발급")
    public ResponseEntity<ResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        // 유저 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUserId(),
                        loginRequestDto.getPassword()
                )
        );

        // JWT 토큰 생성
        String token = tokenProvider.generateToken(authentication);
        Date expirationDate = tokenProvider.getExpirationDateFromToken(token);

        // 토큰 응답 DTO 생성
        TokenResponseDto tokenResponse = new TokenResponseDto(token, expirationDate);
        DataResponseDto<TokenResponseDto> response = new DataResponseDto<>(Code.SUCCESS, tokenResponse);

        return ResponseEntity.ok(response);
    }

    /**
     * 유저 회원가입 API
     *
     * @param signupRequestDto 회원가입 요청 DTO
     * @return 회원가입 성공
     */
    @PostMapping(Path.SIGNUP)
    @Operation(summary = "유저 회원가입", description = "유저 회원가입")
    public ResponseEntity<ResponseDto> signupUser(@RequestBody SignupRequestDto signupRequestDto) {
        // 유저 회원가입
        userServiceImpl.signupUser(signupRequestDto);

        ResponseDto response = new ResponseDto(Code.SUCCESS);

        return ResponseEntity.ok(response);
    }
}