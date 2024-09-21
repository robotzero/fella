package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.ExceptionDTO;
import com.queen.application.service.exception.InvalidUserException;
import com.queen.application.service.exception.MonitorTypeException;
import com.queen.application.service.exception.UserServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ControllerException {
	final Log logger = LogFactory.getLog(this.getClass());
	@ExceptionHandler({MonitorTypeException.class})
	public Mono<ResponseEntity<ExceptionDTO>> handleMonitorTypeException(final Exception exception) {
		return Mono.just(new ResponseEntity<>(
				new ExceptionDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value()), new HttpHeaders(), HttpStatus.BAD_REQUEST)
		);
	}

	@ExceptionHandler({Exception.class, UserServiceException.class})
	public Mono<ResponseEntity<ExceptionDTO>> handleGenericException(final Exception exception) {
		logger.error(exception);
		return Mono.just(new ResponseEntity<>(
				new ExceptionDTO("Error", HttpStatus.INTERNAL_SERVER_ERROR.value()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
		);
	}

	@ExceptionHandler({InvalidUserException.class})
	public Mono<ResponseEntity<ExceptionDTO>> handleInvalidUserException(final Exception exception) {
		logger.error(exception);
		return Mono.just(new ResponseEntity<>(
				new ExceptionDTO("Unauthorized", HttpStatus.UNAUTHORIZED.value()), new HttpHeaders(), HttpStatus.UNAUTHORIZED)
		);
	}
}
