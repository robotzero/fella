package com.queen.domain.validation;

public class ItemExistsException extends RuntimeException {
	public ItemExistsException(final String message) {
		super(message);
	}
}
