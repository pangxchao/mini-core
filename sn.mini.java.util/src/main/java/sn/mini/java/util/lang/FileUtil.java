package sn.mini.java.util.lang;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public final class FileUtil {
    private FileUtil() {}

    /**
     * 获取文件片段数据
     *
     * @param file
     * @param offset
     * @param blockSize
     * @return
     */
    public static byte[] getBlock(File file, long offset, int blockSize) {
        byte[] result = new byte[blockSize];
        try (RandomAccessFile accessFile = new RandomAccessFile(file, "r")) {
            accessFile.seek(offset);
            int readSize = accessFile.read(result);
            if (readSize == -1) {
                return null;
            } else if (readSize == blockSize) {
                return result;
            } else {
                byte[] tmpByte = new byte[readSize];
                System.arraycopy(result, 0, tmpByte, 0, readSize);
                return tmpByte;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理相同文件夹下同名文件
     *
     * @param filePath
     * @return
     */
    public static File distinct(String filePath) {
        return distinct(new File(filePath));

    }

    /**
     * 文件去重命名
     *
     * @param file
     * @return
     */
    public static File distinct(File file) {
        return distinct(file, true);
    }

    /**
     * 获取文件名
     *
     * @param file
     * @param additional true-去重
     * @return
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
     * 获取路径中的文件名
     *
     * @param pathName
     * @return
     */
    public static String getFileName(String pathName) {
        try {
            String path = new URI(pathName).getPath();
            if (StringUtil.isBlank(path)) return path;
            int index = path.lastIndexOf("/");
            return index == -1 ? path : path.substring(index + 1);
        } catch (Exception e) {
            return null;
        }
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

    /**
     * 文件大小单位转换
     *
     * @param fileLength
     * @return
     */
    public static String getShowFileSize(long fileLength) {
        double length = fileLength * 1.0;
        if (length < 1024) {
            return String.format("%.2fB", length);
        }
        if ((length = length / 1024) < 1024) {
            return String.format("%.2fKB", length);
        }
        if ((length = length / 1024) < 1024) {
            return String.format("%.2fMB", length);
        }
        if ((length = length / 1024) < 1024) {
            return String.format("%.2fGB", length);
        }
        return String.format("%.2fTB", length / 1024);
    }

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://127.0.0.1/cloud/aa/bb.htm?ab=1");
        System.out.println(url.getPath());
        System.out.println(url.getQuery());
    }
}
