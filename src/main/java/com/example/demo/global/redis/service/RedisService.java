package com.example.demo.global.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setValues(String key, String data){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key,data);
    }

    public void setValues(String key, String data, Duration duration){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key,data,duration);
    }

    public void deleteValues(String key){
        redisTemplate.delete(key);
    }

    public String getValues(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return "false";
        }

        return (String) values.get(key);
    }

    public boolean checkExistsValue(String value){
        return value.equals("false");
    }
}
