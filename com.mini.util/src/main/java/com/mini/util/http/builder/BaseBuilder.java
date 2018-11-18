package com.mini.util.http.builder;

import com.mini.util.http.HttpRequest;
import com.mini.util.http.call.*;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public abstract class BaseBuilder {
    protected final HttpRequest request;

    public BaseBuilder(HttpRequest request) {
        this.request = request;
    }

    /**
     * 获取请求对象
     *
     * @return
     */
    public HttpRequest getRequest() {
        return request;
    }

    /**
     * 获取连接
     *
     * @return
     */
    public HttpUrl getUrl() {
        return request.getHttpUrl();
    }

    /**
     * 获取原始请求客户端对象
     *
     * @return
     */
    public OkHttpClient getClient() {
        return request.getClient();
    }

    /**
     * 获取原始  Request.Builder 对象
     *
     * @return
     */
    public Request.Builder getBuilder() {
        return request.getBuilder();
    }

    /**
     * 获取RequestBody
     *
     * @return
     */
    public abstract RequestBody getRequestBody();


    /**
     * GET 方式提交数据， 该方法无法回调进度<br/>
     * 该方式请求时，参数只能通过url和FormBuilder方式添加<br/>
     *
     * @param clazz
     * @return
     */
    public final <T> HttpCall<T> get(Class<T> clazz) {
        return new HttpGetCall<T>(this, getRequestBody());
    }

    /**
     * HEAD 方式提交数据， 该方式无法提交进度<br/>
     * 该方式请求时，参数只能通过url和FormBuilder方式添加<br/>
     *
     * @param clazz
     * @return
     */
    public final <T> HttpCall<T> head(Class<T> clazz) {
        return new HttpHeadCall<T>(this, getRequestBody());
    }


    /**
     * POST 方式提交数据
     *
     * @param clazz
     * @return
     */
    public final <T> HttpCall<T> post(Class<T> clazz) {
        return new HttpPostCall<T>(this, getRequestBody());
    }

    /**
     * DELETE 方式提交数据
     *
     * @param clazz
     * @return
     */
    public final <T> HttpCall<T> delete(Class<T> clazz) {
        return new HttpDeleteCall<T>(this, getRequestBody());
    }

    /**
     * PUT 方式提交数据
     *
     * @param clazz
     * @return
     */
    public final <T> HttpCall<T> put(Class<T> clazz) {
        return new HttpPutCall<T>(this, getRequestBody());
    }

    /**
     * PATCH 方式提交数据
     *
     * @param clazz
     * @return
     */
    public final <T> HttpCall<T> patch(Class<T> clazz) {
        return new HttpPatchCall<T>(this, getRequestBody());
    }

}
