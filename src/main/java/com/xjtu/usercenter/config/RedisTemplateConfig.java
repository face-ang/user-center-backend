package com.xjtu.usercenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisTemplateConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 自定义temolate最少需要下面两个配置
        redisTemplate.setConnectionFactory(connectionFactory);  // 定义连接工厂（使用参数自动注入）
        redisTemplate.setKeySerializer(RedisSerializer.string());// 自定义序列化器
        return redisTemplate;
    }

}
