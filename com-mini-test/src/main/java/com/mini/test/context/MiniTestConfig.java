package com.mini.test.context;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.google.inject.Binder;
import com.mini.core.inject.annotation.ComponentScan;
import com.mini.core.web.servlet.DispatcherHttpServlet;
import com.mini.core.web.support.WebApplicationInitializer;
import com.mini.core.web.support.config.Configures;
import com.mini.test.R;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;

@Singleton
@ComponentScan("com.mini.test")
public class MiniTestConfig extends WebApplicationInitializer {
	@Inject
	@Named("LOCATION_PATH")
	private String locationPath;
	
	@Override
	protected final void onStartupBinding(Binder binder) {
		binder.requestStaticInjection(FileGenerator.class);
		binder.requestStaticInjection(R.class);
	}
	
	@Override
	public void onStartupRegister(ServletContext context, Configures configure) {
		configure.addServlet(DispatcherHttpServlet.class, register -> {
			register.addUrlPatterns("/2020/*", "/2021/*");
			register.setMultipartEnabled(true);
			register.setAsyncSupported(true);
		});
		// 全局FastJson 设置
		SerializeConfig serializeConfig = SerializeConfig.globalInstance;
		serializeConfig.put(Long.class, ToStringSerializer.instance);
		serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
		// 设置文件上传临时目录
		configure.setLocationPath(locationPath);
	}
}
