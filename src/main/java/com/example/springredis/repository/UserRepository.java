package com.example.springredis.repository;

import com.example.springredis.entities.User;

import java.util.List;

public interface UserRepository {
    List<User> findByPattern(final String pattern);
    User findById(final String userId);
    void update(final User user);
    void delete(final String userId);
    void save(final User user);
}
