package sn.mini.java.util.http;

import java.nio.charset.Charset;

public abstract class HttpUtil {

    public static final HttpUtil http = new HttpUtil() {
        protected HttpRequest.Builder getHttpRequest(String url) {
            return HttpRequest.builder(url)
                    .setRequestProperty("Charset", "UTF-8")
                    .setRequestProperty("Accept-Charset", "UTF-8")
                    .setRequestProperty("Connection", "Keep-Alive")
                    .setDoOutput(true)
                    .setDoInput(true)
                    .setUseCaches(false);
        }
    };

    public final static HttpUtil https = new HttpUtil() {
        @Override
        protected HttpRequest.Builder getHttpRequest(String url) {
            return HttpsRequest.builder(url)
                    .setRequestProperty("Charset", "UTF-8")
                    .setRequestProperty("Accept-Charset", "UTF-8")
                    .setRequestProperty("Connection", "Keep-Alive")
                    .setDoOutput(true)
                    .setDoInput(true)
                    .setUseCaches(false);
        }
    };


    protected abstract HttpRequest.Builder getHttpRequest(String url);

    public String getForUrlEncoded(String url) {
        try {
            return this.getHttpRequest(url).setRequestProperty("Content-Type", HttpRequest.FORM_URLENCODED)//
                    .GET().send(HttpResponse.BodyHandler.asString()).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getForUrlEncoded(String url, String charset) {
        try {
            return this.getHttpRequest(url).setRequestProperty("Content-Type", HttpRequest.FORM_URLENCODED)//
                    .GET().send(HttpResponse.BodyHandler.asString(Charset.forName(charset))).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String postForUrlEncoded(String url, FormData formData) {
        try {
            return this.getHttpRequest(url).setRequestProperty("Content-Type", HttpRequest.FORM_URLENCODED)//
                    .POST(formData).send(HttpResponse.BodyHandler.asString()).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String postForUrlEncoded(String url, FormData formData, String charset) {
        try {
            return this.getHttpRequest(url).setRequestProperty("Content-Type", HttpRequest.FORM_URLENCODED)//
                    .POST(formData).send(HttpResponse.BodyHandler.asString(Charset.forName(charset))).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String postForMultipart(String url, FormData formData) {
        try {
            return this.getHttpRequest(url).setRequestProperty("Content-Type", HttpRequest.MULTIPART_FORM_DATA)//
                    .POST(formData).send(HttpResponse.BodyHandler.asString()).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String postForMultipart(String url, FormData formData, String charset) {
        try {
            return this.getHttpRequest(url).setRequestProperty("Content-Type", HttpRequest.MULTIPART_FORM_DATA)//
                    .POST(formData).send(HttpResponse.BodyHandler.asString(Charset.forName(charset))).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String postForContentBody(String url, String body) {
        try {
            return this.getHttpRequest(url).setRequestProperty("Content-Type", HttpRequest.TEXT_PLAIN)//
                    .POST(new FormData(body)).send(HttpResponse.BodyHandler.asString()).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String postForContentBody(String url, String body, String charset) {
        try {
            return this.getHttpRequest(url).setRequestProperty("Content-Type", HttpRequest.TEXT_PLAIN)//
                    .POST(new FormData(body)).send(HttpResponse.BodyHandler.asString(Charset.forName(charset))).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String s = HttpUtil.https.postForUrlEncoded("https://baike.baidu.com/item/%E4%BD%A0%E5%A5%BD%E5%90%97/9766493", new FormData("fr", "aladdin"));
        System.out.println(s);
    }
}
