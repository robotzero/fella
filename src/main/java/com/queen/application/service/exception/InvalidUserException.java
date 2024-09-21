package com.queen.application.service.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidUserException extends BadCredentialsException {

	public InvalidUserException(String msg) {
		super(msg);
	}
}
