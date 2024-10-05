package com.lezhintask.mapper;

import com.lezhintask.dto.SignupRequestDto;
import com.lezhintask.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 유저 관련 매퍼 인터페이스
 */
@Mapper
public interface UserMapper {

    /**
     * 유저 정보 저장
     *
     * @param signupRequestDto 회원가입 요청 DTO
     */
    void insertUser(SignupRequestDto signupRequestDto);

    /**
     * 유저 정보 조회
     *
     * @param userId 유저 ID
     * @return 유저 정보 DTO
     */
    UserDto findByUserId(String userId);
}