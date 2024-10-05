package com.lezhintask.service;

import com.lezhintask.dto.SignupRequestDto;

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
}