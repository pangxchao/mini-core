package com.mini.http.callback;

import java.io.IOException;

@FunctionalInterface
public interface Callback<T> {
	void accept(T t) throws IOException;
}
