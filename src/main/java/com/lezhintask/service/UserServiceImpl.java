package com.lezhintask.service;

import com.lezhintask.dto.SignupRequestDto;
import com.lezhintask.mapper.UserMapper;
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
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        signupRequestDto.setPassword(encodedPassword);

        // 유저 정보 저장
        userMapper.insertUser(signupRequestDto);
    }
}