package com.target.myretail.exception;

public class PersistentException extends Exception {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3607021190566004259L;

	public PersistentException() {
		super();
	}

	public PersistentException(Exception e) {
		super(e);
	}

	public PersistentException(String message) {
		super(message);
	}

	public PersistentException(String message, Throwable cause) {
		super(message, cause);
	}

}

