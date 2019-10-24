package com.mini.http.converter;

import com.mini.http.HttpUtil;
import com.mini.util.FileUtil;
import com.mini.util.PKGenerator;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.ObjLongConsumer;

public class FileConverter implements Converter<File> {

    private String outputDir;
    private String fileName;

    // 已下载长度
    private long length;
    // 请求暂停时回调
    private LongConsumer onCancel;
    // 生成本地文件存放路径时回调
    private Consumer<File> onMakeFileName;
    // 下载进度回调
    private ObjLongConsumer<Long> onDownload;

    public FileConverter() {
    }

    public FileConverter(String outputDir) {
        this.outputDir = outputDir;
    }

    public FileConverter(String outputDir, String fileName) {
        this.outputDir = outputDir;
        this.fileName  = fileName;
    }

    public FileConverter setOutputDir(String outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public FileConverter setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileConverter setOnCancel(LongConsumer onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    public FileConverter setOnMakeFileName(Consumer<File> onMakeFileName) {
        this.onMakeFileName = onMakeFileName;
        return this;
    }

    public FileConverter setOnDownload(ObjLongConsumer<Long> onDownload) {
        this.onDownload = onDownload;
        return this;
    }

    @Override
    public File apply(@Nonnull Call call, @Nonnull Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException(response.message());
        try (ResponseBody body = Objects.requireNonNull(response.body())) {
            // 文件夹不存在，并且创建换
            File outputFile = new File(outputDir);
            if (!outputFile.exists() && !outputFile.mkdirs()) {
                throw new IOException("下载文件地址不存在");
            }

            // 返回的没有Content-Range 不支持断点下载，需要重新下载
            long[] contentRange = HttpUtil.getContentRange(response);
            // 总长度
            long totalLength;
            if (contentRange == null) {
                length      = 0;
                totalLength = HttpUtil.getContentLength(response);
            } else {
                length      = contentRange[0];
                totalLength = contentRange[2];
            }

            // 如果输入文件对象不为文件
            if (!outputFile.isFile()) {
                if (fileName == null || fileName.trim().length() == 0) {
                    fileName = HttpUtil.getContentDispositionFileName(response);
                }
                if (fileName == null || fileName.trim().length() == 0) {
                    fileName = String.valueOf(PKGenerator.id());
                }
                outputFile = new File(outputFile, fileName);
            }

            // 处理非断点续传文件重名问题
            outputFile = FileUtil.distinct(outputFile, contentRange == null);
            if (!outputFile.exists() && !outputFile.createNewFile()) {
                throw new IOException("创建文件失败");
            }

            // 通知文件存放路径
            FileConverter.this.onMakeFileName(outputFile);

            // 写数据
            try (BufferedSource source = Okio.buffer(Okio.source(body.byteStream()))) {
                try (FileOutputStream stream = new FileOutputStream(outputFile, true)) {
                    byte[] buf = new byte[2048];
                    for (int len; (len = source.read(buf)) != -1; ) {
                        stream.write(buf, 0, len);
                        length = length + len;
                        onDownload(totalLength, length);
                    }
                }
            }
            return outputFile;
        } catch (IOException exception) {
            if (call.isCanceled()) {
                onCancel(length);
            }
            throw exception;
        }
    }

    // 取消下载
    private void onCancel(long downloadLength) {
        if (onCancel == null) return;
        this.onCancel.accept(downloadLength);
    }

    // 生成本地文件对象回调
    private void onMakeFileName(File file) {
        if (onMakeFileName == null) return;
        this.onMakeFileName.accept(file);
    }

    // 下载进度回调
    private void onDownload(long totalLength, long downloadLength) {
        if (FileConverter.this.onDownload == null) return;
        onDownload.accept(totalLength, downloadLength);
    }
}
