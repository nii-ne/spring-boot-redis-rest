package com.example.springredis.configuration;

import com.example.springredis.configuration.redis.RedisProperties;
import com.example.springredis.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@ComponentScan
@Configuration
public class RedisTemplateConfiguration {

    @Autowired
    private RedisProperties redisProperties;
    @Bean
    public RedisTemplate<String, User> userTemplate() {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getRedisHostname(), redisProperties.getRedisPort());
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }
}
