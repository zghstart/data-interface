package com.zetyun.tiger.datamock.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Autowired
    private RedisTemplate redisTemplate;

    public RedisConfig() {
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplateInit() {
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new StringRedisSerializer());
        return this.redisTemplate;
    }
}
