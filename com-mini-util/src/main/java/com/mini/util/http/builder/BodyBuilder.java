package com.mini.util.http.builder;

import com.mini.util.lang.FileUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.ByteString;

import java.io.File;

public final class BodyBuilder<V> extends AbstractBuilder<BodyBuilder<V>, V> {
    private RequestBody requestBody;

    @Override
    protected RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    protected BodyBuilder<V> getSelf() {
        return this;
    }


    public BodyBuilder<V> setBody(MediaType mediaType, String content) {
        requestBody = RequestBody.create(mediaType, content);
        return getSelf();
    }

    public BodyBuilder<V> setBody(MediaType mediaType, ByteString content) {
        requestBody = RequestBody.create(mediaType, content);
        return getSelf();
    }

    public BodyBuilder<V> setBody(MediaType mediaType, byte[] content) {
        requestBody = RequestBody.create(mediaType, content);
        return getSelf();
    }

    public BodyBuilder<V> setBody(MediaType mediaType, byte[] content, int offset, int byteCount) {
        requestBody = RequestBody.create(mediaType, content, offset, byteCount);
        return getSelf();
    }

    public BodyBuilder<V> setBody(MediaType mediaType, File file) {
        requestBody = RequestBody.create(mediaType, file);
        return getSelf();
    }

    public BodyBuilder<V> setBody(File file) {
        return setBody(MediaType.parse(FileUtil.getMiniType(file)), file);
    }

    public BodyBuilder<V> setJsonBody(String jsonString) {
        return setBody(MediaType.parse("application/json"), jsonString);
    }

    public BodyBuilder<V> setJsonBody(String jsonString, String charset) {
        return setBody(MediaType.parse("application/json;charset=" + charset), jsonString);
    }
}
