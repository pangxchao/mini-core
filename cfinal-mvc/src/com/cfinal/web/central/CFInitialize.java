/**
 * Created the com.cfinal.web.central.CFInitialize.java
 * @created 2016年9月26日 下午4:33:34
 * @version 1.0.0
 */
package com.cfinal.web.central;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfinal.db.CFDBFactory;
import com.cfinal.util.cast.CFCastUtil;
import com.cfinal.util.lang.CFClazz;
import com.cfinal.util.logger.CFLogger;
import com.cfinal.util.param.CFBytecodeParanamer;
import com.cfinal.util.param.CFParanamer;
import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.annotaion.CFControl;
import com.cfinal.web.control.CFActionParameter;
import com.cfinal.web.control.CFActionPorxy;
import com.cfinal.web.editor.CFEditor;
import com.cfinal.web.editor.CFEditorBinding;
import com.cfinal.web.interceptor.CFInterceptor;
import com.cfinal.web.interceptor.CFInterceptorPorxy;
import com.cfinal.web.model.CFModel;
import com.cfinal.web.preprocessor.CFPreprocessor;
import com.cfinal.web.preprocessor.CFPreprocessorPorxy;
import com.cfinal.web.render.CFJsonRender;
import com.cfinal.web.render.CFPageRender;
import com.cfinal.web.render.CFStreamRender;
import com.cfinal.web.scheduler.CFTaskPorxy;
import com.cfinal.web.scheduler.CFTaskScheduler;
import com.cfinal.web.scheduler.thread.CFTask;
import com.cfinal.web.view.CFView;
import com.cfinal.web.view.CFViewPorxy;

/**
 * com.cfinal.web.central.CFInitialize.java
 * @author XChao
 */
public abstract class CFInitialize {
	private static final String PREPROCESSOR_CONFIG_NAME = "PREPROCESSOR_CLAZZ";
	private static final String PREPROCESSOR_METHOD_NAME = "process";
	private static final String NTERCEPTOR_METHOD_NAME = "intercept";
	private static final String SCHEDULER_METHOD_NAME = "execute";
	private static final String IVIEW_METHOD_NAME = "generator";
	private static CFContext context;

	/**
	 * 获取系统配置信息
	 * @return
	 */
	public static CFContext getContext() {
		return context;
	}

	/**
	 * 系统配置初始化方法
	 * @throws Exception
	 */
	public static CFContext init(ServletContext servletContext) throws Exception {
		// 读取配置文件
		CFLogger.info("********** Read config start. ************************************");
		InputStream stream = servletContext.getResourceAsStream("META-INF/cfinal-mvc.config");
		CFConfig config = new CFConfig(stream);
		CFLogger.info("********** Read config end. **************************************");

		// 创建全局 ApplicationContext 并初始化一些参数
		CFLogger.info("********** Init application start. *******************************");
		context = new CFContextImpl(servletContext);
		context.setAccessControl(config.getAccessControlAllowOrigin());
		context.setEncoding(config.getEncode());
		context.setActionSuffix(config.getActionSuffix());
		context.setLoginUrl(config.getLoginUrl());
		context.setWorkerId(config.getWorkerId());
		CFLogger.info("********** Init application end. *********************************");

		// 初始化 全局初始化参数
		CFLogger.info("********** Init init-paramter start. *****************************");
		JSONObject initParams = config.getInitParamter();
		if(initParams != null) {
			for (String name : initParams.keySet()) {
				context.addParameter(name, initParams.getString(name));
			}
		}
		CFLogger.info("********** Init init-paramter end. *******************************");

		// 初始化文件上传参数
		CFLogger.info("********** Init file-upload start. *******************************");
		JSONObject fileUpload = config.getFileUpload();
		if(fileUpload != null) {
			context.setFileUploadSizeThreshold(fileUpload.getIntValue("size-threshold"));
			context.setFileUploadRepository(fileUpload.getString("repository"));
			context.setFileUploadSizeMax(fileUpload.getLongValue("size-max"));
		}
		CFLogger.info("********** Init file-upload start. *******************************");

		// 初始化日志配置
		CFLogger.info("********** Init init-logging start. ******************************");
		JSONObject initLogging = config.getInitLogging();
		if(initLogging != null) {
			CFLogger.set(initLogging.getString("handlers"), initLogging.getString(".level"));
		}
		CFLogger.info("********** Init init-logging end. *********************************");

		// 读取数据处理器/视图渲染器
		CFLogger.info("********** Init Render Start. ************************************");
		CFInitialize.init_render();
		CFLogger.info("********** Init Render End. **************************************");

		// 页面视图渲染器配置
		CFLogger.info("********** Init View Handler Start. ******************************");
		CFInitialize.init_view(config.getHandlers());
		CFLogger.info("********** Init View Handler End. ********************************");

		// 分析并提取拦截器数据 和拦截器堆栈数据
		CFLogger.info("********** Init Interceptor Start. *******************************");
		CFInitialize.init_interceptor(config.getInterceptors());
		CFLogger.info("********** Init Interceptor End. *********************************");

		// 分析 并提取控制器, 丛包开始
		CFLogger.info("********** Init Action Start. ************************************");
		CFInitialize.init_packages(config.getPackages());
		CFLogger.info("********** Init Action End. **************************************");

		// 初始化数据库, 如果设置了使用数据库时 才初始化
		CFLogger.info("********** Init DataSource Start. ********************************");
		init_data_source(config.getDataSource());
		CFLogger.info("********** Init DataSource End. ***********************************");

		// 初始化预处理程序,并执行
		CFLogger.info("********** Init Preprocessor Start. ******************************");
		init_preprocessor(config.getInitClazz());
		CFLogger.info("********** Init Preprocessor End. ********************************");

		// 分析并提取定时任务信息,并启动定时任务
		CFLogger.info("********** Init Task Start. **************************************");
		init_task(config.getTasks());
		CFLogger.info("********** Init Task End. ****************************************");

		// 初始化方法参数名称
		CFLogger.info("********** Init Preprocessor Start. ******************************");
		init_controller_action_params();
		CFLogger.info("********** Init Preprocessor End. ********************************");

		return context;
	}

