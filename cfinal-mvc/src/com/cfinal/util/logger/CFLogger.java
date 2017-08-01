/**
 * Created the com.cfinal.util.logger.CFLogger.java
 * @created 2016年9月20日 上午11:06:45
 * @version 1.0.0
 */
package com.cfinal.util.logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * #Level的五个等级OFF(禁用)、SEVERE（严重）、WARNING(警告)、INFO(信息)、CONFIG(配置)、FINE(较好)、FINER(详细)、FINEST（精细） 、ALL(全部) <br />
 * #为 Handler 指定默认的级别（默认为 Level.INFO）。<br />
 * java.util.logging.ConsoleHandler.level=INFO <br/>
 * # 指定要使用的 Formatter 类的名称（默认为 java.util.logging.SimpleFormatter）。 <br/>
 * java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter <br/>
 * # 为 Handler 指定默认的级别（默认为 Level.ALL） <br/>
 * java.util.logging.FileHandler.level=INFO <br/>
 * # 指定要使用的 Formatter 类的名称（默认为 java.util.logging.XMLFormatter） <br/>
 * java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter <br/>
 * # 指定要写入到任意文件的近似最大量（以字节为单位），如果该数为0，则没有限制（默认为无限制） <br/>
 * java.util.logging.FileHandler.limit=20480 <br/>
 * # 指定有多少输出文件参与循环（默认为 1）。 <br/>
 * java.util.logging.FileHandler.count=10 <br/>
 * # 为生成的输出文件名称指定一个模式。有关细节请参见以下内容（默认为 "%h/java%u.log"） <br/>
 * # "/" 本地路径名分隔符 <br/>
 * # "%t" 系统临时目录 <br/>
 * # "%h" "user.home"系统属性的值 <br/>
 * # "%g" 区分循环日志的生成号 <br/>
 * # "%u" 解决冲突的惟一号码 <br/>
 * # "%d" 根据时间生成 <br/>
 * # "%%" 转换为单个百分数符号"%" <br/>
 * # 如果未指定 "%g"字段，并且文件计数大于 1，那么生成号将被添加到所生成文件名末尾的小数点后面 <br/>
 * java.util.logging.FileHandler.pattern=C:/testLogs/log%g.log <br/>
 * # 指定是否应该将 FileHandler 追加到任何现有文件上（默认为 false） <br/>
 * java.util.logging.FileHandler.append=true <br/>
 * # 指定handler <br/>
 * </p>
 * @author XChao
 */
public class CFLogger {
	public static final String HANDLE_CONSOLE = "java.util.logging.ConsoleHandler";
	public static final String HANDLE_FILE = "java.util.logging.FileHandler";
	public static final String LEVEL_ALL = "ALL";
	public static final String LEVEL_FINEST = "FINEST";
	public static final String LEVEL_FINER = "FINER";
	public static final String LEVEL_FINE = "FINE";
	public static final String LEVEL_CONFIG = "CONFIG";
	public static final String LEVEL_INFO = "INFO";
	public static final String LEVEL_WARNING = "WARNING";
	public static final String LEVEL_SEVERE = "SEVERE";
	public static final String LEVEL_OFF = "OFF";

	private static final int DEFAULT_LIMIT = 10485760; // 10M
	private static final int DEFAULT_COUNT = 10;
	private static final String DEFAULT_FORMATTER = "java.util.logging.SimpleFormatter";
	private static final boolean DEFAULT_APPEND = true;

	private static final Properties PROPERTIES = new Properties();

	static {
		CFLogger.init();
	}

	private static String getDefaultLogDir() {
		String logDir = System.getProperty("catalina.home");
		if(logDir == null) {
			logDir = System.getProperty("user.home");
		}
		try {
			File file = new File(logDir + "/logs");
			if(!file.exists()) {
				file.mkdirs();
			}
			return file.toString().replace("\\", "/");
		} catch (Exception e) {
		}
		return "";
	}

