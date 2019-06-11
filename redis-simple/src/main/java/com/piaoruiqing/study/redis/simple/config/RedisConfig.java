package com.piaoruiqing.study.redis.simple.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.piaoruiqing.study.redis.simple.serializer.GenericRedisKeySerializer;

/**
 * @author piaoruiqing
 * @description:
 * @date: 2019-06-10 22:51
 * @since JDK 1.8
 */
@Configuration
public class RedisConfig {

    @Value("${spring.application.name:undefined}")
    private String applicationName;

    /**
     * StringRedisTemplate
     * @param factory
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {

        StringRedisTemplate template = new StringRedisTemplate(factory);
//        StringRedisSerializer serializer = new StringRedisSerializer();
        GenericRedisKeySerializer serializer = new GenericRedisKeySerializer(applicationName);
        template.setKeySerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        return template;
    }


}