	private static void init_controller_action_params() {
		CFParanamer paranamer = new CFBytecodeParanamer();
		for (String action : context.getActionMap().keySet()) {
			CFActionPorxy porxy = context.getAction(action);
			Class<?>[] types = porxy.getMethod().getParameterTypes();
			String[] names = paranamer.lookupParameterNames(porxy.getMethod());
			if(names == null || names.length != types.length) {
				throw new RuntimeException("Read thd action parameters fail. ");
			}
			CFActionParameter parameter = new CFActionParameter();
			for (int i = 0, j = types.length; i < j; i++) {
				CFEditor editor = CFEditorBinding.getInstence().getEditor(types[i]);
				parameter.addParameter(names[i], types[i], editor, null);
			}
			porxy.setParameter(parameter);
		}
	}

	private static void init_preprocessor(String initclazz) throws Exception {
		if(StringUtils.isBlank(initclazz)) {
			return;
		}
		Object instence = CFClazz.newInstence(initclazz, CFPreprocessor.class);
		Method method = instence.getClass().getMethod(PREPROCESSOR_METHOD_NAME);
		CFPreprocessorPorxy preprocessorPorxy = new CFPreprocessorPorxy();
		preprocessorPorxy.setName(PREPROCESSOR_CONFIG_NAME);
		preprocessorPorxy.setInstence(instence);
		preprocessorPorxy.setMethod(method);
		context.setPreprocessorPorxy(preprocessorPorxy);
		preprocessorPorxy.execute();
	}

	/**
	 * 初始化定时任务
	 * @param tasks
	 * @throws Exception
	 */
	private static void init_task(JSONObject tasks) throws Exception {
		if(tasks == null || tasks.isEmpty()) {
			return;
		}
		// 初始化定时任务线程池大小,并创建线程池实例
		CFTaskScheduler.initTask(tasks.size());
		for (String name : tasks.keySet()) {
			JSONObject task = tasks.getJSONObject(name);
			CFTask instence = CFClazz.newInstence(task.getString("clazz"), CFTask.class);
			Method method = instence.getClass().getMethod(SCHEDULER_METHOD_NAME);
			CFTaskPorxy taskPorxy = new CFTaskPorxy();
			taskPorxy.setName(name);
			taskPorxy.setInstence(instence);
			taskPorxy.setMethod(method);
			taskPorxy.setRule(task.getString("rule"));
			context.addTask(name, taskPorxy);
			CFTaskScheduler.addTask(taskPorxy);
		}
	}

	/**
	 * 初始化数据库
	 * @param dataSource
	 * @throws Exception
	 */
	private static void init_data_source(JSONObject dataSources) throws Exception {
		if(dataSources == null || dataSources.size() == 0) {
			return;
		}
		for (String name : dataSources.keySet()) {
			JSONObject dataSource = dataSources.getJSONObject(name);
			String resourseClazz = dataSource.getString("clazz");
			if(StringUtils.isBlank(resourseClazz)) {
				throw new RuntimeException("The clazz  of the data-source cannot be empty. ");
			}
			// 删除属性中的 clazz 属性， 否则在设置连接池时会报 clazz 属性找不到
			dataSource.remove("clazz");
			Object instence = Class.forName(resourseClazz).newInstance();
			for (String property : dataSource.keySet()) {
				Field field = instence.getClass().getDeclaredField(property);
				if(field == null) {
					throw new RuntimeException("The " + resourseClazz + " " + property + " does not exist. ");
				}
				field.setAccessible(true);
				field.set(instence, CFCastUtil.cast(dataSource.getString(property), field.getType()));
			}
			CFDBFactory.addDataSource(name, (DataSource) instence);
		}
	}

