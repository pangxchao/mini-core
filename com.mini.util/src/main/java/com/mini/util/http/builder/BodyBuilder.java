package com.mini.util.http.builder;
import java.io.File;

import com.mini.util.http.HttpRequest;
import com.mini.util.lang.FileUtil;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.ByteString;

public final class BodyBuilder extends BaseBuilder {
    private RequestBody requestBody;

    public BodyBuilder(HttpRequest request) {
        super(request);
    }

    public BodyBuilder setBody(MediaType mediaType, File file) {
        requestBody = RequestBody.create(mediaType, file);
        return this;
    }

    public BodyBuilder setBody(MediaType mediaType, byte[] content) {
        requestBody = RequestBody.create(mediaType, content);
        return this;
    }

    public BodyBuilder setBody(MediaType mediaType, String content) {
        requestBody = RequestBody.create(mediaType, content);
        return this;
    }

    public BodyBuilder setBody(MediaType mediaType, ByteString content) {
        requestBody = RequestBody.create(mediaType, content);
        return this;
    }

    public BodyBuilder setBody(MediaType mediaType, byte[] content, int offset, int byteCount) {
        requestBody = RequestBody.create(mediaType, content, offset, byteCount);
        return this;
    }

    public BodyBuilder setBody(File file) {
        return setBody(MediaType.parse(FileUtil.getMiniType(file)), file);
    }

    public BodyBuilder setJsonBody(String jsonString) {
        return setBody(MediaType.parse("application/json;charset=utf-8"), jsonString);
    }

    public BodyBuilder setJsonBody(String jsonString, String charset) {
        return setBody(MediaType.parse("application/json;charset=" + charset), jsonString);
    }

    @Override
    public RequestBody getRequestBody() {
        return requestBody == null ? new FormBody.Builder().build() : requestBody;
    }
}
