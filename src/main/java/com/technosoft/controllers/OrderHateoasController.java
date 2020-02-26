package com.technosoft.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technosoft.entities.Order;
import com.technosoft.entities.User;
import com.technosoft.exceptions.UserNotFoundException;
import com.technosoft.repositories.OrderRepository;
import com.technosoft.repositories.UserRepository;

@RestController
@RequestMapping(value = "/hateoas/users")
public class OrderHateoasController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@GetMapping(path = "/{userId}/orders")
	public Resources<Order> getAllOrders(@PathVariable Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent())
			throw new UserNotFoundException("User Not Found");

		List<Order> allOrders = userOptional.get().getOrders();
		Resources<Order> finalResource = new Resources<Order>(allOrders);

		return finalResource;
	}

}