	/**
	 * 提取包配置信息
	 * @param packages
	 * @throws Exception
	 */
	private static void init_packages(JSONObject packages) throws Exception {
		if(packages == null || packages.size() == 0) {
			return;
		}
		// 获取全局配置中的 http url 的后缀内容
		String actionSuffix = context.getActionSuffix();
		for (String packageName : packages.keySet()) {
			if(StringUtils.isBlank(packageName)) {
				throw new RuntimeException("The key of the packages cannot be empty. ");
			}
			JSONObject packageItem = packages.getJSONObject(packageName);
			if(packageItem == null || packageItem.size() == 0) {
				throw new RuntimeException("The value of the packages cannot be empty. ");
			}
			// 获取packageItem 配置中的 action-suffix属性
			String packageSuffix = packageItem.getString("action-suffix");
			// 如果 packageSuffix 为空，则让packageSuffix = suffix
			packageSuffix = StringUtils.defaultIfEmpty(packageSuffix, actionSuffix);
			// 获取 packageItem 配置中的 url 属性
			String packageUrl = packageItem.getString("url");
			// 如果 packageItem 的url配置为空， 则让其等于 packageName 值
			packageUrl = StringUtils.defaultIfEmpty(packageUrl, packageName);
			// 获取 packageItem 配置中 的 use-in 属性
			String packageUseIn = packageItem.getString("use-in");
			// 获取 packageItem 配置中的 view 属性
			String packageView = packageItem.getString("view");
			// 扫描该包下面的所有控制器
			String roleIn = packageItem.getString("role-in");
			for (Class<?> clazz : CFClazz.scanningall(roleIn, true, CFControl.class)) {
				CFLogger.info("Scanner Control --------------------- " + clazz.getName());

				CFControl control = clazz.getAnnotation(CFControl.class);
				// 获取clazz Control 注解的 suffix 属性值，如果为空，则默认为 packageSuffix 内容
				String controlSuffix = StringUtils.defaultIfEmpty(control.suffix(), packageSuffix);
				// 获取clazz Control 注解的 name 属性值， 如果为空，则默认为 packageSuffix 内容
				String controlName = StringUtils.defaultIfEmpty(control.name(), clazz.getSimpleName());
				// 获取clazz Control 注解的url 属性值， 如果为空 ， 则默认为 controlName 内容
				String controlUrl = StringUtils.defaultIfEmpty(control.url(), controlName);
				// 获取clazz Control 注解的 view 属性值， 如果为空，则默认为 packageView内容
				String controlView = StringUtils.defaultIfEmpty(control.view(), packageView);
				// 获取 clazz Control 注解的 use 属性值， 如果为空，则默认为 packageUseIn 内容
				String controlUse = StringUtils.defaultIfEmpty(control.use(), packageUseIn);

				// 创建当前控制器的实例对象
				Object controlInstence = clazz.newInstance();
				for (Method method : clazz.getMethods()) {
					// 获取方法上的 Action 注解
					CFAction action = method.getAnnotation(CFAction.class);
					// 如果该方法没有Action类的注解 表示不为控制器, 不作处理
					if(action == null) {
						continue;
					}
					// 获取 Action 方法的 url 注解信息， 如果为空， 则默认为 controlUrl 内容
					String url = StringUtils.defaultIfEmpty(action.url(), method.getName());
					// 获取 Action 方法的 suffix 注解信息， 如果为空， 则默认为 controlSuffix 内容
					String suffix = StringUtils.defaultIfEmpty(action.suffix(), controlSuffix);
					// 获取 Action 方法的 use 注解信息， 如果为空， 则默认为 controlUse 内容
					String use = StringUtils.defaultIfEmpty(action.use(), controlUse);
					// 获取 Action 方法的 view 注解信息， 如果为空， 则默认为 controlView 内容
					String view = StringUtils.defaultIfEmpty(action.view(), controlView);

					// 创建 Action 代理类
					CFActionPorxy actionPorxy = new CFActionPorxy();

					// 设置Action 方法名称， Action代理类名称
					StringBuilder actionName = new StringBuilder(context.getContextPath()).append("/");
					actionName.append(packageUrl).append("/").append(controlUrl).append("/").append(url);
					actionPorxy.setName(actionName.append(suffix).toString().replaceAll("[/]+", "/"));
					// 如果该控制器连接存在时,则抛出异常
					if(context.getAction(actionPorxy.getName()) != null) {
						throw new RuntimeException("The request url is exists: " + actionPorxy.getName());
					}

					CFViewPorxy viewPorxy = context.getView(view);
					if((viewPorxy = context.getView(context.getDefaultView())) == null) {
						throw new RuntimeException("The Action view  not exists:" + actionPorxy.getName());
					}
					// 设置Action的视图代理
					actionPorxy.setViewPorxy(viewPorxy);

					// 设置Action 方法所对应的默认渲染页面的绝对路径FullPath
					StringBuilder actionViewPath = new StringBuilder("pages").append("/");
					actionViewPath.append(packageName).append("/").append(controlName).append("/");
					actionViewPath.append(method.getName()).append(viewPorxy.getPageSuffix());
					actionPorxy.setDefaultViewPath(actionViewPath.toString().replaceAll("[/]+", "/"));

					// 设置Action 注解信息 和 Control 注解信息
					actionPorxy.setAction(action);
					actionPorxy.setControl(control);
					// 设置类的实体属性 方法对象
					actionPorxy.setInstence(controlInstence);
					actionPorxy.setMethod(method);
					// 设置 Action的 render
					action.value().setRender(actionPorxy, context);
					// 处理拦截器堆栈
					List<String> interceptorStack = context.getInterceptorStack(use);
					if(interceptorStack != null) {
						for (String name : interceptorStack) {
							actionPorxy.addInterceptorPorxy(context.getInterceptor(name));
						}
					}
					// 添加 action 到 application 中
					context.addAction(actionPorxy.getName(), actionPorxy);
					CFLogger.info("Scanner Action name ============================== " + actionPorxy.getName());
				}
			}
		}
	}

