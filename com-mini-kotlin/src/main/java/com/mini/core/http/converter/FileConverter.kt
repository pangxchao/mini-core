package com.mini.core.http.converter

import com.mini.core.http.HttpUtil
import com.mini.core.util.FileUtil
import com.mini.core.util.PKGenerator
import okhttp3.Call
import okhttp3.Response
import okio.buffer
import okio.source
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.function.Consumer
import java.util.function.LongConsumer
import java.util.function.ObjLongConsumer

@Suppress("unused")
class FileConverter : Converter<File?> {
    private var outputDir: String? = null
    private var fileName: String? = null
    // 已下载长度
    private var length: Long = 0
    // 请求暂停时回调
    private var onCancel: LongConsumer? = null
    // 生成本地文件存放路径时回调
    private var onMakeFileName: Consumer<File>? = null
    // 下载进度回调
    private var onDownload: ObjLongConsumer<Long>? = null

    @Suppress("unused")
    constructor(outputDir: String?) {
        this.outputDir = outputDir
    }

    @Suppress("unused")
    constructor(outputDir: String?, fileName: String?) {
        this.outputDir = outputDir
        this.fileName = fileName
    }

    @Suppress("unused")
    fun setOutputDir(outputDir: String?): FileConverter {
        this.outputDir = outputDir
        return this
    }

    @Suppress("unused")
    fun setFileName(fileName: String?): FileConverter {
        this.fileName = fileName
        return this
    }

    @Suppress("unused")
    fun setOnCancel(onCancel: LongConsumer?): FileConverter {
        this.onCancel = onCancel
        return this
    }

    @Suppress("unused")
    fun setOnMakeFileName(onMakeFileName: Consumer<File>?): FileConverter {
        this.onMakeFileName = onMakeFileName
        return this
    }

    @Suppress("unused")
    fun setOnDownload(onDownload: ObjLongConsumer<Long>?): FileConverter {
        this.onDownload = onDownload
        return this
    }

    @Throws(IOException::class)
    override fun apply(call: Call, response: Response): File? {
        if (!response.isSuccessful) throw IOException(response.message())
        return try {
            response.body()?.use { body ->
                // 文件夹不存在，并且创建换
                var outputFile = File(outputDir ?: "")
                if (!outputFile.exists() && !outputFile.mkdirs()) {
                    throw IOException("下载文件地址不存在")
                }
                // 返回的没有Content-Range 不支持断点下载，需要重新下载
                val contentRange = HttpUtil.getContentRange(response)
                // 总长度
                val totalLength: Long
                if (contentRange == null) {
                    length = 0
                    totalLength = HttpUtil.getContentLength(response)
                } else {
                    length = contentRange[0]
                    totalLength = contentRange[2]
                }
                // 如果输入文件对象不为文件
                if (!outputFile.isFile) {
                    if (fileName?.trim { it <= ' ' }?.isEmpty() == true) {
                        fileName = HttpUtil.getContentDispositionFileName(response)
                    }
                    if (fileName?.trim { it <= ' ' }?.isEmpty() == true) {
                        fileName = PKGenerator.id().toString()
                    }
                    outputFile = File(outputFile, fileName ?: "")
                }
                // 处理非断点续传文件重名问题
                outputFile = FileUtil.distinct(outputFile, contentRange == null)
                if (!outputFile.exists() && !outputFile.createNewFile()) {
                    throw IOException("创建文件失败")
                }
                // 通知文件存放路径
                onMakeFileName(outputFile)
                body.byteStream().source().buffer().use { source ->
                    FileOutputStream(outputFile, true).use { stream ->
                        val buf = ByteArray(2048)
                        var len: Int
                        while (source.read(buf).also { len = it } != -1) {
                            stream.write(buf, 0, len)
                            length += len
                            onDownload(totalLength, length)
                        }
                    }
                }
                return outputFile
            }
        } catch (exception: IOException) {
            if (call.isCanceled) {
                onCancel(length)
            }
            throw exception
        }
    }

    // 取消下载
    private fun onCancel(downloadLength: Long) {
        if (onCancel == null) return
        onCancel!!.accept(downloadLength)
    }

    // 生成本地文件对象回调
    private fun onMakeFileName(file: File) {
        if (onMakeFileName == null) return
        onMakeFileName!!.accept(file)
    }

    // 下载进度回调
    private fun onDownload(totalLength: Long, downloadLength: Long) {
        if (onDownload == null) return
        onDownload!!.accept(totalLength, downloadLength)
    }
}