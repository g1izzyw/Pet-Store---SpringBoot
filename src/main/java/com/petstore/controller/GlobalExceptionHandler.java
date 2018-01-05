package com.petstore.controller;

import java.util.UUID;

import org.springframework.beans.TypeMismatchException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petstore.exceptions.FailedToReadAllException;
import com.petstore.exceptions.InsufficientPetInformationException;
import com.petstore.exceptions.PetNotFoundException;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class GlobalExceptionHandler {

	@ExceptionHandler(PetNotFoundException.class)
	public ResponseEntity<VndErrors> petNotFoundException(final PetNotFoundException e) {
		return new ResponseEntity<VndErrors>(new VndErrors(UUID.randomUUID().toString(), e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TypeMismatchException.class)
	public ResponseEntity<VndErrors> typeMismatchException(final TypeMismatchException e) {
		return new ResponseEntity<VndErrors>(new VndErrors(UUID.randomUUID().toString(), e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FailedToReadAllException.class)
	public ResponseEntity<VndErrors> failedToReadAllException(final FailedToReadAllException e) {
		return new ResponseEntity<VndErrors>(new VndErrors(UUID.randomUUID().toString(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InsufficientPetInformationException.class)
	public ResponseEntity<VndErrors> insufficientPetInformationException(final InsufficientPetInformationException e) {
		return new ResponseEntity<VndErrors>(new VndErrors(UUID.randomUUID().toString(), e.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
	}
	
}
