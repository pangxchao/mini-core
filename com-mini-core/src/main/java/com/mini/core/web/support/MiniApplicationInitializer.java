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
import com.mini.core.web.argument.paging.ArgumentResolverPagingDefault;
import com.mini.core.web.argument.paging.ArgumentResolverPagingRequestParam;
import com.mini.core.web.argument.request.param.*;
import com.mini.core.web.argument.request.uri.ArgumentResolverArrayRequestUri;
import com.mini.core.web.argument.request.uri.ArgumentResolverBasicRequestUri;
import com.mini.core.web.argument.request.uri.ArgumentResolverMapRequestUri;
import com.mini.core.web.filter.AccessControlAllowOriginFilter;
import com.mini.core.web.filter.CacheControlFilter;
import com.mini.core.web.filter.CharacterEncodingFilter;
import com.mini.core.web.handler.ExceptionHandlerDefault;
import com.mini.core.web.handler.ExceptionHandlerValidate;
import com.mini.core.web.interceptor.ActionInterceptor;
import com.mini.core.web.model.IModel;
import com.mini.core.web.servlet.DispatcherHttpServlet;
import com.mini.core.web.support.config.Configures;
import com.mini.core.web.view.PageViewResolver;
import com.mini.core.web.view.PageViewResolverFreemarker;
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
	private static final String SEP = "/";

	// 反射获取所有配置类的实例
	private List<WebApplicationInitializer> getWebMvcConfigureList(Set<Class<?>> initializer) {
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
		List<WebApplicationInitializer> configs = getWebMvcConfigureList(initializer);
		Assert.isTrue(!configs.isEmpty(), "WebMvcConfigure can not be empty.");
		// 初始化绑定
		Injector injector = Guice.createInjector(Stream.concat(of(new MiniModule() {
			protected synchronized void onStartup(Binder binder) throws Error {
				// 绑定初始化
				binder.bind(ServletContext.class).toInstance(context);
				Enumeration<String> names = context.getInitParameterNames();
				names.asIterator().forEachRemaining(name -> { //
					String value = context.getInitParameter(name);
					bind(binder, name, value);
				});
				// 绑定配置信息
				for (WebApplicationInitializer config : configs) {
					binder.requestInjection(config);
					binder.install(config);
				}
			}
		}), configs.stream()).collect(Collectors.toList()));

		// 获取配置信息
		Configures configures = injector.getInstance(Configures.class);
		requireNonNull(configures, "Configure can not be null.");

		// 系统配置注册
		this.onStartupRegisterSystemDefault(configures);
		for (WebApplicationInitializer config : configs) {
			config.onStartupRegister(context, configures);
			registerActionProxy(injector, configures, config);
		}

		// 绑定 Servlet、Filter、listener
		configures.getServlets().forEach(servlet -> servlet.register(context));
		configures.getFilters().forEach(filter -> filter.register(context));
		configures.getListeners().forEach(context::addListener);
	}

	private void onStartupRegisterSystemDefault(Configures configure) {
		// 配置默认视图实现类
		configure.setPageViewResolver(PageViewResolverFreemarker.class);

		// 注册默认 HttpServlet
		configure.addServlet(DispatcherHttpServlet.class, registration -> {
			registration.addUrlPatterns(configure.getDefaultMapping());
			registration.setName("DispatcherHttpServlet");
		});

		// 编码统一管理过虑器
		configure.addFilter(CharacterEncodingFilter.class, registration -> {
			registration.setName("CharacterEncodingFilter");
			registration.addUrlPatterns("/*");
		});

		// 跨域请求过虑器
		configure.addFilter(AccessControlAllowOriginFilter.class, registration -> {
			registration.setName("AccessControlAllowOriginFilter");
			registration.addUrlPatterns("/*");
		});

		// 静态资源缓存过虑器
		configure.addFilter(CacheControlFilter.class, registration -> {
			registration.setName("CacheControlFilter");
			registration.addUrlPatterns("/*");
		});

		// 验证异常处理器/其它普通异常处理器
		configure.addExceptionHandler(ExceptionHandlerValidate.class);
		configure.addExceptionHandler(ExceptionHandlerDefault.class);

		// Servlet容器、数据模型渲染、登录Session相关参数
		configure.addArgumentResolver(ArgumentResolverContext.class);
		configure.addArgumentResolver(ArgumentResolverModel.class);
		configure.addArgumentResolver(ArgumentResolverSession.class);

		// 默认方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
		configure.addArgumentResolver(ArgumentResolverBasicDefault.class);
		configure.addArgumentResolver(ArgumentResolverArrayDefault.class);
		configure.addArgumentResolver(ArgumentResolverMapDefault.class);
		configure.addArgumentResolver(ArgumentResolverPartDefault.class);
		configure.addArgumentResolver(ArgumentResolverPartDefaultArray.class);

		// Request Param 方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
		configure.addArgumentResolver(ArgumentResolverBasicRequestParam.class);
		configure.addArgumentResolver(ArgumentResolverArrayRequestParam.class);
		configure.addArgumentResolver(ArgumentResolverPartRequestParam.class);
		configure.addArgumentResolver(ArgumentResolverPartArrayRequestParam.class);
		configure.addArgumentResolver(ArgumentResolverMapRequestParam.class);
		configure.addArgumentResolver(ArgumentResolverMapRequestParamArray.class);

		// Request Uri 方式： 支持一般基础数据、一般基础数组、Map 类型的参数
		configure.addArgumentResolver(ArgumentResolverBasicRequestUri.class);
		configure.addArgumentResolver(ArgumentResolverArrayRequestUri.class);
		configure.addArgumentResolver(ArgumentResolverMapRequestUri.class);

		// 分页器参数解析
		configure.addArgumentResolver(ArgumentResolverPagingRequestParam.class);
		configure.addArgumentResolver(ArgumentResolverPagingDefault.class);
	}

	// 注册默认的 ActionInvocationProxy
	private void registerActionProxy(Injector injector, Configures configure, //
		WebApplicationInitializer config) {
		// 获取需要扫描的所有包
		Stream.concat(of(config.getClass().getPackageName()),
			Optional.ofNullable(config.getClass()
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
				getRequestUriList(clazz, controller, method, action).stream() //
					.distinct().forEach(requestUri -> {  //
					configure.addActionProxy(requestUri, new ActionSupportProxy() {
						private List<ActionInterceptor> interceptors;
						private ParameterHandler[] handlers;

						@Nonnull
						@Override
						public Class<?> getClazz() {
							return clazz;
						}

						@Nonnull
						@Override
						public Method getMethod() {
							return method;
						}

						@Nonnull
						@Override
						public IModel<?> getModel(PageViewResolver resolver) {
							return action.value().getModel(resolver, getViewPath());
						}

						@Nonnull
						@Override
						public Action.Method[] getSupportMethod() {
							return action.method();
						}

						@Nonnull
						@Override
						public List<ActionInterceptor> getInterceptors() {
							return Optional.ofNullable(interceptors).orElseGet(() -> {
								synchronized (this) {
									// 创建拦截器列表实例
									interceptors = new ArrayList<>();
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
									interceptors.addAll(0, configure.getInterceptors());
									return interceptors;
								}
							});
						}

						@Nonnull
						@Override
						public MiniParameter[] getParameters() {
							return parameters;
						}

						@Nonnull
						@Override
						public ParameterHandler[] getParameterHandlers() {
							return Optional.ofNullable(handlers).orElseGet(() -> {
								synchronized (this) {
									handlers = of(getParameters()).map(param ->
										configure.getArgumentResolvers().stream()
											.filter(r -> r.supportParameter(param))
											.findAny()
											.map(r -> new ParameterHandler(r, param))
											.orElseThrow(() -> new NullPointerException("Unsupported parameter type: " + param)))
										.toArray(ParameterHandler[]::new);
									return handlers;
								}
							});
						}

						@Override
						public String getViewPath() {
							return path;
						}

						@Override
						public String getRequestUri() {
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
		Assert.notBlank(typePath);

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
		return typePath + SEP + methodPath;
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
