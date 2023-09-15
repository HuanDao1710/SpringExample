package com.falcon.view.boomer.core.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ViewBoomerException.class)
	public ResponseEntity<Object> handleViewBoomerException(ViewBoomerException exception, WebRequest request) {
		return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException exception, WebRequest request) {
		return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String messageEx = ex.getBindingResult().getAllErrors()
				.stream().map(item -> {
					String field = ((FieldError) item).getField();
					String message = item.getDefaultMessage();
					return String.format("%s: %s", field, message);
				}).collect(Collectors.joining(", "));

		return handleExceptionInternal(ex, ExceptionResponseDTO.of(status, messageEx, new Date()), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity
				.status(status)
				.headers(headers)
				.body(Objects.nonNull(body) ? body : ExceptionResponseDTO.of(status, ex.getMessage(), new Date()));
	}
}
