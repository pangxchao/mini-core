package com.mini.util.http.download;

import com.mini.util.PKGenerator;
import com.mini.util.http.Converter;
import com.mini.util.http.HttpUtil;
import com.mini.util.http.builder.FormBuilder;
import com.mini.util.http.call.HttpCall;
import com.mini.util.lang.FileUtil;
import com.mini.util.lang.Function;
import okhttp3.Call;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 下载信息
 *
 * @author xchao
 */
public class DownloadInfo {

    public static final int STATUS_WAIT = 0; // 队列等待中
    public static final int STATUS_PAUSE = 1; // 暂停
    public static final int STATUS_DOWNLOADING = 2; // 下载中
    public static final int STATUS_FINISH = 3; // 下载完成
    public static final int STATUS_FAIL = 4; // 下载失败

    protected int status;
    protected final long id;
    protected long totalLength;
    protected long downloadLength;
    protected HttpCall<File> call; //调用器
    protected Converter<File> converter; // 数据转换对象
    protected Function.F1<Long> onPause; // 请求暂停时回调
    protected Function.F1<Long> onStart; // 请求开始时的回调
    protected final FormBuilder builder; // 参数构建器
    protected Function.F3<Long, Call, File> onSuccess; // 成功返回时回调
    protected Function.F3<Long, Long, Long> onDownload; // 下载进度回调
    protected Function.F3<Long, Call, IOException> onFail; // 请求失败时的回调

    public DownloadInfo(FormBuilder builder, File inputFile) {
        this.builder = builder;
        id = PKGenerator.key();
        if (!inputFile.exists() && !inputFile.mkdirs()) {
            this.onFail(null, new IOException("创建文件夹失败"));
            return;
        }
        // 获取文件大小，计算出已经下载的文件大小，然后传片
        if (inputFile.isFile() && inputFile.length() > 0) {
            downloadLength = inputFile.length();
            builder.getRequest().header("Range", "bytes=" + downloadLength + "-");
        }
        // 创建文件转换器
        this.converter = (call, response) -> {
            if (!inputFile.exists()) return null;
            // "Accept-Ranges", "bytes" // 允许断点续传
            try (ResponseBody body = Objects.requireNonNull(response.body())) {
                long[] contentRange = HttpUtil.getContentRange(response);
                // 返回的没有Content-Range 不支持断点下载，需要重新下载
                if (contentRange == null) {
                    downloadLength = 0;
                    totalLength = HttpUtil.getContentLength(response);
                } else {
                    downloadLength = contentRange[0];
                    totalLength = contentRange[2];
                }
                File outputFile = inputFile;
                if (!outputFile.isFile()) { // 如果输入文件对象不为文件（为文件夹时）
                    String fileName = HttpUtil.getContentDispositionFileName(response);
                    outputFile = new File(outputFile, fileName != null ? fileName : PKGenerator.key() + "");
                }
                // 处理非断点续传文件重名问题
                outputFile = FileUtil.distinct(outputFile, contentRange == null);
                if (!outputFile.exists() && !outputFile.createNewFile()) {
                    onFail(null, new IOException("创建文件失败"));
                    return null;
                }
                // 这里两种写法，不知道哪种是对的，两种都是对的，不知道哪种效率好点
                // 暂时先保留上面种，测试下是否正确，效率怎么样
                try (BufferedSource source = Okio.buffer(Okio.source(body.byteStream()))) {
                    try (BufferedSink sink = Okio.buffer(Okio.sink(outputFile))) {
                        sink.getBuffer().skip(downloadLength); // 跳过指定的字节数
                        byte[] buf = new byte[2048]; //每次读取2kb
                        int length;
                        while ((length = source.read(buf, 0, buf.length)) != -1) {
                            sink.write(buf, 0, length);
                            downloadLength = downloadLength + length;
                            onDownload(totalLength, downloadLength);
                        }
                        sink.writeAll(source);
                    }
                }

//            try (RandomAccessFile file = new RandomAccessFile(outputFile, "rwd")) {
//                // Chanel NIO中的用法，由于RandomAccessFile没有使用缓存策略，直接使用会使得下载速度变慢，
//                // 亲测缓存下载3.3秒的文件，用普通的RandomAccessFile需要20多秒。
//                // 内存映射，直接使用RandomAccessFile，是用其seek方法指定下载的起始位置，使用缓存下载，在这里指定下载位置。
//                MappedByteBuffer mappedBuffer = file.getChannel().map(FileChannel.MapMode.READ_WRITE, downloadLength, totalLength);
//                try (InputStream is = body.byteStream()) {
//                    byte[] buf = new byte[2048]; // 每次读取2kb
//                    for (int len; (len = is.read(buf)) != -1; ) {
//                        file.write(buf, 0, len);
//                    }
//                }
//            }
                return outputFile;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
//        request.setConverter();
    }

    /**
     * 暂停
     */
    protected void onPause() {
        status = STATUS_PAUSE;
        if (onPause != null) onPause.apply(id);
    }

    /**
     * 开始
     */
    protected void onStart() {
        status = STATUS_DOWNLOADING;
        if (onStart != null) onStart.apply(id);
    }

    /**
     * 成功
     *
     * @param call
     * @param file
     */
    protected void onSuccess(Call call, File file) {
        status = STATUS_FINISH;
        if (onSuccess != null) onSuccess.apply(id, call, file);
    }

    /**
     * 下载进度
     *
     * @param total
     * @param downloadLength
     */
    protected void onDownload(long total, long downloadLength) {
        if (onDownload != null) onDownload.apply(id, total, downloadLength);
    }

    /**
     * 失败回调
     *
     * @param call
     * @param e
     */
    protected void onFail(Call call, IOException e) {
        status = STATUS_FAIL;
        if (onFail != null) onFail.apply(id, call, e);
    }

    /**
     * 设置暂停回调函数
     *
     * @param onPause
     * @return
     */
    public DownloadInfo setOnPause(Function.F1<Long> onPause) {
        this.onPause = onPause;
        return this;
    }

    /**
     * 设置开始回调函数
     *
     * @param onStart
     * @return
     */
    public DownloadInfo setOnStart(Function.F1<Long> onStart) {
        this.onStart = onStart;
        return this;
    }

    /**
     * 设置成功回调函数
     *
     * @param onSuccess
     * @return
     */
    public DownloadInfo setOnSuccess(Function.F3<Long, Call, File> onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }


    /**
     * 设置下载回调函数
     *
     * @param onDownload
     * @return
     */
    public DownloadInfo setOnDownload(Function.F3<Long, Long, Long> onDownload) {
        this.onDownload = onDownload;
        return this;
    }

    /**
     * 设置错误回调
     *
     * @param onFail
     * @return
     */
    public DownloadInfo setOnFail(Function.F3<Long, Call, IOException> onFail) {
        this.onFail = onFail;
        return this;
    }

    /**
     * 获取下载唯一标识
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * 下载状态
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * 需要下载的总大小
     *
     * @return
     */
    public long getTotalLength() {
        return totalLength;
    }

    /**
     * 已经下载的总大小
     *
     * @return
     */
    public long getDownloadLength() {
        return downloadLength;
    }

    /**
     * GET 方式提交数据， 该方法无法回调进度<br/>
     * 该方式请求时，参数只能通过url和FormBuilder方式添加<br/>
     *
     * @return
     */
    public final void getStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return;
        call = builder.get(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).enqueue();
    }

    /**
     * HEAD 方式提交数据， 该方式无法提交进度<br/>
     * 该方式请求时，参数只能通过url和FormBuilder方式添加<br/>
     *
     * @return
     */
    public final void headStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return;
        call = builder.head(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).enqueue();
    }


