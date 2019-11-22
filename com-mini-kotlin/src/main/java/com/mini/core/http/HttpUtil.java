package com.mini.core.http;

import com.mini.core.jdbc.builder.SQLBuilder;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.parseLong;

public final class HttpUtil {

    public static void main(String[] args) {
        new SQLBuilder(sqlBuilder -> {

            return null;
        });
    }

    /**
     * Content-Length=123456
     * @param response Response
     * @return 123456
     */
    public static long getContentLength(Response response) {
        String contentLength = response.header("Content-Length");
        if (contentLength == null || contentLength.trim().length() == 0) {
            return 0;
        }
        return parseLong(contentLength);
    }

    /**
     * attachment; filename="file_name.jpg"
     * @param response Response
     * @return "file_name.jpg"
     */
    public static String getContentDispositionFileName(Response response) {
        return getContentDisposition(response).get("filename");
    }

    /**
     * attachment; filename="file_name.jpg"
     * @param response Response
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
            else result.put(child.substring(0, index), child.substring(index + 1));
        }
        return result;
    }

    /**
     * bytes startPos-endPos/fileLength
     * @param response Response
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
        return new long[]{parseLong(s1), parseLong(s2), parseLong(s3)};
    }
}
