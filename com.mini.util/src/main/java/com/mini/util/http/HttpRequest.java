package com.mini.util.http;

import com.mini.util.http.builder.BaseBuilder;
import com.mini.util.http.builder.BodyBuilder;
import com.mini.util.http.builder.FormBuilder;
import com.mini.util.http.builder.PartBuilder;
import okhttp3.*;

import java.lang.reflect.Constructor;
import java.net.URL;

public class HttpRequest {
    private final Request.Builder builder;
    private final HttpClient client;
    private HttpUrl httpUrl;

    /**
     * 构造方法
     *
     * @param client
     */
    public HttpRequest(HttpClient client) {
        builder = new Request.Builder();
        this.client = client;
    }

    /**
     * 获取请求地址
     *
     * @return
     */
    public HttpUrl getHttpUrl() {
        return httpUrl;
    }

    /**
     * 原始请求对象
     *
     * @return
     */
    public Request.Builder getBuilder() {
        return builder;
    }

    /**
     * 原始请求客户端
     *
     * @return
     */
    public OkHttpClient getClient() {
        return client.getOkHttpClient();
    }

    /**
     * 设置请求地址
     *
     * @param url
     * @return
     */
    public HttpRequest url(HttpUrl url) {
        builder.url((httpUrl = url));
        return this;
    }

    /**
     * 设置请求地址
     *
     * @param url
     * @return
     */
    public HttpRequest url(String url) {
        return url(HttpUrl.parse(url));
    }

    /**
     * 设置请求地址
     *
     * @param url
     * @return
     */
    public HttpRequest url(URL url) {
        return url(HttpUrl.get(url));
    }

    /**
     * 添加请求头
     *
     * @param name
     * @param value
     * @return
     */
    public HttpRequest header(String name, String value) {
        builder.header(name, value == null ? "" : value);
        return this;
    }

    /**
     * 删除请求头
     *
     * @param name
     * @return
     */
    public HttpRequest removeHeader(String name) {
        builder.removeHeader(name);
        return this;
    }

    /**
     * 设置请求头
     *
     * @param headers
     * @return
     */
    public HttpRequest headers(Headers headers) {
        builder.headers(headers);
        return this;
    }

    /**
     * 缓存
     *
     * @param cacheControl
     * @return
     */
    public HttpRequest cacheControl(CacheControl cacheControl) {
        builder.cacheControl(cacheControl);
        return this;
    }

    /**
     * TAG 对象
     *
     * @param tag
     * @return
     */
    public HttpRequest tag(Object tag) {
        builder.tag(tag);
        return this;
    }

    /**
     * 创建 BaseBuilder 参数对象
     *
     * @param clazz
     * @param <E>
     * @return
     * @throws Exception
     */
    public <E extends BaseBuilder> E newBuilder(Class<E> clazz) throws Exception {
        Constructor<E> constructor = clazz.getConstructor(this.getClass());
        return constructor == null ? null : constructor.newInstance(this);
    }

    /**
     * 创建FormBuilder参数对象
     *
     * @return
     */
    public FormBuilder newFormBuilder() {
        return new FormBuilder(this);
    }

    /**
     * 创建  PartBuilder 参数对象
     *
     * @return
     * @throws Exception
     */
    public PartBuilder newPartBuilder() {
        return new PartBuilder(this);
    }

    /**
     * 创建 BodyBuilder 参数对象
     *
     * @return
     * @throws Exception
     */
    public BodyBuilder newBodyBuilder() {
        return new BodyBuilder(this);
    }
}
