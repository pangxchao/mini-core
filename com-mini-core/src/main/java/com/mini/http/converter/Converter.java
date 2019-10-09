package com.mini.http.converter;

import java.io.IOException;

import javax.annotation.Nonnull;

import okhttp3.Call;
import okhttp3.Response;

@FunctionalInterface
public interface Converter<T> {
	/**
	 * 数据转换方法
	 * @param call     调用器
	 * @param response Response 对象
	 * @return 转换后的数据对象
	 * @throws IOException 异常对象
	 */
	T apply(@Nonnull Call call, @Nonnull Response response) throws IOException;

}
