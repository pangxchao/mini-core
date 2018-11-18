package com.mini.util.http;

import okhttp3.Call;
import okhttp3.Response;

public interface Converter<T> {
    T apply(Call call, Response response);
}
