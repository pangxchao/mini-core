package com.mini.core.web.support;

import com.google.auto.service.AutoService;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mini.core.inject.MiniModule;
import com.mini.core.inject.annotation.ComponentScan;
import com.mini.core.util.Assert;
import com.mini.core.util.ClassUtil;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Action;
import com.mini.core.web.annotation.Before;
import com.mini.core.web.annotation.Clear;
import com.mini.core.web.annotation.Controller;
import com.mini.core.web.argument.ArgumentResolverContext;
import com.mini.core.web.argument.ArgumentResolverModel;
import com.mini.core.web.argument.ArgumentResolverSession;
import com.mini.core.web.argument.defaults.*;
import com.mini.core.web.argument.header.*;
import com.mini.core.web.argument.param.*;
import com.mini.core.web.argument.uri.*;
import com.mini.core.web.filter.AccessControlAllowOriginFilter;
import com.mini.core.web.filter.CharacterEncodingFilter;
import com.mini.core.web.handler.ExceptionHandlerDefault;
import com.mini.core.web.handler.ExceptionHandlerValidate;
import com.mini.core.web.interceptor.ActionInterceptor;
import com.mini.core.web.model.IModel;
import com.mini.core.web.servlet.DispatcherHttpServlet;
import com.mini.core.web.support.config.Configures;
import com.mini.core.web.view.JspPageViewResolver;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mini.core.util.FileUtil.getFileExt;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

@Singleton
@HandlesTypes(WebApplicationInitializer.class)
@AutoService(ServletContainerInitializer.class)
public final class MiniApplicationInitializer implements ServletContainerInitializer {
	private static final Configures CONFIGURES = new Configures();
	private static final String SEP = "/";
	
	// 反射获取所有配置类的实例
	private List<WebApplicationInitializer> getConfigureList(Set<Class<?>> initializer) {
		return ofNullable(initializer).stream().flatMap(Collection::stream).filter( //
				WebApplicationInitializer.class::isAssignableFrom).map(clazz -> {
			try {
				Constructor<?> constructor = clazz.getConstructor();
				Object instance = constructor.newInstance();
				return (WebApplicationInitializer) instance;
			} catch (ReflectiveOperationException ex) {
				throw new RuntimeException(ex);
			}
		}).collect(Collectors.toList());
	}
	
	@Override
	public final void onStartup(Set<Class<?>> initializer, ServletContext context) {
		// 获取所有配置信息
		List<WebApplicationInitializer> configList = getConfigureList(initializer);
		Assert.isTrue(!configList.isEmpty(), "WebMvcConfigure can not be empty.");
		// 初始化绑定
		Injector injector = Guice.createInjector(new MiniModule() {
			protected final void onStartup(Binder binder) throws Error {
				// 绑定 Configures 对象和 ServletContext 对象
				binder.bind(ServletContext.class).toInstance(context);
				binder.bind(Configures.class).toInstance(CONFIGURES);
				// 绑定初始化参数
				var names = context.getInitParameterNames();
				names.asIterator().forEachRemaining(name -> { //
					var val = context.getInitParameter(name);
					this.bind(binder, name, val);
				});
				// 绑定配置信息
				for (WebApplicationInitializer config : configList) {
					binder.requestInjection(config);
					binder.install(config);
				}
			}
		});
		// 配置默认视图实现类
		CONFIGURES.setPageViewResolver(JspPageViewResolver.class);
		// 注册默认 HttpServlet
		CONFIGURES.addServlet(DispatcherHttpServlet.class, registration -> {
			registration.addUrlPatterns(CONFIGURES.getDefaultMapping());
			registration.setName("DispatcherHttpServlet");
		});
		// 编码统一管理过虑器
		CONFIGURES.addFilter(CharacterEncodingFilter.class, registration -> {
			registration.setName("CharacterEncodingFilter");
			registration.addUrlPatterns("/*");
		});
		// 跨域请求过虑器
		CONFIGURES.addFilter(AccessControlAllowOriginFilter.class, registration -> {
			registration.setName("AccessControlAllowOriginFilter");
			registration.addUrlPatterns("/*");
		});
		// 验证异常处理器/其它普通异常处理器
		CONFIGURES.addExceptionHandler(ExceptionHandlerValidate.class);
		CONFIGURES.addExceptionHandler(ExceptionHandlerDefault.class);
		// Servlet容器、数据模型渲染、登录Session相关参数
		CONFIGURES.addArgumentResolver(ArgumentResolverContext.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverModel.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverSession.class);
		// 默认方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
		CONFIGURES.addArgumentResolver(ArgumentResolverBasicDefault.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverArrayDefault.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverBeanDefault.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverMapDefault.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverPartDefault.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverPartDefaultArray.class);
		// Request Param 方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
		CONFIGURES.addArgumentResolver(ArgumentResolverBasicRequestParam.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverArrayRequestParam.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverBeanRequestParam.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverPartRequestParam.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverPartArrayRequestParam.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverMapRequestParam.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverMapRequestParamArray.class);
		// Request Uri 方式： 支持一般基础数据、一般基础数组、Map 类型的参数
		CONFIGURES.addArgumentResolver(ArgumentResolverBasicRequestUri.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverArrayRequestUri.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverBeanRequestUri.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverMapRequestUri.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverMapRequestUriArray.class);
		// Request Header 方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
		CONFIGURES.addArgumentResolver(ArgumentResolverBasicRequestHeader.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverArrayRequestHeader.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverBeanRequestHeader.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverMapRequestHeader.class);
		CONFIGURES.addArgumentResolver(ArgumentResolverMapRequestHeaderArray.class);
		// 初始化项目自定义配置信息
		for (WebApplicationInitializer config : configList) {
			config.onStartupRegister(context, CONFIGURES);
			registerActionProxy(injector, config);
		}
		// 绑定 Servlet、Filter、listener
		CONFIGURES.getServlets().forEach(servlet -> servlet.register(context));
		CONFIGURES.getFilters().forEach(filter -> filter.register(context));
		CONFIGURES.getListeners().forEach(context::addListener);
	}
	
