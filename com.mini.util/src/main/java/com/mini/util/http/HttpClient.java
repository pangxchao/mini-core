package com.mini.util.http;

import okhttp3.OkHttpClient;

public class HttpClient {
    private final OkHttpClient okHttpClient;

    public HttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public HttpRequest newRequest() {
        return new HttpRequest(this);
    }
}