	/**
	 * 读取拦截器信息, 包括初始化登录验证拦截器和参数绑定拦截器
	 * @param interceptor
	 * @throws Exception
	 */
	private static void init_interceptor(JSONObject interceptors) throws Exception {
		if(interceptors == null || interceptors.size() == 0) {
			return;
		}
		JSONObject interceptor = interceptors.getJSONObject("interceptor");
		if(interceptor == null || interceptor.size() == 0) {
			return;
		}
		JSONObject interceptorStack = interceptors.getJSONObject("interceptor-stack");
		if(interceptorStack == null || interceptorStack.size() == 0) {
			return;
		}
		// 读取拦截器信息
		for (String name : interceptor.keySet()) {
			String clazzStr = interceptor.getString(name);
			CFInterceptor instence = CFClazz.newInstence(clazzStr, CFInterceptor.class);
			Method method = instence.getClass().getMethod(NTERCEPTOR_METHOD_NAME, CFInvocation.class);
			CFInterceptorPorxy interceptorPorxy = new CFInterceptorPorxy();
			interceptorPorxy.setName(name);
			interceptorPorxy.setInstence(instence);
			interceptorPorxy.setMethod(method);
			context.addInterceptor(name, interceptorPorxy);
		}
		// 读取拦截器堆栈信息
		for (String name : interceptorStack.keySet()) {
			JSONArray arrays = interceptorStack.getJSONArray(name);
			List<String> interceptorStackValue = new ArrayList<>();
			for (int i = 0; i < arrays.size(); i++) {
				interceptorStackValue.add(arrays.getString(i));
			}
			context.addInterceptorStack(name, interceptorStackValue);
		}
	}

	/**
	 * 分析视图渲染器
	 * @param handler
	 */
	private static void init_view(JSONObject handler) throws Exception {
		if(handler == null || handler.size() == 0) {
			handler = new JSONObject();
			JSONObject object = new JSONObject();
			object.put("default", true);
			object.put("page-suffix", ".jsp");
			object.put("clazz", "com.xcc.web.view.JspView");
			handler.put("jsp", object);
			handler.put("default", object);
		}
		for (String name : handler.keySet()) {
			JSONObject handlerView = handler.getJSONObject(name);
			CFView instence = CFClazz.newInstence(handlerView.getString("clazz"), CFView.class);
			Method method = instence.getClass().getMethod(IVIEW_METHOD_NAME, CFModel.class, String.class,
				CFRequest.class, CFResponse.class);
			CFViewPorxy viewPorxy = new CFViewPorxy();
			viewPorxy.setName(name);
			viewPorxy.setInstence(instence);
			viewPorxy.setMethod(method);
			viewPorxy.setPageSuffix(handlerView.getString("page-suffix"));
			context.addView(name, viewPorxy);
			if(handlerView.getBooleanValue("default") == true) {
				// 设置应用程序的默认视图渲染器
				context.setDefaultView(name);
			}
		}
		if(StringUtils.isBlank(context.getDefaultView())) {
			throw new RuntimeException("The default view of the handlers cannot be empty. ");
		}
	}

	/**
	 * 初始化处理器
	 */
	private static void init_render() {
		context.addRender(CFPageRender.class, new CFPageRender());
		context.addRender(CFJsonRender.class, new CFJsonRender());
		context.addRender(CFStreamRender.class, new CFStreamRender());
	}
}
