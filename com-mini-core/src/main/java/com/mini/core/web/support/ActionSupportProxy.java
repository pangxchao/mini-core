package com.mini.core.web.support;

import com.google.inject.Injector;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.validation.ValidationUtil;
import com.mini.core.web.annotation.Action;
import com.mini.core.web.argument.ArgumentResolver;
import com.mini.core.web.interceptor.ActionInterceptor;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.model.IModel;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.concurrent.CopyOnWriteArrayList;


public interface ActionSupportProxy {
	/**
	 * 获取Controller类Class对象
	 * @return Class对象
	 */
	@Nonnull
	Class<?> getClazz();
	
	/**
	 * 获取目标方法对象
	 * @return 目标方法对象
	 */
	@Nonnull
	Method getMethod();
	
	/**
	 * 获取数据模型实现类型
	 * @return 数据模型实现类型
	 */
	@Nonnull
	IModel<?> getModel();
	
	/**
	 * 获取控制器支持的方法
	 * @return 控制器支持的方法
	 */
	@Nonnull
	Action.Method[] getSupportMethod();
	
	/**
	 * 获取所有拦截器对象
	 * @return 拦截器对象
	 */
	@Nonnull
	CopyOnWriteArrayList<ActionInterceptor> getInterceptors();
	
	/**
	 * 获取目标方法的所有参数信息
	 * @return 所有参数信息
	 */
	@Nonnull
	MiniParameter[] getParameters();
	
	/**
	 * 获取参数处理器列表
	 * @return 参数处理器列表
	 */
	@Nonnull
	ParameterHandler[] getParameterHandlers();
	
	/**
	 * 获取 Action 视图路径
	 * @return Action 视图路径
	 */
	String getViewPath();
	
	/**
	 * 获取方法的请求路径
	 * @return 方法请求路径
	 */
	String getRequestUri();
	
	final class ParameterHandler implements EventListener {
		private final ArgumentResolver resolver;
		private final MiniParameter parameter;
		private final Injector injector;
		
		public ParameterHandler(@Nonnull Injector injector, @Nonnull ArgumentResolver resolver,
				@Nonnull MiniParameter parameter) {
			this.parameter = parameter;
			this.resolver = resolver;
			this.injector = injector;
		}
		
		public final Object getValue(ActionInvocation invocation) {
			var value = resolver.getValue(parameter, invocation);
			ValidationUtil.validate(injector, parameter, value);
			return value;
		}
	}
}
