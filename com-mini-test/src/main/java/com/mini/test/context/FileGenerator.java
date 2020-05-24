package com.mini.test.context;

import com.mini.core.util.PKGenerator;
import com.mini.core.util.StringUtil;
import com.mini.core.web.util.ResponseCode;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件管理上下文，处理附件图片等
 * @author XChao
 */
public final class FileGenerator implements ResponseCode, Serializable {
	static DateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd/");
	@Inject
	@Named("FilePath")
	private static String filePath = "";
	
	/**
	 * 根据主键生成器生成的ID生成文件路径在数据库存储部分
	 * @param fileExt 文件后缀带 '.' 比如: (.png, .txt, .zip )
	 * @return dbPath
	 */
	public static String getDBPath(String fileExt) {
		fileExt = "." + StringUtil.strip(fileExt, ".");
		String fileName = PKGenerator.id() + fileExt;
		return FORMAT.format(new Date()) + fileName;
	}
	
	/**
	 * 根据文件在数据库存储的路径,获取公共文件在硬盘或者其它存储中的绝对路径
	 * @param dbPath 数据库部分路径
	 */
	public static String getFullPath(String dbPath) {
		File file = new File(filePath + dbPath);
		if (file.getParentFile().exists()) {
			return file.getAbsolutePath();
		}
		if (file.getParentFile().mkdirs()) {
			return file.getAbsolutePath();
		}
		throw new RuntimeException("Folder Create Fail.");
	}
}
