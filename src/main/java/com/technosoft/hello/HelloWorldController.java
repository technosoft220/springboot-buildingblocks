package com.technosoft.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	// @RequestMapping(path = "/helloworld",method = RequestMethod.GET)
	@GetMapping("helloWorld")
	public String helloWorld() {
		return "Hello World!";
	}

	@GetMapping("helloWorld-bean")
	public UserDetails helloWorldbean() {
		return new UserDetails("Ansh", "Anand", "Bangalore");
	}

}
