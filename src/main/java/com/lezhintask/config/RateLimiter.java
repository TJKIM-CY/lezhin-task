package com.lezhintask.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Rate Limiting을 위한 구성 요소
 * Bucket4j를 사용하여 API 요청 수를 제한
 */
@Component
public class RateLimiter {

    private final Bucket bucket;

    /**
     * 1분 100 요청 제한
     */
    public RateLimiter() {
        Bandwidth limit = Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    /**
     * 요청 처리 할 수 있는지 확인
     */
    public boolean tryConsume() {
        return bucket.tryConsume(1);
    }
}