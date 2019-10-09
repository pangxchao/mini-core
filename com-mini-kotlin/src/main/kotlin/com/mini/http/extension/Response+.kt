package com.mini.http.extension

import com.mini.http.RangeParse
import okhttp3.Response
import java.util.*

/**
 * Content-Length=123456
 * @param response Response
 * @return 123456
 */
@Suppress("unused")
fun getContentLength(response: Response): Long? = response.header("Content-Length")?.trim()?.toLong()

/**
 * attachment; filename="file_name.jpg"
 * @param response Response
 * @return {"attachment": null, "filename": "file_name.jpg"}
 */
fun getContentDisposition(response: Response): Map<String, String?> {
    val result = HashMap<String, String?>()
    response.header("Content-Disposition")?.trim()?.split(";")?.forEach {
        val index = it.trim().indexOf("=")
        if (index <= 0) {
            result[it.trim()] = null
        } else {
            result[it.substring(0, index)] = it.substring(index + 1)
        }
    }
    return result
}

/**
 * attachment; filename="file_name.jpg"
 * @param response Response
 * @return "file_name.jpg"
 */
@Suppress("unused")
fun getContentDispositionFileName(response: Response): String? {
    return getContentDisposition(response)["filename"]
}

/**
 * bytes startPos-endPos/fileLength
 * @param response Response
 * @return long[]{startPos, endPos, fileLength}
 */
@Suppress("unused")
fun getContentRange(response: Response): List<RangeParse.Range>? {
    return response.header("Content-Range")?.let {
        return RangeParse.parseRange(it)
    }
}

