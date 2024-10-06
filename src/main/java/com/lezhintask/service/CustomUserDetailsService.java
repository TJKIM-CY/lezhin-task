package com.lezhintask.service;

import com.lezhintask.dto.UserDto;
import com.lezhintask.mapper.UserMapper;
import com.lezhintask.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 유저 세부 정보 서비스 구현
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 유저 세부 정보 로드
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 유저 정보를 데이터베이스에서 로드
        UserDto user = userMapper.findByUserId(userId);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }

        // CustomUserDetails 객체로 변환
        return new CustomUserDetails(user.getUserId(), user.getPassword(), user.isAdult(), new ArrayList<>());
    }
}