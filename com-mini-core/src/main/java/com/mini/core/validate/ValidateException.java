package com.mini.core.validate;

import java.util.List;

import static java.util.Arrays.asList;

public final class ValidateException extends RuntimeException {
	private static final long serialVersionUID = -1L;
	private final List<Object> args;
	private final int status;
	
	public ValidateException(int status, String message, Object... args) {
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
