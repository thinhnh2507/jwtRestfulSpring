package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.User;
import com.repository.UserRepository;
@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void createUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void deletedUser(Long id) {
		User user = userRepository.findUserById(id);
		user.setIsDeleted("Y");
		userRepository.save(user);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

}
