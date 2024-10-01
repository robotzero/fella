package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.ExceptionDTO;
import com.queen.application.service.exception.ActivePeriodExistsException;
import com.queen.application.service.exception.DatabaseException;
import com.queen.application.service.exception.InvalidPeriodIdException;
import com.queen.application.service.exception.InvalidUserException;
import com.queen.application.service.exception.MonitorTypeException;
import com.queen.application.service.exception.PeriodUpdateException;
import com.queen.application.service.exception.UserServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ControllerException {
	final Log logger = LogFactory.getLog(this.getClass());
	@ExceptionHandler({MonitorTypeException.class, ActivePeriodExistsException.class, MethodNotAllowedException.class, InvalidPeriodIdException.class, PeriodUpdateException.class})
	public Mono<ResponseEntity<ExceptionDTO>> handleMonitorTypeException(final Exception exception) {
		logger.debug(exception);
		logger.error(exception.getMessage());
		return Mono.just(new ResponseEntity<>(
				new ExceptionDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value()), new HttpHeaders(), HttpStatus.BAD_REQUEST)
		);
	}

	@ExceptionHandler({Exception.class, UserServiceException.class, DatabaseException.class})
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

	@ExceptionHandler({DuplicateKeyException.class})
	public Mono<ResponseEntity<ExceptionDTO>> handleDuplicateKeyException(final Exception exception) {
		logger.error(exception);
		return Mono.just(new ResponseEntity<>(
				new ExceptionDTO("Invalid operation", HttpStatus.BAD_REQUEST.value()), new HttpHeaders(), HttpStatus.BAD_REQUEST)
		);
	}

	@ExceptionHandler({WebExchangeBindException.class})
	public Mono<ResponseEntity<ExceptionDTO>> handleValidationException(final Exception exception) {
		logger.debug(exception);
		logger.error(exception.getMessage());
		return Mono.just(new ResponseEntity<>(
				new ExceptionDTO("Missing data in the request", HttpStatus.BAD_REQUEST.value()), new HttpHeaders(), HttpStatus.BAD_REQUEST)
		);
	}
}
