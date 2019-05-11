package com.biubiu.user.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author yule.zhang
 * @date 2019/5/10 23:09
 * @email zhangyule1993@sina.com
 * @description 客户端
 */

@Component
public class RedisClient {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, int timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public void expire(String key, int timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
}
