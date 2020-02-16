package com.technosoft.exceptions;

public class UserNameNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNameNotFoundException(String message) {
		super(message);
	}

}
