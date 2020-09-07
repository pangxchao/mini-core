package com.mini.core.mvc.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;

import static com.mini.core.mvc.R.BAD_REQUEST;
import static java.util.ResourceBundle.getBundle;

/**
 * JSON类型的数据实现
 *
 * @author xchao
 */
public class JsonModel implements Serializable {
    private final Map<String, Object> mapData = new HashMap<>();
    private final List<Object> listData = new ArrayList<>();
    private String message;
    private int code = 0;
    private Object data;

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public JsonModel setData(Object data) {
        this.data = data;
        return this;
    }

    public JsonModel putAll(@NotNull Map<String, Object> map) {
        this.data = this.mapData;
        this.mapData.putAll(map);
        return this;
    }

    public JsonModel put(@NotNull String name, Object value) {
        this.mapData.put(name, value);
        this.data = this.mapData;
        return this;
    }

    public JsonModel addAll(Collection<Object> collection) {
        this.listData.addAll(collection);
        this.data = this.listData;
        return this;
    }

    public JsonModel add(Object value) {
        this.data = this.listData;
        this.listData.add(value);
        return this;
    }

    public JsonModel set(int index, Object value) {
        this.listData.set(index, value);
        this.data = listData;
        return this;
    }

    public JsonModel error(int code, @NotNull String message, @NotNull Locale locale) {
        ResourceBundle bundle = getBundle("ValidationMessages", locale);
        this.message = bundle.getString(message);
        this.code = code;
        return this;
    }

    public JsonModel error(@NotNull String message, @NotNull Locale locale) {
        return error(BAD_REQUEST, message, locale);
    }

    public JsonModel error(int code, String message) {
        this.message = message;
        this.code = code;
        return this;
    }

    public JsonModel error(String message) {
        return error(BAD_REQUEST, message);
    }

    public JsonModel ok(String message) {
        this.message = message;
        this.code = 0;
        return this;
    }

    public JsonModel ok() {
        return ok("Success");
    }
}