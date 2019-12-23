package com.mini.web.test.util;

import com.mini.web.test.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static com.mini.core.util.FileUtil.createFile;

/**
 * 文件管理上下文，处理附件图片等
 * @author XChao
 */
public final class FileGenerator {
	static final DateFormat FORMAT = new SimpleDateFormat("yyyyMM/dd/HH/");

	/**
	 * 获取根据时间缀生成的文件路径在数据库存储的路径部分
	 * @param fileName 文件后缀带 '.' 比如: (.png, .txt, .zip )
	 * @return 数据库存储的文件路径
	 */
	public static String getDBPath(String fileName) {
		return FORMAT.format(new Date()) + createFile(fileName);
	}

	/**
	 * 根据文件在数据库存储的路径,获取公共文件在硬盘或者其它存储中的绝对路径
	 * @param dbPath 数据库部分路径、
	 * @return 绝对路径
	 */
	public static String getPublicFullPath(String dbPath) {
		File file = new File(R.getPath(), dbPath);
		// 文件夹存在时，返回文件路径
		if (file.getParentFile().exists()) {
			return file.getAbsolutePath();
		}
		// 文件夹不存，但创建成功时，返回文件路径
		if (file.getParentFile().mkdirs()) {
			return file.getAbsolutePath();
		}
		// 创建文件夹失败
		return null;
	}

	/**
	 * 根据文件在数据库存储的路径,获取该文件在网页中访问的绝对路径
	 * @param dbPath 数据库部分路径
	 * @return 绝对地址
	 */
	public static String getPublicFullUrl(String dbPath) {
		Pattern pattern = Pattern.compile("(\\w+)(://)(\\S*)");
		return pattern.matcher(dbPath).matches() ? dbPath : R.getUrl() + dbPath;
	}

}
