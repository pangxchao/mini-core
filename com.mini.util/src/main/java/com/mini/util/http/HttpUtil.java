package com.mini.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class HttpUtil {
    private HttpUtil() {}

    public static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder() //
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间
            .writeTimeout(10, TimeUnit.SECONDS) // 写数据超时时间
            .readTimeout(30, TimeUnit.SECONDS) // 读数据超时时间
            .retryOnConnectionFailure(true).build();

    /**
     * String 转换器
     *
     * @param response
     */
    public static final Converter<String> STRING_CONVERTER = (call, response) -> {
        try (ResponseBody body = response.body()) {
            return body == null ? null : body.string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    /**
     * com.alibaba.fastjson.JSONObject 转换器
     * 数据转换成
     */
    public static final Converter<JSONObject> JSON_OBJECT_CONVERTER = (call, response) -> {
        try (ResponseBody body = response.body()) {
            if (body == null) return null;
            String string = body.string();
            if (string == null) return null;
            return JSON.parseObject(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public static HttpClient newClient() {
        return new HttpClient(OK_HTTP_CLIENT);
    }

    public static HttpClient newClient(OkHttpClient okHttpClient) {
        return new HttpClient(okHttpClient);
    }

//
//    public static FormBuilder formBodyRequest(String url) {
//        return new FormBuilder(okHttpClient).url(url);
//    }
//
//    public static PartBuilder multipartBodyRequest(String url) {
//        return new PartBuilder(okHttpClient).url(url);
//    }
//
//    public static BodyBuilder requestBodyRequest(String url) {
//        return new BodyBuilder(okHttpClient).url(url);
//    }

    public static long getContentLength(Response response) {
        String contentLength = response.header("Content-Length");
        return contentLength == null || contentLength.trim().length() == 0 ? 0 : Long.valueOf(contentLength);
    }

    /**
     * attachment; filename="file_name.jpg"
     *
     * @param response
     * @return "file_name.jpg"
     */
    public static String getContentDispositionFileName(Response response) {
        return getContentDisposition(response).get("filename");
    }

    /**
     * attachment; filename="file_name.jpg"
     *
     * @param response
     * @return {"attachment": null, "filename": "file_name.jpg"}
     */
    public static Map<String, String> getContentDisposition(Response response) {
        Map<String, String> result = new HashMap<>();
        String disposition = response.header("Content-Disposition");
        if (disposition == null || disposition.trim().length() == 0) {
            return result;
        }
        for (String child : disposition.split("[;]")) {
            int index = (child = child.trim()).indexOf('=');
            if (index == -1) result.put(child.trim(), null);
            else result.put(child.substring(0, index), //
                    child.substring(index + 1));
        }
        return result;
    }

    /**
     * bytes startPos-endPos/fileLength
     *
     * @param response
     * @return long[]{startPos, endPos, fileLength}
     */
    public static long[] getContentRange(Response response) {
        String contentRange = response.header("Content-Range");
        if (contentRange == null) return null;
        if (contentRange.length() < 6) return null;
        contentRange = contentRange.substring(6);
        if (contentRange.length() == 0) return null;
        int i1 = contentRange.indexOf('-');
        int i2 = contentRange.indexOf('/');
        if (i1 == -1 || i2 == -1 || i1 >= i2) return null;
        String s1 = contentRange.substring(0, i1);
        String s2 = contentRange.substring(i1 + 1, i2);
        String s3 = contentRange.substring(i2 + 1);
        return new long[]{Long.valueOf(s1), Long.valueOf(s2), Long.valueOf(s3)};
    }
}
