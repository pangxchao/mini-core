package com.mini.util.http.builder;


import com.mini.util.http.HttpRequest;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public final class FormBuilder extends BaseBuilder {
    private final FormBody.Builder builder;

    public FormBuilder(HttpRequest request) {
        super(request);
        builder = new FormBody.Builder();
    }


    public FormBuilder add(String name, Object value) {
        builder.add(name, value == null ? "" : String.valueOf(value));
        return this;
    }

    public FormBuilder addEncoded(String name, Object value) {
        builder.addEncoded(name, value == null ? "" : String.valueOf(value));
        return this;
    }

    @Override
    public RequestBody getRequestBody() {
        return builder.build();
    }
}
