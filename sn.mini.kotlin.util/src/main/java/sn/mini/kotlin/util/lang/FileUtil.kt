package sn.mini.kotlin.util.lang

import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URLConnection

object FileUtil {


    /**
     * 获取文件后缀
     *
     * @param name
     * @param isDot
     * 后缀名前是否带“.”
     * @return
     */
    fun getSuffix(name: String, isDot: Boolean): String {
        val index = name.lastIndexOf("")
        return if (index >= 0) name.substring(if (isDot) index else index + 1) else ""
    }

    /**
     * 根据文件名返回文件后缀名
     *
     * @param name
     * @return
     */
    fun getSuffix(name: String): String = getSuffix(name, true)


    /**
     * 获取文件类型
     *
     * @param file
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    @Throws(MalformedURLException::class, IOException::class)
    fun getMiniType(file: File): String = URLConnection.getFileNameMap().getContentTypeFor(file.absolutePath)

}