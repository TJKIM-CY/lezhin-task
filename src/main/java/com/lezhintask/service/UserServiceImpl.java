package com.lezhintask.service;

import com.lezhintask.dto.SignupRequestDto;
import com.lezhintask.dto.UserDto;
import com.lezhintask.mapper.UserMapper;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 유저 관련 서비스 구현
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 유저 회원가입
     *
     * @param signupRequestDto 회원가입 요청 DTO
     */
    @Override
    public void signupUser(SignupRequestDto signupRequestDto) {
        // 파라미터 검증
        if (StringUtils.isEmpty(signupRequestDto.getUserId()) || StringUtils.isEmpty(signupRequestDto.getPassword())) {
            throw new IllegalArgumentException("파라미터 값 누락");
        }

        // ID 중복 체크
        if (userMapper.findByUserId(signupRequestDto.getUserId()) != null) {
            throw new IllegalArgumentException("중복 아이디" + signupRequestDto.getUserId());
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        signupRequestDto.setPassword(encodedPassword);

        // 유저 정보 저장
        userMapper.insertUser(signupRequestDto);
    }

    /**
     * 유저 정보 조회
     *
     * @param userId 유저 ID
     */
    @Override
    public UserDto findByUserId(String userId) {
        // 유저 정보 조회
        UserDto userInfo = userMapper.findByUserId(userId);

        return userInfo;
    }
}