	private static void reloadProperties() {
		ByteArrayInputStream inputStream = null;
		try {
			StringBuilder probuilder = new StringBuilder();
			for (Object name : PROPERTIES.keySet()) {
				probuilder.append(name).append("=").append(PROPERTIES.get(name)).append("\r\n");
			}
			inputStream = new ByteArrayInputStream(probuilder.toString().getBytes());
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (Exception e) {
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private static void init() {
		setHandler(new String[] { HANDLE_CONSOLE, HANDLE_FILE });
		setAllLevel(LEVEL_INFO);
		setAllFormatter(DEFAULT_FORMATTER);
		setFileHandlerAppend(DEFAULT_APPEND);
		setFileHandlerLimit(DEFAULT_LIMIT);
		setFileHandlerCount(DEFAULT_COUNT);
		setFileHandlerPattern(getDefaultLogDir() + "/cfinal-all-%g.%u.log");
		CFLogger.reloadProperties();
	}

	public static void set(String handlers, String level) {
		if(StringUtils.isNotBlank(handlers)) {
			PROPERTIES.setProperty("handlers", handlers);
		}
		if(StringUtils.isNotBlank(level)) {
			PROPERTIES.setProperty(".level", level);
			PROPERTIES.setProperty("java.util.logging.FileHandler.level", level);
			PROPERTIES.setProperty("java.util.logging.ConsoleHandler.level", level);
		}
		CFLogger.reloadProperties();
	}

	public static void setHandler(String handlers) {
		PROPERTIES.setProperty("handlers", handlers);
		CFLogger.reloadProperties();
	}

	public static void setHandler(String[] handlers) {
		PROPERTIES.setProperty("handlers", StringUtils.join(handlers, ", "));
		CFLogger.reloadProperties();
	}

	public static void setAllLevel(String level) {
		PROPERTIES.setProperty(".level", level);
		PROPERTIES.setProperty("java.util.logging.FileHandler.level", level);
		PROPERTIES.setProperty("java.util.logging.ConsoleHandler.level", level);
		CFLogger.reloadProperties();
	}

	public static void setFileHandlerLevel(String level) {
		PROPERTIES.setProperty("java.util.logging.FileHandler.level", level);
		CFLogger.reloadProperties();
	}

	public static void setConsoleHandleLevel(String level) {
		PROPERTIES.setProperty("java.util.logging.ConsoleHandler.level", level);
		CFLogger.reloadProperties();
	}

	public static void setAllFormatter(String formatter) {
		PROPERTIES.setProperty("java.util.logging.FileHandler.formatter", formatter);
		PROPERTIES.setProperty("java.util.logging.ConsoleHandler.formatter", formatter);
		CFLogger.reloadProperties();
	}

	public static void setFileHandlerFormatter(String formatter) {
		PROPERTIES.setProperty("java.util.logging.FileHandler.formatter", formatter);
		CFLogger.reloadProperties();
	}

	public static void setConsoleHandleFormatter(String formatter) {
		PROPERTIES.setProperty("java.util.logging.ConsoleHandler.formatter", formatter);
		CFLogger.reloadProperties();
	}

	public static void setFileHandlerLimit(int limit) {
		PROPERTIES.setProperty("java.util.logging.FileHandler.limit", String.valueOf(limit));
		CFLogger.reloadProperties();
	}

	public static void setFileHandlerCount(int count) {
		PROPERTIES.setProperty("java.util.logging.FileHandler.count", String.valueOf(count));
		CFLogger.reloadProperties();
	}

	public static void setFileHandlerAppend(boolean append) {
		PROPERTIES.setProperty("java.util.logging.FileHandler.append", String.valueOf(append));
		CFLogger.reloadProperties();
	}

	public static void setFileHandlerPattern(String pattern) {
		PROPERTIES.setProperty("java.util.logging.FileHandler.pattern", pattern);
		CFLogger.reloadProperties();
	}

	public static void finest(String message) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.FINEST, fullMessage);
	}

	public static void finest(String message, Throwable thrown) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.FINEST, fullMessage, thrown);
	}

	public static void finer(String message) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.FINER, fullMessage);
	}

	public static void finer(String message, Throwable thrown) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.FINER, fullMessage, thrown);
	}

	public static void fine(String message) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.FINE, fullMessage);
	}

	public static void fine(String message, Throwable thrown) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.FINE, fullMessage, thrown);
	}

	public static void config(String message) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.CONFIG, fullMessage);
	}

	public static void config(String message, Throwable thrown) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.CONFIG, fullMessage, thrown);
	}

	public static void info(String message) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.INFO, fullMessage);
	}

	public static void info(String message, Throwable thrown) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.INFO, fullMessage, thrown);
	}

	public static void warning(String message) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.WARNING, fullMessage);
	}

	public static void warning(String message, Throwable thrown) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.WARNING, fullMessage, thrown);
	}

	public static void severe(String message) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.SEVERE, fullMessage);
	}

	public static void severe(String message, Throwable thrown) {
		StackTraceElement element = CFLogger.getStackTraceElement(new Throwable());
		String fullMessage = CFLogger.getFullMessageByElement(element, message);
		java.util.logging.Logger.getLogger(element.getClassName()).log(Level.SEVERE, fullMessage, thrown);
	}

	private static StackTraceElement getStackTraceElement(Throwable throwable) {
		StackTraceElement[] stackTraceElement = throwable.getStackTrace();
		if(stackTraceElement.length > 1) {
			return stackTraceElement[1];
		}
		return stackTraceElement[0];
	}

	private static String getFullMessageByElement(StackTraceElement element, String message) {
		StringBuilder builer = new StringBuilder();
		builer.append(element.getClassName()).append(".").append(element.getMethodName());
		builer.append("[Line:").append(element.getLineNumber()).append("] \r\n");
		return builer.append(message).toString();
	}
}
