package com.technosoft.controllers;

import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.technosoft.entities.User;
import com.technosoft.entities.Views;
import com.technosoft.exceptions.UserNotFoundException;
import com.technosoft.services.UserService;

@RestController
@Validated
@RequestMapping(value = "/jsonview/users")
public class UserJsonViewController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "/external/{id}")
	@JsonView(Views.External.class)
	public Optional<User> getUserById(@PathVariable @Min(1) Long id) {
		try {
			return userService.getUserById(id);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@GetMapping(path = "/internal/{id}")
	@JsonView(Views.Internal.class)
	public Optional<User> getUserById2(@PathVariable @Min(1) Long id) {
		try {
			return userService.getUserById(id);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

}
