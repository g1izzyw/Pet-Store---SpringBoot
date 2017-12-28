package com.petstore.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petstore.exceptions.PetNotFoundException;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class GlobalExceptionHandler {

	@ExceptionHandler(PetNotFoundException.class)
	public ResponseEntity<VndErrors> notFoundException(final PetNotFoundException e) {
		return new ResponseEntity<VndErrors>(new VndErrors(e.getPetId().toString(), e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TypeMismatchException.class)
	public ResponseEntity<VndErrors> notFoundException(final TypeMismatchException e) {
		return new ResponseEntity<VndErrors>(new VndErrors(e.getErrorCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
}
