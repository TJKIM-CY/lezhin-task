package com.lezhintask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * API 요청에 대한 Rate Limiting을 적용하는 인터셉터
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiter rateLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!rateLimiter.tryConsume()) {
            response.setStatus(429);
            return false;
        }
        return true;
    }
}