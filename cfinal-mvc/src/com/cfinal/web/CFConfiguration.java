/**
 * Created the com.cfinal.web.core.CFConfiguration.java
 * @created 2017年8月20日 下午11:27:40
 * @version 1.0.0
 */
package com.cfinal.web;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import com.cfinal.db.CFDBFactory;
import com.cfinal.util.logger.CFLog;
import com.cfinal.web.http.interceptor.CFInterceptor;
import com.cfinal.web.timmer.CFTimmer;

/**
 * com.cfinal.web.core.CFConfiguration.java
 * @author XChao
 */
public abstract class CFConfiguration {

	protected abstract void initialize(CFServletContext context) throws Exception;

	protected abstract String getEncoding(CFServletContext context) throws Exception;

	protected abstract String getActionSuffix(CFServletContext context) throws Exception;

	protected abstract long getWorkerid(CFServletContext context) throws Exception;

	protected abstract String getLoginUrl(CFServletContext context) throws Exception;

	protected abstract Map<String, List<Class<? extends CFInterceptor>>> getInterceptor(CFServletContext context)
		throws Exception;

	protected abstract MultipartConfigElement getMultipartConfigElement(CFServletContext context) throws Exception;

	protected abstract Map<Class<? extends CFTimmer>, String> getTimmers(CFServletContext context) throws Exception;

	protected abstract Map<String, DataSource> getDataSource(CFServletContext context) throws Exception;

	protected final void onStartup(CFServletContext context) throws Exception {
		// 初始化方法
		this.initialize(context);
		// 编码配置
		context.setEncoding(this.getEncoding(context));
		// URL后缀配置
		context.setActionSuffix(this.getActionSuffix(context));
		// 机器唯一ID配置
		context.setWorkerid(this.getWorkerid(context));
		// 默认登录URL配置
		context.setLoginUrl(this.getLoginUrl(context));
		// 文件上传配置
		context.setMultipartConfigElement(this.getMultipartConfigElement(context));
		// 定时任务配置
		for (Entry<Class<? extends CFTimmer>, String> entry : getTimmers(context).entrySet()) {
			context.addTimmer(entry.getKey(), entry.getValue());
		}
		// 数据库配置
		for (Entry<String, DataSource> entry : getDataSource(context).entrySet()) {
			CFDBFactory.addDataSource(entry.getKey(), entry.getValue());
		}
		// 验证临时目录是否存在， 如果不存在则创建文件夹
		File file = new File(context.getMultipartConfigElement().getLocation());
		if(file != null && !file.exists() && file.mkdirs()) {
			CFLog.debug("create forder : " + file.getAbsolutePath());
		}

		CFLog.debug(this.getClass().getName() + ".onStartup. ");
	}
}
