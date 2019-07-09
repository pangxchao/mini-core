package com.mini.util;

import java.io.File;
import java.net.URI;
import java.net.URLConnection;

public final class FileUtil {

    private FileUtil() {
    }

    /**
     * 处理相同文件夹下同名文件
     * @param filePath 文件路径
     * @return 文件对象
     */
    public static File distinct(String filePath) {
        return distinct(new File(filePath));

    }

    /**
     * 处理文件名相同的情况
     * @param file 文件对象
     * @return 文件对象
     */
    public static File distinct(File file) {
        return distinct(file, true);
    }

    /**
     * 获取文件名
     * @param file       文件对象
     * @param additional true-去重
     * @return 文件对象
     */
    public static File distinct(File file, boolean additional) {
        String fileName = file.getName();
        String suffix = FileUtil.getSuffix(fileName);
        String fileNameTemp = fileName.substring(0, suffix.length());
        for (int i = 1; additional && file.exists(); i++) {
            file = new File(file.getParentFile(), fileNameTemp + "(" + i + ")" + suffix);
        }
        return file;
    }

    /**
     * 根据文件名返回文件后缀名
     * @param name 文件名
     * @return 后缀名
     */
    public static String getSuffix(String name) {
        int index = name == null ? -1 : name.lastIndexOf(".");
        return index >= 0 ? name.substring(index) : "";
    }

    /**
     * 获取文件后缀
     * @param name  文件名
     * @param isDot 后缀名前是否带“.”
     * @return 后缀名
     */
    public static String getSuffix(String name, boolean isDot) {
        int index = name == null ? -1 : name.lastIndexOf(".");
        return index >= 0 ? name.substring(isDot ? index : (index + 1)) : "";
    }

    /**
     * 获取路径中的文件名
     * @param pathName 文件路径
     * @return 文件名
     */
    public static String getFileName(String pathName) {
        try {
            String path = new URI(pathName).getPath();
            if (path == null || path.length() == 0) return path;
            int index = path.lastIndexOf("/");
            return index == -1 ? path : path.substring(index + 1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取文件类型
     * @param path 文件路径
     * @return 文件类型
     */
    public static String getMiniType(String path) {
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(path);
        return contentType == null ? "application/octet-stream" : contentType;
    }

    /**
     * 获取文件类型
     * @param file 文件对象
     * @return 文件类型
     */
    public static String getMiniType(File file) {
        return getMiniType(file.getAbsolutePath());
    }

    /**
     * 文件大小单位转换
     * @param fileLength 文件大小
     * @return 格式化结果
     */
    public static String getShowFileSize(long fileLength) {
        double length = fileLength * 1.0;
        if (length < 1024) return String.format("%.2fB", length);
        if ((length = length / 1024) < 1024) return String.format("%.2fKB", length);
        if ((length = length / 1024) < 1024) return String.format("%.2fMB", length);
        if ((length = length / 1024) < 1024) return String.format("%.2fGB", length);
        return String.format("%.2fTB", length / 1024);
    }
}
