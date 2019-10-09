package com.mini.http.converter;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import javax.annotation.Nonnull;

import com.alibaba.fastjson.JSON;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BeanConverter<T> implements Converter<T> {
	private final Class<T> clazz;

	public BeanConverter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T apply(@Nonnull Call call, @Nonnull Response response) throws IOException {
		if (!response.isSuccessful()) throw new IOException(response.message());
		try (ResponseBody body = requireNonNull(response.body())) {
			String string = requireNonNull(body.string());
			return JSON.parseObject(string, clazz);
		}
	}
}