	// 注册默认的 ActionInvocationProxy
	private void registerActionProxy(Injector injector, WebApplicationInitializer config) {
		// 获取需要扫描的所有包
		Stream.concat(of(config.getClass().getPackageName()), ofNullable(config.getClass()
				.getAnnotation(ComponentScan.class))
				.map(ComponentScan::value)
				.stream()
				.flatMap(Stream::of))
				.map(name -> ClassUtil.scanner(name, Controller.class))
				.flatMap(Collection::stream).distinct().forEach(clazz -> {
			// 获取类上的注解信息
			Controller controller = clazz.getAnnotation(Controller.class);
			requireNonNull(controller, "@Controller can not be null");
			// 获取类上的拦截器信息
			Clear controllerClear = clazz.getAnnotation(Clear.class);
			Before controllerBefore = clazz.getAnnotation(Before.class);
			// 查找当前类下的所有公开方法并处理
			Arrays.stream(clazz.getMethods()).forEach(method -> {
				// 获取方法上的Action注解信息
				Action action = method.getAnnotation(Action.class);
				if (action == null) return;
				// 获取方法上的拦截器信息
				Clear methodClear = method.getAnnotation(Clear.class);
				Before methodBefore = method.getAnnotation(Before.class);
				// 视图文件路径
				String path = getViewPath(clazz, controller, method, action);
				// 获取方法参数信息
				MiniParameter[] parameters = ClassUtil.getParameterByAsm(method);
				// 获取 请求 Action 的路径 并 注册Action
				getRequestUriList(clazz, controller, method, action).stream().distinct().forEach(requestUri -> {
					// 根据扫描出来的 Action 对象创建 ActionProxy 并添加到配置信息中
					CONFIGURES.addActionProxy(requestUri, new ActionSupportProxy() {
						private CopyOnWriteArrayList<ActionInterceptor> interceptors;
						private ParameterHandler[] handlers;
						
						@Nonnull
						@Override
						public final Class<?> getClazz() {
							return clazz;
						}
						
						@Nonnull
						@Override
						public final Method getMethod() {
							return method;
						}
						
						@Nonnull
						@Override
						public final IModel<?> getModel() {
							return ofNullable(injector.getInstance(action.value()))
									.map(v -> v.setViewPath(getViewPath()))
									.orElseThrow();
						}
						
						@Nonnull
						@Override
						public final Action.Method[] getSupportMethod() {
							return action.method();
						}
						
						@Nonnull
						@Override
						public final CopyOnWriteArrayList<ActionInterceptor> getInterceptors() {
							return ofNullable(interceptors).orElseGet(() -> {
								synchronized(this) {
									// 创建拦截器列表实例
									interceptors = new CopyOnWriteArrayList<>();
									// 将方法上的拦截器添加到实例列表中
									if (methodBefore != null && methodBefore.value().length > 0) {
										interceptors.addAll(of(methodBefore.value())
												.map(injector::getInstance)
												.collect(Collectors.toList()));
									}
									// 方法上有清除注解时直接返回
									if (methodClear != null) {
										return interceptors;
									}
									// 将类上的注解添加到拦截器实例列表之前
									if (controllerBefore != null && controllerBefore.value().length > 0) {
										interceptors.addAll(0, of(controllerBefore.value())
												.map(injector::getInstance)
												.collect(Collectors.toList()));
									}
									if (controllerClear != null) {
										return interceptors;
									}
									// 添加全局拦截器到拦截器实例列表
									interceptors.addAll(0, CONFIGURES.getInterceptorList());
									return interceptors;
								}
							});
						}
						
						@Nonnull
						@Override
						public final MiniParameter[] getParameters() {
							return parameters;
						}
						
						@Nonnull
						@Override
						public final ParameterHandler[] getParameterHandlers() {
							return ofNullable(handlers).orElseGet(this::getHandlers);
						}
						
						private synchronized ParameterHandler[] getHandlers() {
							handlers = Stream.of(getParameters()).map(param -> CONFIGURES.getArgumentResolverSet()
									.stream().filter(r -> r.supportParameter(param)).findAny()
									.map(r -> new ParameterHandler(injector, r, param))
									.orElseThrow(() -> new NullPointerException("Unsupported parameter:" + param)))
									.toArray(ParameterHandler[]::new);
							return handlers;
						}
						
						@Override
						public final String getViewPath() {
							return path;
						}
						
						@Override
						public final String getRequestUri() {
							return requestUri;
						}
					});
				});
			});
		});
	}
	
