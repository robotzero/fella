package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.ExceptionDTO;
import com.queen.application.service.exception.MonitorTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ControllerException {

	@ExceptionHandler({MonitorTypeException.class})
	public Mono<ResponseEntity<ExceptionDTO>> handleMonitorTypeException(final Exception exception) {
		return Mono.just(new ResponseEntity<>(
				new ExceptionDTO(exception.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST)
		);
	}

	@ExceptionHandler({Exception.class})
	public Mono<ResponseEntity<ExceptionDTO>> handleGenericException(final Exception exception) {
		return Mono.just(new ResponseEntity<>(
				new ExceptionDTO("Error"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
		);
	}
}
