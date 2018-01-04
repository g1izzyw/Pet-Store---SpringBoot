package com.petstore.exceptions;

public class FailedToReadAllCategoriesException extends FailedToReadAllException {

	private static final long serialVersionUID = 1182251717975097501L;
	
	public FailedToReadAllCategoriesException() {
		super("Failed to retrieve list of categories");
	}

	@Override
	public String getType(){
		return "Category";
	}
	
}
