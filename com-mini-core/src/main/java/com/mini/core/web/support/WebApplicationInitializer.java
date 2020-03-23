package com.mini.core.web.support;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.mini.core.inject.MiniModule;
import com.mini.core.inject.annotation.ComponentScan;
import com.mini.core.web.support.config.Configures;

import javax.inject.Singleton;
import javax.servlet.ServletContext;

@Singleton
@ComponentScan
public abstract class WebApplicationInitializer extends MiniModule implements Module {
	
	@Override
	protected final void onStartup(Binder binder) {
		onStartupBinding(binder);
	}
	
	/**
	 * 该方法只难做依赖绑定相关操作
	 * @param binder 绑定器
	 */
	protected void onStartupBinding(Binder binder) {
	}
	
	/**
	 * 该方法在自动注入之后调用，使用时需要注意顺序
	 * @param context   ServletContext 对象
	 * @param configure 配置信息
	 */
	public void onStartupRegister(ServletContext context, Configures configure) {
	}
}
