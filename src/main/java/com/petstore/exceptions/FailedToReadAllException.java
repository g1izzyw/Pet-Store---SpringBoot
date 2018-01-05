package com.petstore.exceptions;

public abstract class FailedToReadAllException extends Exception {

	private static final long serialVersionUID = 8282298405483330427L;

	public FailedToReadAllException(String msg) {
		super(msg);
	}
	
}
