package com.mini.core.mvc.model;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

import static java.util.Objects.requireNonNullElse;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@SuppressWarnings("UnusedReturnValue")
public abstract class IModel<D, T extends IModel<D, T>> implements Serializable {
    public static final String MODEL_KEY = IModel.class.getName() + ".key";
    private final HttpHeaders headers = new HttpHeaders();
    private final HttpServletResponse response;
    private HttpStatus status = HttpStatus.OK;
    private final HttpServletRequest request;
    private String message;
    private Integer code;

    protected IModel(HttpServletRequest request, HttpServletResponse response, MediaType mediaType) {
        this.headers.setContentType(mediaType);
        this.response = response;
        this.request = request;
    }

    protected abstract T getThis();

    public final HttpServletResponse getResponse() {
        return response;
    }

    public final HttpServletRequest getRequest() {
        return request;
    }

    @NotNull
    public final HttpHeaders getHeaders() {
        return headers;
    }

    @NotNull
    public final HttpStatus getStatus() {
        return requireNonNullElse(status, INTERNAL_SERVER_ERROR);
    }

    public final String getMessage() {
        return message;
    }

    public final Integer getCode() {
        return code;
    }

    protected abstract String getDispatcherPath();

    public final void show() throws ServletException, IOException {
        final String path = IModel.this.getDispatcherPath();
        final HttpServletRequest request = getRequest();
        var rd = request.getRequestDispatcher(path);
        rd.forward(request, getResponse());
    }

    public T setStatus(@NotNull HttpStatus status) {
        this.status = status;
        return getThis();
    }

    public T setMessage(String message) {
        this.message = message;
        return getThis();
    }

    public T setCode(Integer code) {
        this.code = code;
        return getThis();
    }

    @NotNull
    public abstract D build();

}
