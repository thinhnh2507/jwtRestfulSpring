package com.service;

import java.util.Optional;

import com.model.User;

public interface UserService {
	User findByEmail(String email);

	Optional<User> findById(Long id);

	void updateUser(User user);

	void createUser(User user);

	void deletedUser(Long id);
}
