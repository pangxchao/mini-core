package com.mini.web.config;

import javax.inject.Singleton;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.Serializable;

@Singleton
public final class MultipartConfigure implements Serializable {
    private static final long serialVersionUID = -770217363684714360L;
    private int fileSizeThreshold = 4096;
    private long maxRequestSize = -1L;
    private String location = "temp";
    private long maxFileSize = -1L;

    /**
     * 设置文件缓冲区大小
     * @param fileSizeThreshold The value of fileSizeThreshold
     * @return {@Code #this}
     */
    public MultipartConfigure setFileSizeThreshold(int fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
        return this;
    }

    /**
     * 文件上传时所有文件大小限制
     * @param maxRequestSize The value of maxRequestSize
     * @return {@Code #this}
     */
    public MultipartConfigure setMaxRequestSize(long maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
        return this;
    }

    /**
     * 单个文件大小限制
     * @param maxFileSize The value of maxFileSize
     * @return {@Code #this}
     */
    public MultipartConfigure setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
        return this;
    }

    /**
     * 设置文件上传的临时目录
     * @param location The value of location
     * @return {@Code #this}
     */
    public MultipartConfigure setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * 获取文件上传配置
     * @return 文件上传配置
     */
    public MultipartConfigElement getMultipartConfigElement() {
        File file = new File(this.location);
        if (!file.exists() || file.mkdirs()) {
            return null;
        }
        return new MultipartConfigElement(file.getAbsolutePath(),
                maxFileSize, maxRequestSize, fileSizeThreshold);
    }
}
