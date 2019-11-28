package com.project.redis.service.impl;

import com.project.redis.service.ObjectRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @version: V1.0
 * @author: liu zhenming
 * @Email: 1119264845@qq.com
 * @Date: 2018-07-27 11:36
 */
@Service
public class ObjectRedisServiceImpl implements ObjectRedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setKeyValueExpire(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key).booleanValue();
    }
}
