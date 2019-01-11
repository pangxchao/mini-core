package com.mini.util.http.call;

import com.mini.util.http.Converter;
import com.mini.util.http.builder.BaseBuilder;
import okhttp3.Request;

/**
 * PUT 方式提交
 *
 * @param <T>
 */
public class HttpPutCall<T> extends HttpCall<T> {
    public HttpPutCall(BaseBuilder builder, Converter<T> converter) {
        super(builder, converter);
    }

    @Override
    protected Request getRequest(BaseBuilder builder) {
        return builder.getBuilder().post(new ProgressRequestBody(builder.getRequestBody())).build();
    }
}
