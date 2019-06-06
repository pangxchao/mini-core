package com.mini.util.validate;

public class ValidateException extends RuntimeException {
    private static final long serialVersionUID = 5797524882646866674L;

    private final int error;

    public ValidateException(int error) {
        this(error, (String) null);
    }

    public ValidateException(int error, String message) {
        this(error, message, null);
    }

    public ValidateException(int error, Throwable cause) {
        super(cause);
        this.error = error;
    }


    public ValidateException(int error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }


    public int getError() {
        return error;
    }
}
