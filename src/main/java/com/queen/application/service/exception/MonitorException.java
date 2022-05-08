package com.queen.application.service.exception;

public class MonitorException extends RuntimeException {
	public MonitorException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
