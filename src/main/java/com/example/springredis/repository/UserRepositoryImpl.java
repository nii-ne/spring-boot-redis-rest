package com.example.springredis.repository;

import com.example.springredis.configuration.redis.RedisProperties;
import com.example.springredis.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String REDIS_PREFIX_USERS = "users";

    private static final String REDIS_KEYS_SEPARATOR = ":";

    @Autowired
    private RedisTemplate<String, User> userTemplate;
    @Autowired
    private RedisProperties redisProperties;

    @Override
    public List<User> findByPattern(String pattern) {
        return this.getValueOperations().multiGet(userTemplate.keys(getRedisKey(pattern)));
    }

    @Override
    public User findById(String userId) {
        return this.getValueOperations().get(getRedisKey(userId));
    }

    @Override
    public void update(User user) {
        getValueOperations().set(getRedisKey(user.getId()), user,redisProperties.getTimeToLive(), TimeUnit.SECONDS);
    }

    @Override
    public void delete(String userId) {
        if(!userTemplate.delete(getRedisKey(userId))) {
            throw new NotFoundException("User does not exist in the DB");
        }
    }

    @Override
    public void save(User user) {
        getValueOperations().set(getRedisKey(user.getId()), user,redisProperties.getTimeToLive(), TimeUnit.SECONDS);
    }

    private String getRedisKey(final String userId) {
        return REDIS_PREFIX_USERS + REDIS_KEYS_SEPARATOR + userId;
    }

    private ValueOperations<String, User> getValueOperations() {
        return userTemplate.opsForValue();
    }
}
