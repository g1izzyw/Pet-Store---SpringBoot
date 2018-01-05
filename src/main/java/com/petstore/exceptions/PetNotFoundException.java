package com.petstore.exceptions;

public class PetNotFoundException extends Exception {

	private static final long serialVersionUID = 2733695839502196475L;

	public PetNotFoundException(Long petId) {
		super(String.format("Pet not found with id: %d", petId));
	}
	
}
