package com.navarna.computerdb.ui;

public class CLIException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public CLIException(String message) {
		super(message);
	}

	public CLIException(Throwable cause) {
		super(cause);
	}

	public CLIException(String message, Throwable cause) {
		super(message, cause);
	}

	public CLIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
