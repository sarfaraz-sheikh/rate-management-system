package com.dpworld.rms.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dpworld.rms.exception.InvalidDataException;
import com.dpworld.rms.exception.ResourceNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception exception) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Please contact admin");
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RateId not found in RMS");
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<?> handleResourceNotFoundException(InvalidDataException exception) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
}