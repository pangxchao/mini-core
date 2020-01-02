package com.mini.core.http.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Objects;

public class JSONArrayConverter implements Converter<JSONArray> {
	public static final JSONArrayConverter INSTANCE = new JSONArrayConverter();

	@Override
	public JSONArray apply(@Nonnull Call call, @Nonnull Response response) throws IOException {
		if (!response.isSuccessful()) throw new IOException(response.message());
		try (ResponseBody body = Objects.requireNonNull(response.body())) {
			String string = Objects.requireNonNull(body.string());
			return JSON.parseArray(string);
		}
	}
}
