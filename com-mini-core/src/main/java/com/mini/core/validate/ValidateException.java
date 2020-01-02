package com.mini.core.validate;

public class ValidateException extends RuntimeException {
	private static final long serialVersionUID = 5797524882646866674L;

	private final int status;

	public ValidateException(int status) {
		this(status, (String) null);
	}

	public ValidateException(int status, String message) {
		this(status, message, null);
	}

	public ValidateException(int status, Throwable cause) {
		this(status, null, cause);
	}

	public ValidateException(int status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
