package com.mini.http.builder;

import com.mini.http.converter.Converter;
import com.mini.util.Function;
import okhttp3.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AbstractBuilder<T extends AbstractBuilder<T, V>, V> {
    private static final OkHttpClient.Builder HTTP_CLIENT = new OkHttpClient.Builder();
    private final Request.Builder request = new Request.Builder();
    private Converter<V> converter;
    private Call call;

    /** 全局设置 */
    public final T connectTimeout(long timeout, TimeUnit unit) {
        HTTP_CLIENT.connectTimeout(timeout, unit);
        return getSelf();
    }

    /** 全局设置 */
    public final T readTimeout(long timeout, TimeUnit unit) {
        HTTP_CLIENT.readTimeout(timeout, unit);
        return getSelf();
    }

    /** 全局设置 */
    public final T writeTimeout(long timeout, TimeUnit unit) {
        HTTP_CLIENT.writeTimeout(timeout, unit);
        return getSelf();
    }

    /** 全局设置 */
    public final T pingInterval(long interval, TimeUnit unit) {
        HTTP_CLIENT.pingInterval(interval, unit);
        return getSelf();
    }

    /** 全局设置 */
    public final T proxy(@Nullable Proxy proxy) {
        HTTP_CLIENT.proxy(proxy);
        return getSelf();
    }

    /** 全局设置 */
    public final T proxySelector(@Nonnull ProxySelector proxySelector) {
        HTTP_CLIENT.proxySelector(proxySelector);
        return getSelf();
    }

    /** 全局设置 */
    public final T cookieJar(CookieJar cookieJar) {
        HTTP_CLIENT.cookieJar(cookieJar);
        return getSelf();
    }

    /** 全局设置 */
    public final T cache(@Nullable Cache cache) {
        HTTP_CLIENT.cache(cache);
        return getSelf();
    }

    /** 全局设置 */
    public final T dns(Dns dns) {
        HTTP_CLIENT.dns(dns);
        return getSelf();
    }

    /** 全局设置 */
    public final T socketFactory(SocketFactory socketFactory) {
        HTTP_CLIENT.socketFactory(socketFactory);
        return getSelf();
    }

    /** 全局设置 */
    public final T sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
        HTTP_CLIENT.sslSocketFactory(sslSocketFactory, trustManager);
        return getSelf();
    }

    /** 全局设置 */
    public final T hostnameVerifier(HostnameVerifier hostnameVerifier) {
        HTTP_CLIENT.hostnameVerifier(hostnameVerifier);
        return getSelf();
    }

    /** 全局设置 */
    public final T certificatePinner(CertificatePinner certificatePinner) {
        HTTP_CLIENT.certificatePinner(certificatePinner);
        return getSelf();
    }

    /** 全局设置 */
    public final T authenticator(Authenticator authenticator) {
        HTTP_CLIENT.authenticator(authenticator);
        return getSelf();
    }

    /** 全局设置 */
    public final T proxyAuthenticator(Authenticator proxyAuthenticator) {
        HTTP_CLIENT.proxyAuthenticator(proxyAuthenticator);
        return getSelf();
    }

    /** 全局设置 */
    public final T connectionPool(ConnectionPool connectionPool) {
        HTTP_CLIENT.connectionPool(connectionPool);
        return getSelf();
    }

    /** 全局设置 */
    public final T followSslRedirects(boolean followProtocolRedirects) {
        HTTP_CLIENT.followSslRedirects(followProtocolRedirects);
        return getSelf();
    }

    /** 全局设置 */
    public final T followRedirects(boolean followRedirects) {
        HTTP_CLIENT.followRedirects(followRedirects);
        return getSelf();
    }

    /** 全局设置 */
    public final T retryOnConnectionFailure(boolean retryOnConnectionFailure) {
        HTTP_CLIENT.retryOnConnectionFailure(retryOnConnectionFailure);
        return getSelf();
    }

    /** 全局设置 */
    public final T dispatcher(Dispatcher dispatcher) {
        HTTP_CLIENT.dispatcher(dispatcher);
        return getSelf();
    }

    /** 全局设置 */
    public final T protocols(List<Protocol> protocols) {
        HTTP_CLIENT.protocols(protocols);
        return getSelf();
    }

    /** 全局设置 */
    public final T connectionSpecs(List<ConnectionSpec> connectionSpecs) {
        HTTP_CLIENT.connectionSpecs(connectionSpecs);
        return getSelf();
    }

    /** 全局设置 */
    public final List<Interceptor> interceptors() {
        return HTTP_CLIENT.interceptors();
    }

    /** 全局设置 */
    public final T addInterceptor(@Nonnull Interceptor interceptor) {
        HTTP_CLIENT.addInterceptor(interceptor);
        return getSelf();
    }

    /** 全局设置 */
    public final List<Interceptor> networkInterceptors() {
        return HTTP_CLIENT.networkInterceptors();
    }

    /** 全局设置 */
    public final T addNetworkInterceptor(@Nonnull Interceptor interceptor) {
        HTTP_CLIENT.addNetworkInterceptor(interceptor);
        return getSelf();
    }

    /** 全局设置 */
    public final T eventListener(EventListener eventListener) {
        HTTP_CLIENT.eventListener(eventListener);
        return getSelf();
    }

    /** 全局设置 */
    public final T eventListenerFactory(EventListener.Factory eventListenerFactory) {
        HTTP_CLIENT.eventListenerFactory(eventListenerFactory);
        return getSelf();
    }


    /** Request.Builder */
    public final T url(HttpUrl url) {
        request.url(url);
        return getSelf();
    }

    /** Request.Builder */
    public final T url(String url) {
        request.url(url);
        return getSelf();
    }

    /** Request.Builder */
    public final T url(URL url) {
        request.url(url);
        return getSelf();
    }

    /** Request.Builder */
    public final T header(String name, String value) {
        request.header(name, value);
        return getSelf();
    }

    /** Request.Builder */
    public final T addHeader(String name, String value) {
        request.addHeader(name, value);
        return getSelf();
    }

    /** Request.Builder */
    public final T removeHeader(@Nonnull String name) {
        request.removeHeader(name);
        return getSelf();
    }

    /** Request.Builder */
    public final T headers(Headers headers) {
        request.headers(headers);
        return getSelf();
    }

    /** Request.Builder */
    public final T cacheControl(CacheControl cacheControl) {
        request.cacheControl(cacheControl);
        return getSelf();
    }

    /** Request.Builder */
    public final T tag(@Nullable Object tag) {
        request.tag(tag);
        return getSelf();
    }

    /** Request.Builder */
    public final <E> T tag(Class<? super E> type, @Nullable E tag) {
        request.tag(type, tag);
        return getSelf();
    }

    /** Request.Builder */
    public final T get() {
        return method("GET");
    }

    /** Request.Builder */
    public final T head() {
        return method("HEAD");
    }

    /** Request.Builder */
    public final T post() {
        return method("POST");
    }

    /** Request.Builder */
    public final T delete() {
        return method("DELETE");
    }

    /** Request.Builder */
    public final T put() {
        return method("PUT");
    }

    /** Request.Builder */
    public final T patch() {
        return method("PATCH");
    }

    /** Request.Builder */
    public final T method(String method) {
        request.method(method, getRequestBody());
        return getSelf();
    }

    /** 获取请求体 */
    protected abstract RequestBody getRequestBody();

    /** 设置数据转换器 */
    public T setConverter(Converter<V> converter) {
        this.converter = converter;
        return getSelf();
    }

    /**
     * 发送同步请求并返回结果
     * @return 返回请求结果
     * @throws IOException 请求出现错误
     */
    public final Response execute() throws IOException {
        call = HTTP_CLIENT.build().newCall(request.build());
        return call.execute();
    }

    /**
     * 发送同步请求并返回结果
     * @return 返回请求结果
     * @throws IOException 请求出现错误
     */
    public final V execute(@Nonnull Converter<V> converter) throws IOException {
        call = HTTP_CLIENT.build().newCall(request.build());
        return converter.apply(call, call.execute());
    }

    /**
     * 发送异步请求并回调
     * @param callback 回调方法
     */
    public final T enqueue(@Nonnull okhttp3.Callback callback) {
        call = HTTP_CLIENT.build().newCall(request.build());
        call.enqueue(callback);
        return getSelf();
    }

    /**
     * 发送异步请求并回调
     * @param callback 回调方法
     */
    public final T enqueue(@Nonnull Function.F1<V> callback) {
        return enqueue(new okhttp3.Callback() {
            public void onFailure(@Nonnull Call call, @Nonnull IOException e) {
                callback.apply(null);
            }

            public void onResponse(@Nonnull Call call, @Nonnull Response response) {
                try {
                    if (AbstractBuilder.this.converter == null) {
                        callback.apply(null);
                        return;
                    }
                    callback.apply(converter.apply(call, response));
                } catch (Exception e) {
                    callback.apply(null);
                }
            }
        });
    }

    /**
     * 发送异步请求并回调
     * @param success 成功回调
     * @param fail    失败回调
     */
    public final T enqueue(@Nonnull Function.F1<V> success, Function.F1<String> fail) {
        return enqueue(new okhttp3.Callback() {
            public void onFailure(@Nonnull Call call, @Nonnull IOException e) {
                fail.apply(e.getMessage());
            }

            public void onResponse(@Nonnull Call call, @Nonnull Response response) {
                try {
                    if (AbstractBuilder.this.converter == null) {
                        fail.apply("Converter is null.");
                        return;
                    }
                    success.apply(converter.apply(call, response));
                } catch (Exception e) {
                    fail.apply(e.getMessage());
                }
            }
        });
    }


    /** 取消已发送的请求 */
    public final T cancel() {
        if (call != null) {
            call.cancel();
        }
        return getSelf();
    }

    /**
     * 是否下在执行请求
     * @return true-是
     */
    public final boolean isExecuted() {
        if (call == null) return false;
        return call.isExecuted();
    }

    /**
     * 是否已经取消
     * @return true-是
     */
    public final boolean isCanceled() {
        if (call == null) return false;
        return call.isCanceled();
    }

    /** 获取请求对象 */
    public final Request request() {
        if (call == null) return null;
        return call.request();
    }

    /** 获取调用器对象 */
    public final Call getCall() {
        return call;
    }

    /** 获取当前实例 */
    protected abstract T getSelf();

}


