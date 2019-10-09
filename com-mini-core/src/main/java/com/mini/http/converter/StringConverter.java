package com.mini.http.converter;

import java.io.IOException;
import java.util.Objects;

import javax.annotation.Nonnull;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class StringConverter implements Converter<String> {
	public static final StringConverter INSTANCE = new StringConverter();

	@Override
	public String apply(@Nonnull Call call, @Nonnull Response response) throws IOException {
		if (!response.isSuccessful()) throw new IOException(response.message());
		try (ResponseBody body = Objects.requireNonNull(response.body())) {
			return body.string();
		}
	}

}
