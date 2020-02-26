package com.technosoft.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.technosoft.entities.Order;
import com.technosoft.entities.User;
import com.technosoft.exceptions.UserNotFoundException;
import com.technosoft.repositories.UserRepository;
import com.technosoft.services.UserService;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@GetMapping(path = "/{id}")
	public Resource<User> getUserById(@PathVariable @Min(1) Long id) {
		try {
			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			Long userId = user.getUserId();
			Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
			Resource<User> finalResource = new Resource<User>(user, selfLink);
			return finalResource;
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@GetMapping
	public Resources<User> getAllUsers() {
		List<User> userList = userService.getAllUsers();
		for (User user : userList) {
			Long userId = user.getUserId();
			Link link = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
			user.add(link);

			// Relationship link with getAllOrders
			Resources<Order> orders = ControllerLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userId);
			Link ordersLink = ControllerLinkBuilder.linkTo(orders).withRel("all-orders");
			user.add(ordersLink);

		}

		// self link for getAllUser
		Link linkAllUsers = ControllerLinkBuilder.linkTo(this.getClass()).withSelfRel();
		Resources<User> finalResource = new Resources<User>(userList, linkAllUsers);
		return finalResource;

	}

}
