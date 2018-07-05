package sn.mini.java.util.http;

import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class UrlRequest {
    private final URLConnection urlConnection;

    /**
     * 构造方法
     *
     * @param url
     */
    protected UrlRequest(String url) {
        try {
            this.urlConnection = new URL(url).openConnection();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 构造方法
     *
     * @param url
     * @param proxy
     */
    protected UrlRequest(String url, Proxy proxy) {
        try {
            this.urlConnection = new URL(url).openConnection(proxy);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected URLConnection getURLConnection() {
        return this.urlConnection;
    }

    public static class Builder {
        private UrlRequest request;

        protected Builder(UrlRequest request) {
            this.request = request;
        }

        protected UrlRequest getUrlRequest() {
            return this.request;
        }

        public Builder setConnectTimeout(int timeout) {
            getUrlRequest().getURLConnection().setConnectTimeout(timeout);
            return this;

        }

        public Builder setReadTimeout(int timeout) {
            getUrlRequest().getURLConnection().setReadTimeout(timeout);
            return this;
        }


        public Builder setDoInput(boolean doInput) {
            getUrlRequest().getURLConnection().setDoInput(doInput);
            return this;
        }


        public Builder setDoOutput(boolean doOutput) {
            getUrlRequest().getURLConnection().setDoOutput(doOutput);
            return this;
        }

        public Builder setAllowUserInteraction(boolean allowUserInteraction) {
            getUrlRequest().getURLConnection().setAllowUserInteraction(allowUserInteraction);
            return this;
        }

        public Builder setUseCaches(boolean useCaches) {
            getUrlRequest().getURLConnection().setUseCaches(useCaches);
            return this;
        }


        public Builder setIfModifiedSince(long fModifiedSince) {
            getUrlRequest().getURLConnection().setIfModifiedSince(fModifiedSince);
            return this;
        }


        public Builder setDefaultUseCaches(boolean defaultUseCaches) {
            getUrlRequest().getURLConnection().setDefaultUseCaches(defaultUseCaches);
            return this;
        }

        public Builder setRequestProperty(String key, String value) {
            getUrlRequest().getURLConnection().setRequestProperty(key, value);
            return this;
        }

        public Builder addRequestProperty(String key, String value) {
            getUrlRequest().getURLConnection().addRequestProperty(key, value);
            return this;
        }
    }
}
