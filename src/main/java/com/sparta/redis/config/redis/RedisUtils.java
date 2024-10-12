package com.sparta.redis.config.redis;

import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Getter
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Memo 객체를 저장
    public void setData(String key, Object value, Long expiredTime) {
        // expiredTime이 null인 경우 기본 만료 시간 설정 (5분)
        if (expiredTime == null) {
            expiredTime = 300000L; // 기본 5분 (300,000 밀리초)
        }
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    // Memo 객체를 가져옴
    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 데이터를 삭제
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
