package com.mini.util.http.call;

import com.mini.util.http.builder.BaseBuilder;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * PATCH 方式提交
 *
 * @param <T>
 */
public class HttpPatchCall<T> extends HttpCall<T> {
    public HttpPatchCall(BaseBuilder builder, RequestBody body) {
        super(builder, body);
    }

    @Override
    protected Request getRequest(BaseBuilder builder, RequestBody body) {
        return builder.getBuilder().post(new ProgressRequestBody(body)).build();
    }
}
