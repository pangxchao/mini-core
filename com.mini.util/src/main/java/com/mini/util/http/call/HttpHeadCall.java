package com.mini.util.http.call;

import com.mini.util.http.Converter;
import com.mini.util.http.builder.BaseBuilder;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * HEAD 方式无法处理上传过程的进度
 *
 * @param <T>
 */
public class HttpHeadCall<T> extends HttpCall<T> {
    public HttpHeadCall(BaseBuilder builder, Converter<T> converter) {
        super(builder, converter);
    }

    @Override
    protected Request getRequest(BaseBuilder builder) {
        RequestBody body = builder.getRequestBody();
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            for (int i = 0; i < formBody.size(); i++) {
                builder.getBuilder().header(formBody.name(i), formBody.value(i));
            }
        }
        return builder.getBuilder().head().build();
    }
}
