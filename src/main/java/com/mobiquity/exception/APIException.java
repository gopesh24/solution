package com.mobiquity.exception;

/**
 * Exception class used when an Exception is raised.
 */
public class APIException extends RuntimeException {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -3062609275349917531L;

	/**
	 * @param message the exception message
	 */
	public APIException(String message) {
		super(message);
	}

	/**
	 * @param message the exception message
	 * @param cause   the cause
	 */
	public APIException(String message, Throwable cause) {
		super(message, cause);
	}
}
