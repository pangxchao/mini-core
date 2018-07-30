package sn.mini.java.util.lang;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class FileUtil {
    private FileUtil() {
    }

    /**
     * 根据文件名返回文件后缀名
     *
     * @param name
     * @return
     */
    public static String getSuffix(String name) {
        int index = name == null ? -1 : name.lastIndexOf(".");
        return index >= 0 ? name.substring(index) : "";
    }

    /**
     * 获取文件后缀
     *
     * @param name
     * @param isDot 后缀名前是否带“.”
     * @return
     */
    public static String getSuffix(String name, boolean isDot) {
        int index = name == null ? -1 : name.lastIndexOf(".");
        return index >= 0 ? name.substring(isDot ? index : (index + 1)) : "";
    }


    /**
     * 获取文件类型
     *
     * @param path
     * @return
     */
    public static String getMiniType(String path) {
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(path);
        return contentType == null ? "application/octet-stream" : contentType;
    }

    /**
     * 获取文件类型
     *
     * @param file
     * @return
     */
    public static String getMiniType(File file) {
        return getMiniType(file.getAbsolutePath());
    }

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://127.0.0.1/cloud/aa/bb.htm?ab=1");
        System.out.println(url.getPath());
        System.out.println(url.getQuery());
    }
}
