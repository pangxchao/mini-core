package com.mini.core.validation;

import java.util.List;

import static java.util.Arrays.asList;

public final class ValidationException extends RuntimeException {
	private static final long serialVersionUID = -1L;
	private final List<Object> args;
	private final int status;
	
	public ValidationException(int status, String message, Object... args) {
		super(message);
		this.status = status;
		this.args = asList(args);
	}
	
	public final List<Object> getArgs() {
		return args;
	}
	
	@Override
	public final String getMessage() {
		return super.getMessage();
	}
	
	public final int getStatus() {
		return status;
	}
	
	public final String getKey() {
		return "" + status;
	}
}
