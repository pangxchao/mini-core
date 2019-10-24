package com.mini.http.builder;

import okhttp3.FormBody.Builder;
import okhttp3.RequestBody;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static com.mini.util.ObjectUtil.defIfNull;

public final class FormBuilder<T> extends AbstractBuilder<T> {
    private final Builder builder = new Builder();

    @Override
    protected RequestBody getRequestBody() {
        return builder.build();
    }

    /** Form Request */
    public FormBuilder<T> add(@Nonnull String name, Object value) {
        builder.add(name, String.valueOf(defIfNull(value, "")));
        return this;
    }

    /** Form Request */
    public <E> FormBuilder<T> addStream(String name, Stream<E> stream) {
        stream.forEach(e -> add(name, e));
        return this;
    }

    /** Form Request */
    public FormBuilder<T> addEncoded(@Nonnull String name, Object value) {
        builder.addEncoded(name, String.valueOf(defIfNull(value, "")));
        return this;
    }

    /** Form Request */
    public <E> FormBuilder<T> addStreamEncoded(@Nonnull String name, Stream<E> stream) {
        stream.forEach(e -> addEncoded(name, e));
        return this;
    }

}
