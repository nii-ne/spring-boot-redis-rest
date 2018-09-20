package com.example.springredis.services;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.NotFoundException;

import com.example.springredis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springredis.entities.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> findByPattern(final String pattern) {
		return userRepository.findByPattern(pattern);
	}

	public User findById(final String userId) {

		final User user = userRepository.findById(UUID.fromString(userId).toString());
		if(user == null) {
			throw new NotFoundException("User does not exist in the DB");
		}
		return user;
	}

	public void save(final User user) {
		user.setId(UUID.randomUUID().toString());
		userRepository.save(user);
	}

	public void update(final User user) {
		userRepository.update(user);
	}

	public void delete(final String userId) {
		userRepository.delete(UUID.fromString(userId).toString());
	}
}
