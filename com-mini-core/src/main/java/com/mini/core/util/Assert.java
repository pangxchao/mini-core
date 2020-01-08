package com.mini.core.util;

import org.apache.commons.lang3.Validate;

import static java.lang.String.format;

public class Assert extends Validate {
	
	public static void error(String message, Object... args) {
		throw new IllegalArgumentException(format(message, args));
	}
	
	public static void error() {
		throw new IllegalArgumentException();
	}
}
