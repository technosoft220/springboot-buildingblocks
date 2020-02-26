package com.technosoft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.technosoft.entities.User;
import com.technosoft.exceptions.UserAlreadyExistsException;
import com.technosoft.exceptions.UserNotFoundException;
import com.technosoft.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User createUser(User user) throws UserAlreadyExistsException {
		String username = user.getUserName();
		User existingUser = userRepository.findByUserName(username);
		if (existingUser != null) {
			throw new UserAlreadyExistsException(
					"User with username " + username + " already exists in the repository");
		}
		return userRepository.save(user);
	}

	public Optional<User> getUserById(Long id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User with Id " + id + " not exists in the repository");
		}
		return user;
	}

	public User updateUserById(Long id, User user) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(
					"User with Id " + id + " not exists in the repository, please provide correct user id");
		}
		user.setUserId(id);
		return userRepository.save(user);
	}

	public void deleteUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"User with Id " + id + " not exists in the repository, please provide correct user id");
		}

		userRepository.deleteById(id);

	}

	public User getUserByUsername(String username) {
		return userRepository.findByUserName(username);
	}
}
