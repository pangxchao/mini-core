/**
 * Created the com.cfinal.web.CFListener.java
 * @created 2016年9月24日 下午12:30:02
 * @version 1.0.0
 */
package com.cfinal.web;

import java.io.File;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.annotation.WebListener;

import org.apache.commons.lang.StringUtils;

import com.cfinal.util.logger.CFLogger;
import com.cfinal.web.central.CFContext;
import com.cfinal.web.central.CFInitialize;
import com.cfinal.web.control.CFActionPorxy;
import com.cfinal.web.scheduler.CFTaskScheduler;

/**
 * com.cfinal.web.CFListener.java
 * @author XChao
 */
@WebListener
public class CFListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			// 获取全局配置 IApplicationContext
			CFContext context = CFInitialize.init(event.getServletContext());
			// 动态注册 DefaultServlet
			Dynamic register = context.addServlet("CFServlet", CFServlet.class);
			// 获取 当前项目中所有 的 Action 代理对象
			Map<String, CFActionPorxy> actionMap = context.getActionMap();
			// 为 DefaultServlet 设置 url-mapping， 当 url-mappng为 "" 或者 "/"时抛出异常
			for (String actionName : actionMap.keySet()) {
				String urlMapping = actionName.replaceFirst(context.getContextPath(), "");
				if(StringUtils.equals("", urlMapping) || StringUtils.equals("/", urlMapping)) {
					throw new RuntimeException("Action URL cannot be blank. ");
				}
				register.addMapping(urlMapping);
			}
			// 检查文件上传临时目录是否存在, 如果不存在则创建,否则可能会接收不到参数
			File location = new File(context.getFileUploadRepository());
			// 如果该文件夹或者文件不存时, 则创建该文件夹或者文件
			if(!location.exists() && location.mkdirs()) {
			}
			// 设置文件上传配置信息
			register.setMultipartConfig(new MultipartConfigElement(location.getAbsolutePath(),
				context.getFileUploadSizeMax(), context.getFileUploadSizeMax(), context.getFileUploadSizeThreshold()));
			// 设置可以异步返回
			register.setAsyncSupported(true);
		} catch (Throwable e) {
			CFLogger.severe("ServletContextListener error.", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// 停止定时 任务
		CFTaskScheduler.stopTask();
	}
}
