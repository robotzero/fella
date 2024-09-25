package com.queen.application.service.exception;

public class ActivePeriodExistsException extends RuntimeException {
	public ActivePeriodExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