	@Nonnull
	private String getViewPath(Class<?> clazz, Controller controller, Method method, Action action) {
		// 处理文件路径
		String typePath = controller.path();
		if (StringUtils.isBlank(typePath)) {
			typePath = clazz.getSimpleName();
		}
		// 去掉类上的路径两边的空格和 “/”
		typePath = StringUtils.strip(typePath);
		typePath = StringUtils.strip(typePath, SEP);
		// typePath 不能为空
		Objects.requireNonNull(typePath);
		
		String methodPath = action.path();
		if (StringUtils.isBlank(methodPath)) {
			methodPath = method.getName();
		}
		
		// 去掉方法上的路径和两边的空格
		methodPath = StringUtils.strip(methodPath);
		methodPath = StringUtils.strip(methodPath, SEP);
		
		// typePath 不能为空
		Assert.notBlank(methodPath);
		
		// 获取完整的视图路径
		return StringUtils.strip(typePath + SEP //
				+ methodPath, SEP);
	}
	
	@Nonnull
	private List<String> getRequestUriList(Class<?> clazz, Controller controller, Method method, Action action) {
		String typeUrl = controller.url();
		if (StringUtils.isBlank(typeUrl)) {
			typeUrl = clazz.getSimpleName();
		}
		// 去掉类URL上的空格 “/”
		typeUrl = StringUtils.strip(typeUrl);
		typeUrl = StringUtils.strip(typeUrl, SEP);
		
		// 所有URL
		List<String> urlList = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(action.url())) {
			for (String methodUrl : action.url()) {
				methodUrl = StringUtils.strip(methodUrl);
				methodUrl = StringUtils.strip(methodUrl, SEP);
				
				// 组装成完整的URL
				String url = typeUrl + SEP + methodUrl;
				url = StringUtils.strip(url, SEP);
				// 如果URL上没有配置后缀，添加默认后缀
				if (StringUtils.isBlank(getFileExt(url))) {
					url = url + action.suffix();
				}
				// 添加路径到列表
				urlList.add(url);
			}
		} else if (!method.getName().isBlank()) {
			// 处理方法路径
			String methodUrl = method.getName();
			methodUrl = methodUrl + action.suffix();
			urlList.add(typeUrl + SEP + methodUrl);
		}
		return urlList;
	}
}
