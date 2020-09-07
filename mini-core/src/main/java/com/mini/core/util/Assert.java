package com.mini.core.util;

import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.util.EventListener;

import static java.lang.String.format;

public final class Assert extends Validate implements EventListener, Serializable {
	
	public static void error(String message, Object... args) throws IllegalArgumentException {
		throw new IllegalArgumentException(format(message, args));
	}
	
	public static void error() throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}
}
