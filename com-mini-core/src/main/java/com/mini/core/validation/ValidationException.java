package com.mini.core.validation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

public final class ValidationException extends RuntimeException {
	private static final long serialVersionUID = -1L;
	private final List<Object> args;
	private final int status;
	
	public ValidationException(int status, String message, List<Object> args) {
		super(defaultIfBlank(message, format("UnKnown Error:%d", status)));
		this.args = ofNullable(args).orElse(new ArrayList<>());
		this.status = status;
	}
	
	@Nonnull
	public final List<Object> getArgs() {
		return args;
	}
	
	@Nonnull
	@Override
	public final String getMessage() {
		return super.getMessage();
	}
	
	public final int getStatus() {
		return status;
	}
	
	@Nonnull
	public final String getKey() {
		return "" + status;
	}
}
