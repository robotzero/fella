package com.queen.application.service.exception;

public class UserServiceException extends RuntimeException {
	public UserServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
