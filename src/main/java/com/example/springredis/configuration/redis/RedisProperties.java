package com.example.springredis.configuration.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data

@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    @Value(value = "${redis.hostname}")
    private String redisHostname;

    @Value(value = "${redis.port}")
    private int redisPort;

    @Value(value = "${redis.time-to-live}")
    private int timeToLive;
}
