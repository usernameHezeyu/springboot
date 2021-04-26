package com.example.spring.security.spring.utils.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * @ClassName:RedisConfiguration
 * @Description: redis序列化
 * @Author:lxw
 * @Date:2019/9/15 9:40
 * @Version: 1.0
 **/
@Configuration
public class RedisConfiguration {
    @Bean
    @ConditionalOnMissingBean(name="redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException{
        RedisTemplate<Object, Object> template = new RedisTemplate<Object,Object>();
        template.setConnectionFactory(redisConnectionFactory);
        // key的序列化类型  保持的key简明易读
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException{
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
