package com.technosoft.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technosoft.entities.Order;
import com.technosoft.entities.User;
import com.technosoft.exceptions.UserNotFoundException;
import com.technosoft.repositories.OrderRepository;
import com.technosoft.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@GetMapping(path = "/{userId}/orders")
	public List<Order> getAllOrders(@PathVariable Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent())
			throw new UserNotFoundException("User Not Found");

		return user.get().getOrders();
	}

	@PostMapping(path = "/{userId}/orders")
	public Order createOrder(@PathVariable Long userId, @RequestBody Order order) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent())
			throw new UserNotFoundException("User Not Found");

		User user = userOptional.get();
		order.setUser(user);
		return orderRepository.save(order);
	}
	
	@GetMapping(path = "/{userId}/orders/{orderId}")
	public void getOrderByOrderId(@PathVariable Long userId, @PathVariable Long orderId) {
		
	}

}
