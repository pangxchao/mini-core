package com.mini.core.http.builder;

import com.mini.core.http.callback.Callback;
import com.mini.core.http.converter.Converter;
import com.mini.core.util.FileUtil;
import okhttp3.*;
import okio.ByteString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.LongConsumer;
import java.util.function.ObjLongConsumer;
import java.util.stream.Stream;

import static okhttp3.MediaType.parse;

public abstract class AbstractBuilder<T> {
    private static final OkHttpClient.Builder HTTP_CLIENT = new OkHttpClient.Builder();
    private final Request.Builder request = new Request.Builder();
    private Call call;

    /** 全局设置 */
    public final AbstractBuilder<T> connectTimeout(long timeout, TimeUnit unit) {
        HTTP_CLIENT.connectTimeout(timeout, unit);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> readTimeout(long timeout, TimeUnit unit) {
        HTTP_CLIENT.readTimeout(timeout, unit);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> writeTimeout(long timeout, TimeUnit unit) {
        HTTP_CLIENT.writeTimeout(timeout, unit);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> pingInterval(long interval, TimeUnit unit) {
        HTTP_CLIENT.pingInterval(interval, unit);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> proxy(@Nullable Proxy proxy) {
        HTTP_CLIENT.proxy(proxy);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> proxySelector(@Nonnull ProxySelector proxySelector) {
        HTTP_CLIENT.proxySelector(proxySelector);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> cookieJar(CookieJar cookieJar) {
        HTTP_CLIENT.cookieJar(cookieJar);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> cache(@Nullable Cache cache) {
        HTTP_CLIENT.cache(cache);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> dns(Dns dns) {
        HTTP_CLIENT.dns(dns);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> socketFactory(SocketFactory socketFactory) {
        HTTP_CLIENT.socketFactory(socketFactory);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
        HTTP_CLIENT.sslSocketFactory(sslSocketFactory, trustManager);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> hostnameVerifier(HostnameVerifier hostnameVerifier) {
        HTTP_CLIENT.hostnameVerifier(hostnameVerifier);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> certificatePinner(CertificatePinner certificatePinner) {
        HTTP_CLIENT.certificatePinner(certificatePinner);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> authenticator(Authenticator authenticator) {
        HTTP_CLIENT.authenticator(authenticator);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> proxyAuthenticator(Authenticator proxyAuthenticator) {
        HTTP_CLIENT.proxyAuthenticator(proxyAuthenticator);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> connectionPool(ConnectionPool connectionPool) {
        HTTP_CLIENT.connectionPool(connectionPool);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> followSslRedirects(boolean followProtocolRedirects) {
        HTTP_CLIENT.followSslRedirects(followProtocolRedirects);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> followRedirects(boolean followRedirects) {
        HTTP_CLIENT.followRedirects(followRedirects);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> retryOnConnectionFailure(boolean retryOnConnectionFailure) {
        HTTP_CLIENT.retryOnConnectionFailure(retryOnConnectionFailure);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> dispatcher(Dispatcher dispatcher) {
        HTTP_CLIENT.dispatcher(dispatcher);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> protocols(List<Protocol> protocols) {
        HTTP_CLIENT.protocols(protocols);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> connectionSpecs(List<ConnectionSpec> connectionSpecs) {
        HTTP_CLIENT.connectionSpecs(connectionSpecs);
        return this;
    }

    /** 全局设置 */
    public final List<Interceptor> interceptors() {
        return HTTP_CLIENT.interceptors();
    }

    /** 全局设置 */
    public final AbstractBuilder<T> addInterceptor(@Nonnull Interceptor interceptor) {
        HTTP_CLIENT.addInterceptor(interceptor);
        return this;
    }

    /** 全局设置 */
    public final List<Interceptor> networkInterceptors() {
        return HTTP_CLIENT.networkInterceptors();
    }

    /** 全局设置 */
    public final AbstractBuilder<T> addNetworkInterceptor(@Nonnull Interceptor interceptor) {
        HTTP_CLIENT.addNetworkInterceptor(interceptor);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> eventListener(EventListener eventListener) {
        HTTP_CLIENT.eventListener(eventListener);
        return this;
    }

    /** 全局设置 */
    public final AbstractBuilder<T> eventListenerFactory(EventListener.Factory eventListenerFactory) {
        HTTP_CLIENT.eventListenerFactory(eventListenerFactory);
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> url(HttpUrl url) {
        request.url(url);
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> url(String url) {
        request.url(url);
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> url(URL url) {
        request.url(url);
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> header(String name, String value) {
        request.header(name, value);
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> addHeader(String name, String value) {
        request.addHeader(name, value);
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> removeHeader(@Nonnull String name) {
        request.removeHeader(name);
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> headers(Headers headers) {
        request.headers(headers);
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> cacheControl(CacheControl cacheControl) {
        request.cacheControl(cacheControl);
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> tag(@Nullable Object tag) {
        request.tag(tag);
        return this;
    }

    /** Request.Builder */
    public final <E> AbstractBuilder<T> tag(Class<? super E> type, @Nullable E tag) {
        request.tag(type, tag);
        return this;
    }

    /** 获取请求体 */
    protected abstract RequestBody getRequestBody();

    /** Body Request */
    public AbstractBuilder<T> setRequestBody(RequestBody body) {
        throw new UnsupportedOperationException();
    }

    /** Form Request */
    public AbstractBuilder<T> add(@Nonnull String name, Object value) {
        throw new UnsupportedOperationException();
    }

    /** Form Request */
    public <E> AbstractBuilder<T> addStream(String name, Stream<E> stream) {
        throw new UnsupportedOperationException();
    }

    /** Form Request */
    public AbstractBuilder<T> addEncoded(@Nonnull String name, Object value) {
        throw new UnsupportedOperationException();
    }

    /** Form Request */
    public <E> AbstractBuilder<T> addStreamEncoded(@Nonnull String name, Stream<E> stream) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> setOnUpload(ObjLongConsumer<Long> onUpload) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */

    public AbstractBuilder<T> setOnCancel(LongConsumer onCancel) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> setType(MediaType type) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> addFormDataPart(String name, Object value) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public <E> AbstractBuilder<T> addFormDataPartStream(String name, Stream<E> stream) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> addFormDataPart(String name, @Nullable String fileName, RequestBody body) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> addPart(RequestBody body) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> addPart(Headers headers, RequestBody body) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> addPart(MultipartBody.Part part) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> addPart(String name, String fileName, String contentType, byte[] content) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> addPart(String name, String fileName, String contentType, long contentLength, InputStream content) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> addPart(String name, File file, long offset, long contentLength) {
        throw new UnsupportedOperationException();
    }

    /** Part Request */
    public AbstractBuilder<T> addPart(String name, File file) {
        throw new UnsupportedOperationException();
    }

    /** Request.Builder */
    public final AbstractBuilder<T> get() {
        this.request.get();
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> post() {
        request.post(getRequestBody());
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> delete() {
        request.delete(getRequestBody());
        return this;
    }

    /** Request.Builder */
    public final AbstractBuilder<T> put() {
        request.put(getRequestBody());
        return this;
    }

    /**
     * 发送同步请求并返回结果
     * @param callback 结果回调
     * @throws IOException 请求出现错误
     */
    public final void execute(Callback<Response> callback) throws IOException {
        this.call = HTTP_CLIENT.build().newCall(request.build());
        callback.accept(call.execute());
    }

    /**
     * 发送同步请求并返回结果
     * @param converter 数据转换器
     * @param callback  结果回调
     * @throws IOException 请求出现错误
     */
    public final void execute(@Nonnull Converter<T> converter, Callback<T> callback) throws IOException {
        this.call = HTTP_CLIENT.build().newCall(request.build());
        callback.accept(converter.apply(call, call.execute()));
    }

    /** 取消已发送的请求 */
    public final AbstractBuilder<T> cancel() {
        if (this.call != null) {
            call.cancel();
        }
        return this;
    }

    /**
     * 是否下在执行请求
     * @return true-是
     */
    public final boolean isExecuted() {
        if (this.call == null) {
            return false;
        }
        return call.isExecuted();
    }

    /**
     * 是否已经取消
     * @return true-是
     */
    public final boolean isCanceled() {
        if (this.call == null) {
            return false;
        }
        return call.isCanceled();
    }

    /** 获取请求对象 */
    public final Request request() {
        if (this.call == null) {
            return null;
        }
        return call.request();
    }

    /** 获取调用器对象 */
    public final Call getCall() {
        return call;
    }

    public static RequestBody create(MediaType mediaType, String content) {
        return RequestBody.create(mediaType, content);
    }

    public static RequestBody create(MediaType mediaType, ByteString content) {
        return RequestBody.create(mediaType, content);
    }

    public static RequestBody create(MediaType mediaType, byte[] content) {
        return RequestBody.create(mediaType, content);
    }

    public static RequestBody create(MediaType mediaType, byte[] content, int offset, int byteCount) {
        return RequestBody.create(mediaType, content, offset, byteCount);
    }

    public static RequestBody create(MediaType mediaType, File file) {
        return RequestBody.create(mediaType, file);
    }

    public static RequestBody create(File file) {
        String type = FileUtil.getMiniType(file);
        return create(parse(type), file);
    }

    public RequestBody createJSONBody(String json, String charset) {
        return create(parse("application/json;charset=" + charset), json);
    }

    public static RequestBody createJSONBody(String json) {
        return create(parse("application/json"), json);
    }
}
