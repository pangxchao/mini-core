/**
 * Created the com.cfinal.util.CFFileGenerator.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package sn.mini.kotlin.util

import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URLConnection
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

/**
 * 文件管理上下文，处理附件图片等
 *
 * @author XChao
 */
object FileGenerator {
	private val FORMAT = SimpleDateFormat("yyyyMM/dd/HH/")
	private var privatePath = ""
	private var publicFilePath = ""
	private var publicFileUrl = ""

	/**
	 * @param privateFilePath
	 * the privateFilePath to set
	 */
	fun setPrivateFilePath(privatePath: String) {
		FileGenerator.privatePath = privatePath
	}

	/**
	 * @param publicFilePath
	 * the publicFilePath to set
	 */
	fun setPublicFilePath(publicFilePath: String) {
		FileGenerator.publicFilePath = publicFilePath
	}

	/**
	 * @param publicFileUrl
	 * the publicFileUrl to set
	 */
	fun setPublicFileUrl(publicFileUrl: String) {
		FileGenerator.publicFileUrl = publicFileUrl
	}

	/**
	 * 获取根据时间缀生成的文件路径在数据库存储的路径部分
	 *
	 * @param fileExt
	 * 文件后缀带 '.' 比如: (.png, .txt, .zip )
	 * @return
	 */
	fun getDateDBPath(fileExt: String): String {
		return FORMAT.format(Date(System.currentTimeMillis())) + PKGenerator.key() + fileExt
	}

	/**
	 * 根据主键生成器生成的ID生成文件路径在数据库存储部分
	 *
	 * @param key
	 * PKGenerator.key() 生成的值
	 * @param fileExt
	 * 文件后缀带 '.' 比如: (.png, .txt, .zip )
	 * @return
	 */
	fun getKeyDBPath(key: Long, fileExt: String): String {
		return FORMAT.format(Date(PKGenerator.millis(key))) + key + fileExt
	}

	/**
	 * 根据文件在数据库存储的路径,获取私有文件在硬盘或者其它存储中的绝对路径
	 *
	 * @param dbPath
	 * 数据库路径
	 */
	fun getPrivateFullPath(dbPath: String): String {
		val file = File(privatePath + dbPath)
		if (!file.parentFile.exists() && !file.parentFile.mkdirs()) {
			RuntimeException("创建文件夹失败: " + file.parentFile.absolutePath)
		}
		return file.absolutePath
	}

	/**
	 * 根据文件在数据库存储的路径,获取公共文件在硬盘或者其它存储中的绝对路径
	 *
	 * @param dbPath
	 * 数据库部分路径
	 */
	fun getPublicFullPath(dbPath: String): String {
		val file = File(publicFilePath + dbPath)
		if (!file.parentFile.exists() && !file.parentFile.mkdirs()) {
			RuntimeException("创建文件夹失败: " + file.parentFile.absolutePath)
		}
		return file.absolutePath
	}

	/**
	 * 根据文件在数据库存储的路径,获取该文件在网页中访问的绝对路径
	 *
	 * @param dbPath
	 * @return
	 */
	fun getPublicFullUrl(dbPath: String): String {
		val pattern = Pattern.compile("(\\w+)(://|://)(\\S*)")
		return if (pattern.matcher(dbPath).matches()) dbPath else publicFileUrl + dbPath
	}

	/**
	 * 根据文件名返回文件后缀名
	 *
	 * @param name
	 * @return
	 */
	fun getSuffix(name: String?): String {
		val index = name?.lastIndexOf(".") ?: -1
		return if (index >= 0) name!!.substring(index) else ""
	}

	/**
	 * 获取文件后缀
	 *
	 * @param name
	 * @param isDot
	 * 后缀名前是否带“.”
	 * @return
	 */
	fun getSuffix(name: String?, isDot: Boolean): String {
		val index = name?.lastIndexOf(".") ?: -1
		return if (index >= 0) name!!.substring(if (isDot) index else index + 1) else ""
	}

	/**
	 * 获取文件类型
	 *
	 * @param file
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@Throws(MalformedURLException::class, IOException::class)
	fun getMiniType(file: File): String {
		return URLConnection.getFileNameMap().getContentTypeFor(file.absolutePath)
	}
}
