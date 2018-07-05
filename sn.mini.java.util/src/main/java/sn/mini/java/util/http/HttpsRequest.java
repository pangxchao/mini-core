package sn.mini.java.util.http;

import javax.net.ssl.*;
import java.net.ProtocolException;
import java.net.Proxy;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpsRequest extends HttpRequest {
    protected HttpsRequest(String url) {
        super(url);
    }

    protected HttpsRequest(String url, Proxy proxy) {
        super(url, proxy);

    }

    public HttpsURLConnection getHttpsURLConnection() {
        return (HttpsURLConnection) this.getHttpURLConnection();
    }


    public static Builder builder(String url) {
        return new Builder(new HttpsRequest(url));
    }

    public static Builder builder(String url, Proxy proxy) {
        return new Builder(new HttpsRequest(url, proxy));
    }

    public static class Builder extends HttpRequest.Builder {
        protected Builder(HttpsRequest request) {
            super(request);
            setSSLSocketFactory(getInitSSLSocketFactory());
        }

        protected HttpsRequest getHttpsRequest() {
            return (HttpsRequest) this.getHttpRequest();
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

        public Builder setFixedLengthStreamingMode(int contentLength) {
            getHttpRequest().getHttpURLConnection().setFixedLengthStreamingMode(contentLength);
            return this;
        }

        public Builder setFixedLengthStreamingMode(long contentLength) {
            getHttpRequest().getHttpURLConnection().setFixedLengthStreamingMode(contentLength);
            return this;
        }

        public Builder setChuckleStreamingMode(int chuckle) {
            getHttpRequest().getHttpURLConnection().setChunkedStreamingMode(chuckle);
            return this;
        }

        public Builder setInstanceFollowRedirects(boolean followRedirects) {
            getHttpRequest().getHttpURLConnection().setInstanceFollowRedirects(followRedirects);
            return this;
        }

        public HttpsRequest POST(FormData formData) throws ProtocolException {
            return (HttpsRequest) super.POST(formData);
        }

        public HttpsRequest GET() throws ProtocolException {
            return (HttpsRequest) super.GET();
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            getHttpsRequest().getHttpsURLConnection().setHostnameVerifier(hostnameVerifier);
            return this;
        }


        public Builder setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            getHttpsRequest().getHttpsURLConnection().setSSLSocketFactory(sslSocketFactory);
            return this;
        }
    }

    /**
     * 获取 SSLSocketFactory
     *
     * @return
     * @throws Exception
     */
    private static SSLSocketFactory getInitSSLSocketFactory() {
        try {
            // TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            // 创建SSLSocketFactory
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TrustManager
     *
     * @author XChao
     */
    private static X509TrustManager x509TrustManager = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType)//
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) //
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };
}
