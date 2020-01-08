package com.project.questionlibrary.service.inmp;

import com.project.questionlibrary.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2020/1/6 14:02
 * @version: 1.0
 * @description:
 */
@Service
public class RedisServiceImpl implements RedisService {
    private RedisTemplate redisTemplate;
    private StringRedisTemplate stringRedisTemplate;
    private RedisServiceImpl(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate){
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 读取上传的文档数据，并转换放入数据库
     */
    public void importQuestion(){


    }
}
