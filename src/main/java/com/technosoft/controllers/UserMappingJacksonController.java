package com.technosoft.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.technosoft.entities.User;
import com.technosoft.exceptions.UserNotFoundException;
import com.technosoft.services.UserService;

@RestController
@RequestMapping(value = "/jacksonfilter/user")
@Validated
public class UserMappingJacksonController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "/{id}")
	public MappingJacksonValue getUserById(@PathVariable @Min(1) Long id) {
		try {

			Set<String> fields = new HashSet<>();
			fields.add("userId");
			fields.add("userName");
			fields.add("ssn");

			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();

			FilterProvider filtersProvider = new SimpleFilterProvider().addFilter("userFilter",
					SimpleBeanPropertyFilter.filterOutAllExcept(fields));

			MappingJacksonValue jacksonValue = new MappingJacksonValue(user);

			jacksonValue.setFilters(filtersProvider);
			return jacksonValue;

		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@GetMapping(path = "/params/{id}")
	public MappingJacksonValue getUserById1(@PathVariable @Min(1) Long id, @RequestParam Set<String> fields) {
		try {

			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();

			FilterProvider filtersProvider = new SimpleFilterProvider().addFilter("userFilter",
					SimpleBeanPropertyFilter.filterOutAllExcept(fields));

			MappingJacksonValue jacksonValue = new MappingJacksonValue(user);

			jacksonValue.setFilters(filtersProvider);
			return jacksonValue;

		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

}