    /**
     * POST 方式提交数据
     *
     * @return
     */
    public final void postStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return;
        call = builder.post(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).enqueue();
    }

    /**
     * DELETE 方式提交数据
     *
     * @return
     */
    public final void deleteStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return;
        call = builder.delete(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).enqueue();
    }

    /**
     * PUT 方式提交数据
     *
     * @return
     */
    public final void putStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return;
        call = builder.put(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).enqueue();
    }

    /**
     * PATCH 方式提交数据
     *
     * @return
     */
    public final void patchStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return;
        call = builder.patch(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).enqueue();
    }

    /**
     * GET 方式提交数据， 该方法无法回调进度<br/>
     * 该方式请求时，参数只能通过url和FormBuilder方式添加<br/>
     *
     * @return
     */
    public final File synchGetStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return null;
        call = builder.get(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        return call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).executed();
    }

    /**
     * HEAD 方式提交数据， 该方式无法提交进度<br/>
     * 该方式请求时，参数只能通过url和FormBuilder方式添加<br/>
     *
     * @return
     */
    public final File synchHeadStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return null;
        call = builder.head(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        return call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).executed();
    }


    /**
     * POST 方式提交数据
     *
     * @return
     */
    public final File synchPostStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return null;
        call = builder.post(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        return call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).executed();
    }

    /**
     * DELETE 方式提交数据
     *
     * @return
     */
    public final File synchDeleteStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return null;
        call = builder.delete(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        return call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).executed();
    }

    /**
     * PUT 方式提交数据
     *
     * @return
     */
    public final File synchPutStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return null;
        call = builder.put(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        return call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).executed();
    }

    /**
     * PATCH 方式提交数据
     *
     * @return
     */
    public final File synchPatchStart() {
        if (status == STATUS_DOWNLOADING || status == STATUS_FINISH) return null;
        call = builder.patch(File.class).setConverter(converter).setOnSuccess(this::onSuccess);
        return call.setOnPause(this::onPause).setOnStart(this::onStart).setOnFail(this::onFail).executed();
    }

    /**
     * 暂停
     */
    public void pause() {
        if (status == STATUS_FINISH || status == STATUS_FAIL) return;
        if (status != STATUS_PAUSE && call != null && !call.isCanceled()) {
            call.cancel();
        }
    }
}
