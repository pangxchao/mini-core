package com.mini.util.http.call;

import com.mini.util.http.builder.BaseBuilder;
import com.mini.util.lang.StringUtil;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * GET方式无法处理上传进度
 *
 * @param <T>
 */
public class HttpGetCall<T> extends HttpCall<T> {

    public HttpGetCall(BaseBuilder builder, RequestBody body) {
        super(builder, body);
    }

    @Override
    protected Request getRequest(BaseBuilder builder, RequestBody body) {
        if (body instanceof FormBody) {
            HttpUrl url = builder.getUrl();
            FormBody formBody = (FormBody) body;
            List<String> params = new ArrayList<>();
            for (int i = 0, l = url.querySize(); i < l; i++) {
                params.add(url.queryParameterName(i) + "=" + url.queryParameterValue(i));
            }
            for (int i = 0; i < formBody.size(); i++) {
                params.add(formBody.name(i) + "=" + formBody.value(i));
            }
            HttpUrl.Builder urlBuilder = url.newBuilder("?" + StringUtil.join("&", params));
            if (urlBuilder != null) builder.getRequest().url(urlBuilder.build());
        }
        return builder.getBuilder().build();
    }

}
