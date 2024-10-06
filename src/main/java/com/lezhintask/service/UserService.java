package com.lezhintask.service;

import com.lezhintask.dto.SignupRequestDto;
import com.lezhintask.dto.UserDto;

/**
 * 유저 관련 서비스 인터페이스
 */
public interface UserService {

    /**
     * 유저 회원가입
     *
     * @param signupRequestDto 회원가입 요청 DTO
     */
    void signupUser(SignupRequestDto signupRequestDto);

    /**
     * 유저 정보 조회
     *
     * @param userId 유저 ID
     */
    UserDto findByUserId(String userId);
}