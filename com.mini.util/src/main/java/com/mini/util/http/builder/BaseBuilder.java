package com.mini.util.http.builder;

import com.mini.util.http.Converter;
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
     * @param converter
     * @return
     */
    public final <T> HttpCall<T> get( Converter<T> converter) {
        return new HttpGetCall<T>(this, converter);
    }

    /**
     * HEAD 方式提交数据， 该方式无法提交进度<br/>
     * 该方式请求时，参数只能通过url和FormBuilder方式添加<br/>
     *
     * @param converter
     * @return
     */
    public final <T> HttpCall<T> head( Converter<T> converter) {
        return new HttpHeadCall<T>(this, converter );
    }


    /**
     * POST 方式提交数据
     *
     * @param converter
     * @return
     */
    public final <T> HttpCall<T> post( Converter<T> converter) {
        return new HttpPostCall<T>(this,converter );
    }

    /**
     * DELETE 方式提交数据
     *
     * @param converter
     * @return
     */
    public final <T> HttpCall<T> delete( Converter<T> converter) {
        return new HttpDeleteCall<T>(this,converter );
    }

    /**
     * PUT 方式提交数据
     *
     * @param converter
     * @return
     */
    public final <T> HttpCall<T> put( Converter<T> converter) {
        return new HttpPutCall<T>(this,converter );
    }

    /**
     * PATCH 方式提交数据
     *
     * @param converter
     * @return
     */
    public final <T> HttpCall<T> patch( Converter<T> converter) {
        return new HttpPatchCall<T>(this, converter);
    }

}
