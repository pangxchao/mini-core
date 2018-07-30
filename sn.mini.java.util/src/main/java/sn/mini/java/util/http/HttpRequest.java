package sn.mini.java.util.http;

import sn.mini.java.util.lang.StringUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.ArrayList;

public class HttpRequest extends UrlRequest {
    public static final String FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String TEXT_PLAIN = "text/plain";

    protected HttpRequest(String url) {
        super(url);
    }

    protected HttpRequest(String url, Proxy proxy) {
        super(url, proxy);
    }

    public HttpURLConnection getHttpURLConnection() {
        return (HttpURLConnection) this.getURLConnection();
    }

    public <T> HttpResponse<T> send(HttpResponse.BodyHandler<T> bodyHandler) throws IOException {
        if (getHttpURLConnection().getResponseCode() == HttpURLConnection.HTTP_OK) {
            return bodyHandler.apply(HttpURLConnection.HTTP_OK, getHttpURLConnection().getResponseMessage(), //
                    getHttpURLConnection().getInputStream());
        }
        return bodyHandler.apply(getHttpURLConnection().getResponseCode(), getHttpURLConnection() //
                .getResponseMessage(), null);
    }


    public static Builder builder(String url) {
        return new Builder(new HttpRequest(url));
    }

    public static Builder builder(String url, Proxy proxy) {
        return new Builder(new HttpRequest(url, proxy));
    }

    public static class Builder extends UrlRequest.Builder {


        protected Builder(HttpRequest request) {
            super(request);
        }

        protected HttpRequest getHttpRequest() {
            return (HttpRequest) this.getUrlRequest();
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

        @SuppressWarnings("serial")
		public HttpRequest POST(FormData formData) throws ProtocolException {
            getHttpRequest().getHttpURLConnection().setRequestMethod("POST");
            String contentType = getHttpRequest().getHttpURLConnection().getRequestProperty("Content-Type");
            String end = "\r\n", hyphens = "--", boundary = "**********";
            if (contentType == null) { // 鍒ゆ柇 瀛楃 涓叉槸鍚︿负绌�
                setRequestProperty("Content-Type", HttpRequest.FORM_URLENCODED);
                contentType = HttpRequest.FORM_URLENCODED;
            } else {
                contentType = contentType.toLowerCase();
                if (FORM_URLENCODED.equals(contentType)) { // 鏅�氭柟寮�

                } else if (contentType.startsWith(MULTIPART_FORM_DATA)) {
                    if (MULTIPART_FORM_DATA.equals(contentType)) {
                        contentType = contentType + ";boundary=" + boundary;
                    } else {
                        String[] contentTypeArr = contentType.split(";");
                        String br = null;
                        for (String ct : contentTypeArr) {
                            if (ct != null && ct.startsWith("boundary=")) {
                                br = ct.replace("boundary=", "");
                            }
                        }
                        if (br == null || br.length() == 0) {
                            contentType = contentType + ";boundary=" + boundary;
                        } else {
                            boundary = br;
                        }
                    }
                }
                setRequestProperty("Content-Type", contentType);
            }
            if (HttpRequest.FORM_URLENCODED.equals(contentType)) { //application/x-www-form-urlencoded 缂栫爜
                try (OutputStreamWriter writer = new OutputStreamWriter(getHttpRequest().getHttpURLConnection().getOutputStream())) {
                    writer.write(
                            StringUtil.join(new ArrayList<String>() {{
                                for (String name : formData.textKeySet()) {
                                    for (String value : formData.getText(name)) {
                                        add(name + "=" + value);
                                    }
                                }
                            }}, "&"));
                    writer.flush();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (contentType.startsWith(MULTIPART_FORM_DATA)) { // multipart/form-data 缂栫爜
                try (DataOutputStream steam = new DataOutputStream(getHttpRequest().getHttpURLConnection().getOutputStream())) {
                    for (String name : formData.textKeySet()) {
                        for (String value : formData.getText(name)) {
                            steam.writeBytes(hyphens);
                            steam.writeBytes(boundary);
                            steam.writeBytes(end);

                            steam.writeBytes("Content-Disposition: form-data; name=\"");
                            steam.writeBytes(name);
                            steam.writeBytes("\"");
                            steam.writeBytes(end);

                            steam.writeBytes(end);
                            steam.writeBytes(value);
                            steam.writeBytes(end);
                        }
                    }
                    for (String name : formData.fileKeySet()) {
                        for (int i = 0, len = formData.getFileCount(name); i < len; i++) {
                            steam.writeBytes(hyphens);
                            steam.writeBytes(boundary);
                            steam.writeBytes(end);

                            steam.writeBytes("Content-Disposition: form-data; name=\"");
                            steam.writeBytes(name);
                            steam.writeBytes("\";filename=\"");
                            steam.writeBytes(formData.getFileName(name, i));
                            steam.writeBytes("\"");
                            steam.writeBytes(end);

                            steam.writeBytes("Content-Type:");
                            steam.writeBytes(formData.getFileContentType(name, i));
                            steam.writeBytes(end);

                            steam.writeBytes(end);
                            int bufferSize = 2048, length;
                            byte[] buffer = new byte[bufferSize];
                            try (InputStream inputStream = formData.getFileInputStream(name, i)) {
                                while ((length = inputStream.read(buffer)) != -1) {
                                    steam.write(buffer, 0, length);
                                }
                            }
                            steam.writeBytes(end);
                        }
                    }
                    steam.writeBytes(hyphens);
                    steam.writeBytes(boundary);
                    steam.writeBytes(hyphens);
                    steam.writeBytes(end);
                    steam.flush();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else { // multipart/form-data涓巃pplication/x-www-form-urlencoded 浠ュ鐨勬柟寮忕紪鐮�
                try (OutputStreamWriter writer = new OutputStreamWriter(getHttpRequest().getHttpURLConnection().getOutputStream())) {
                    writer.write(formData.getContentBody());
                    writer.flush();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return getHttpRequest();
        }

        /**
         * GET鏂瑰紡澶勭悊鏃讹紝鏃犻渶鎻愪氦鍙傛暟锛屽己鍒朵娇鐢� application/x-www-form-urlencoded  缂栫爜鏂瑰紡
         *
         * @return
         * @throws ProtocolException
         */
        public HttpRequest GET() throws ProtocolException {
            getHttpRequest().getHttpURLConnection().setRequestMethod("GET");
            setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            try {
                getHttpRequest().getHttpURLConnection().connect();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            return getHttpRequest();
        }
    }
}
