/**
 * Created the com.cfinal.web.core.CFServletContainerInitializer.java
 * @created 2017年8月20日 下午11:26:06
 * @version 1.0.0
 */
package com.cfinal.web;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.http.CFHttpServlet;

/**
 * com.cfinal.web.core.CFServletContainerInitializer.java
 * @author XChao
 */
@HandlesTypes({ CFConfiguration.class, CFService.class, CFHttpServlet.class })
public class CFServletContainerInitializer implements ServletContainerInitializer {
	@Override
	public void onStartup(Set<Class<?>> applicationInitializer, ServletContext ctx) throws ServletException {
		try {
			CFServletContext.createNewInstance(ctx);
			// 如果 applicationInitializer 为空， 则不处理
			if(applicationInitializer == null) {
				return;
			}
			// CFConfiguration 实现类集合
			List<CFConfiguration> configurations = new ArrayList<>();
			// CFAbstract 实现类集合
			List<CFService> abstracts = new ArrayList<>();
			// CFHttpServlet 实现类集合
			List<CFHttpServlet> httpServlets = new ArrayList<>();
			for (Class<?> clazz : applicationInitializer) { // 遍历 applicationInitializer 对象
				if(clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
					continue; // 如果该类为接口或者 抽象类时，则不处理
				}
				
				// 读取 CFHttpServlet 实现
				if(CFHttpServlet.class.isAssignableFrom(clazz)) {
					httpServlets.add(CFHttpServlet.class.cast(clazz.newInstance()));
					continue;
				}
				
				// 读取 CFAbstract 实现
				if(CFService.class.isAssignableFrom(clazz)) {
					abstracts.add(CFService.class.cast(clazz.newInstance()));
					continue;
				}
				
				// 读取CFConfiguration 实现
				if(CFConfiguration.class.isAssignableFrom(clazz)) {
					configurations.add(CFConfiguration.class.cast(clazz.newInstance()));
					continue;
				}
			}
			// CFConfiguration 实现类调用
			for (CFConfiguration initializer : configurations) {
				initializer.onStartup(CFServletContext.getInstance());
			}
			// CFConfiguration 实现类调用
			for (CFService abstractItem : abstracts) {
				abstractItem.onStartup(CFServletContext.getInstance());
			}
			// CFHttpServlet 实现类调用
			for (CFHttpServlet servlets : httpServlets) {
				servlets.onStartup(CFServletContext.getInstance());
			}
		} catch (Throwable throwable) {
			CFLog.error("CFServletContainerInitializer.onStartup error. ", throwable);
		}
	}

}
