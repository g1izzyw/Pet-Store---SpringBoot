package com.petstore.exceptions;

public class FailedToReadAllTagsException extends FailedToReadAllException {

	private static final long serialVersionUID = -509639326212682015L;

	public FailedToReadAllTagsException() {
		super("Failed to retrieve list of tags");
	}
}
