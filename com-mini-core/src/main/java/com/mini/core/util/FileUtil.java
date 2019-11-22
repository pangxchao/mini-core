package com.mini.core.util;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.io.Files.getFileExtension;
import static com.google.common.io.Files.getNameWithoutExtension;
import static com.mini.core.util.PKGenerator.uuid;
import static java.lang.String.format;

public final class FileUtil {
    /**
     * 处理文件名相同的情况
     * @param file 文件对象
     * @return 文件对象
     */
    @SuppressWarnings("unused")
    public static File distinct(File file) {
        return distinct(file, true);
    }

    /**
     * 处理相同文件夹下同名文件
     * @param filePath 文件路径
     * @return 文件对象
     */
    @SuppressWarnings("unused")
    public static File distinct(String filePath) {
        return distinct(filePath, true);

    }

    /**
     * 获取文件名
     * @param file       文件对象
     * @param isDistinct true-去重
     * @return 文件对象
     */
    public static File distinct(File file, boolean isDistinct) {
        return distinct(file.getAbsolutePath(), isDistinct);
    }

    /**
     * 获取文件名
     * @param path       文件路径
     * @param isDistinct true-去重
     * @return 文件对象
     */
    @SuppressWarnings("WeakerAccess")
    public static File distinct(String path, boolean isDistinct) {
        File file = new File(path), p = file.getParentFile();
        String nameWithoutExt = getNameWithoutExt(path);
        String fileExt = FileUtil.getFileExt(path);

        // 循环检查文件是否存在，并返回第一个不存在文件对象
        for (int i = 1; isDistinct && file.exists(); i++) {
            file = new File(p, String.format("%s(%d).%s", //
                    nameWithoutExt, i, fileExt));
        }
        return file;
    }

    /**
     * 获取文件名称(不包括扩展名)
     * @param file 文件对象
     * @return 文件名称(不包括扩展名)
     */
    @SuppressWarnings("WeakerAccess")
    public static String getNameWithoutExt(File file) {
        return getNameWithoutExtension(file.getName());
    }

    /**
     * 获取文件名称(不包括扩展名)
     * @param file 文件路径
     * @return 文件名称(不包括扩展名)
     */
    @SuppressWarnings("WeakerAccess")
    public static String getNameWithoutExt(String file) {
        return getNameWithoutExtension(file);
    }

    /**
     * 获取文件名称(不包括扩展名)
     * @param file 文件路径
     * @return 文件名称(不包括扩展名)
     */
    @SuppressWarnings("unused")
    public static String getNameWithoutExt(Path file) {
        return getNameWithoutExt(file.toFile());
    }

    /**
     * 获取文件扩展名
     * @param file 文件对象
     * @return 文件扩展名
     */
    public static String getFileExt(File file) {
        return getFileExtension(file.getName());
    }

    /**
     * 获取文件扩展名
     * @param file 文件路径
     * @return 文件扩展名
     */
    public static String getFileExt(String file) {
        return getFileExtension(file);
    }

    /**
     * 获取文件扩展名
     * @param file 文件路径
     * @return 文件扩展名
     */
    public static String getFileExt(Path file) {
        return getFileExt(file.toFile());
    }

    /**
     * 获取路径中的文件名
     * @param pathName 文件路径
     * @return 文件名
     */
    public static String getFileName(String pathName) {
        return new File(pathName).getName();
    }

    /**
     * 根据文件名称，生成一个服务器文件名
     * @param fileName 本地文件名
     * @return 服务器文件名
     */
    public static String createFile(String fileName) {
        return uuid() + "." + getFileExt(fileName);
    }

    /**
     * 根据文件名称，生成一个服务器文件名
     * @param fileName 本地文件名
     * @return 服务器文件名
     */
    public static String createFile(File fileName) {
        return uuid() + "." + getFileExt(fileName);
    }

    /**
     * 获取文件类型
     * @param path 文件路径
     * @return 文件类型
     */
    @Nonnull
    public static String getMiniType(Path path) {
        try {
            return Files.probeContentType(path);
        } catch (IOException | Error ignored) {
        }
        return "application/octet-stream";
    }

    /**
     * 获取文件类型
     * @param path 文件路径
     * @return 文件类型
     */
    public static String getMiniType(String path) {
        return getMiniType(Path.of(path));
    }

    /**
     * 获取文件类型
     * @param file 文件对象
     * @return 文件类型
     */
    public static String getMiniType(File file) {
        return getMiniType(file.toPath());
    }

    /**
     * 文件大小单位转换
     * @param fileLength 文件大小
     * @return 格式化结果
     */
    @SuppressWarnings("unused")
    public static String getShowFileSize(long fileLength) {
        double length;
        if ((length = fileLength) < 1024) {
            return format("%.2fB", length);
        }
        if ((length = length / 1024) < 1024) {
            return format("%.2fKB", length);
        }
        if ((length = length / 1024) < 1024) {
            return format("%.2fMB", length);
        }
        if ((length = length / 1024) < 1024) {
            return format("%.2fGB", length);
        }
        return format("%.2fTB", length / 1024);
    }
}
