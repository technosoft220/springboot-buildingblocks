package com.technosoft.hello;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
/*import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;*/
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	private ResourceBundleMessageSource messageSource;

	// @RequestMapping(path = "/helloworld",method = RequestMethod.GET)
	@GetMapping("/helloWorld")
	public String helloWorld() {
		return "Hello World!";
	}

	@GetMapping("/helloWorld-bean")
	public UserDetails helloWorldbean() {
		return new UserDetails("Ansh", "Anand", "Bangalore");
	}

	@GetMapping("/hello-int")
	public String getI18NMessages(@RequestHeader(name = "Accept-Language", required = false) String locale) {
		return messageSource.getMessage("label.hello", null, new Locale(locale));
	}

	@GetMapping("/hello-int1")
	public String getI18NMessages2() {
		return messageSource.getMessage("label.hello", null, LocaleContextHolder.getLocale());
	}

}
