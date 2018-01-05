package com.petstore.exceptions;

public class InsufficientPetInformationException extends Exception {

	private static final long serialVersionUID = 7278485620945950616L;

	public InsufficientPetInformationException() {
		super("Insufficient pet information provided");
	}
	
}
