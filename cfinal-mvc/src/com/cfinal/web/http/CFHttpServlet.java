/**
 * Created the com.cfinal.web.core.CFHttpServlet.java
 * @created 2017年8月20日 下午11:32:48
 * @version 1.0.0
 */
package com.cfinal.web.http;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.cfinal.db.CFDBFactory;
import com.cfinal.util.lang.CFString;
import com.cfinal.util.lang.CFThrowable;
import com.cfinal.util.logger.CFLog;
import com.cfinal.util.param.CFBytecodeParanamer;
import com.cfinal.util.param.CFParanamer;
import com.cfinal.web.CFService;
import com.cfinal.web.CFServletContext;
import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.annotaion.CFControl;
import com.cfinal.web.annotaion.CFType;
import com.cfinal.web.editor.CFEditor;
import com.cfinal.web.editor.CFEditorBinding;
import com.cfinal.web.editor.CFParamBindException;
import com.cfinal.web.http.interceptor.CFInterceptor;
import com.cfinal.web.model.CFModel;
import com.cfinal.web.model.CFModelImpl;
import com.cfinal.web.model.CFUser;

/**
 * com.cfinal.web.core.CFHttpServlet.java
 * @author XChao
 */
public abstract class CFHttpServlet extends HttpServlet implements CFService {
	private static final long serialVersionUID = 1L;
	private CFServletContext context;
	private final List<String> SERVLET_NAME_ALL_LIST = new ArrayList<>();
	
	

	/**
	 * 注册 Action
	 * @param context
	 */
	public final void onStartup(CFServletContext context) throws Exception {
		this.context = context; // 设置当前类context的值
		// 获取控制器的 CFControl 注解信息
		CFControl control = this.getClass().getAnnotation(CFControl.class);
		if(control != null) { // 如果control注解信息不为空时，Servlet 信息
			CFLog.debug("Scanner Control : " + this.getClass().getName());

			String conSuf = StringUtils.defaultIfEmpty(control.suffix(), context.getActionSuffix());
			String conName = StringUtils.defaultIfEmpty(control.name(), this.getClass().getSimpleName());
			String conUrl = StringUtils.defaultIfEmpty(control.url(), this.getClass().getSimpleName());
			List<Class<? extends CFInterceptor>> interceptors = context.getInterceptor(control.interceptor());

			Dynamic register = context.addServlet(conName, this);
			for (Method method : this.getClass().getMethods()) { // 遍历Action
				CFAction action = method.getAnnotation(CFAction.class);
				if(action != null) {
					CFActionServlet servlet = new CFActionServlet(context, this, conSuf, //
						conName, conUrl, interceptors, control, method, action);
					context.addCFServlet(servlet); // 添加 Action 到 context 中
					register.addMapping(servlet.getUrlMapping());
					// 将当前Servlet 类的名称添加到集合
					SERVLET_NAME_ALL_LIST.add(servlet.getName());
				}
			}
			context.addBean(this); // 添加 bean 到 context

		}

		CFLog.debug(this.getClass().getName() + ".onStartup. ");
	}

	public final CFServletContext getContext() {
		return this.context;
	}

	protected void initialize(ServletConfig config) {
	}

	public final void init() throws ServletException {
		super.init();
		CFParanamer paranamer = new CFBytecodeParanamer();
		for (String servletName : SERVLET_NAME_ALL_LIST) { // 初始化参数配置
			CFActionServlet servlet = getContext().getCFServlet(servletName);
			Class<?>[] types = servlet.getMethod().getParameterTypes();
			String[] names = paranamer.lookupParameterNames(servlet.getMethod());
			if(names == null || names.length != types.length) {
				throw new RuntimeException("Read thd action parameters fail. ");
			}
			for (int i = 0, j = types.length; i < j; i++) {
				CFEditor editor = CFEditorBinding.getInstence().getEditor(types[i]);
				servlet.getParameter().addParameter(names[i], types[i], editor, null);
			}
		}
	}

	public final void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.initialize(config);
	}

	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws ServletException, IOException {
		this.doHandler(httpServletRequest, httpServletResponse);
	}

	@Override
	protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws ServletException, IOException {
		this.doHandler(httpServletRequest, httpServletResponse);
	}

	protected final void doHandler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws ServletException, IOException {
		try {
			// 重新实现 HttpServletRequest, 方便获取文件数据
			CFHttpServletRequest request = new CFHttpServletRequest(httpServletRequest);
			CFHttpServletResponse response = new CFHttpServletResponse(httpServletResponse);

			CFModel model = new CFModelImpl(request); // 创建Mode对象
			CFActionServlet servlet = context.getCFServlet(request.getRequestURI());
			if(servlet == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			// 调用登录验证方法 ,交获取指定视图渲染文件地址
			String viewPath = this.loginInterceptor(servlet, model, request, response);
			// 渲染页面 和 数据
			servlet.getRender().render(model, servlet.getView(), viewPath, request, response);
		} catch (Throwable throwable) {
			throwable = CFThrowable.getLastCause(throwable);
			CFLog.error("Service response error. ", throwable);
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, throwable.getMessage());
		}
	}

	/**
	 * 登录验证, 异常处理, 并调用参数绑定方法
	 * @param porxy ActionPorxy 对象
	 * @param model 数据模型对象
	 * @param request DefaultRequest 对象
	 * @param response DefaultResponse 对象
	 * @return
	 * @throws Exception
	 */
	protected final String loginInterceptor(CFActionServlet porxy, CFModel model, CFHttpServletRequest request, //
		CFHttpServletResponse response) throws Exception {
		try {
			CFUser usessin = (CFUser) request.getSession().getAttribute(CFUser.USER_KEY);
			if(porxy.getAction().permiss() > 0 && usessin == null) {
				if(porxy.getAction().value() == CFType.json) {
					model.setError(600).setMessage("Not login in error. ");
					return null;
				}
				if(StringUtils.isNotBlank(context.getLoginUrl())) {
					StringBuilder uri = new StringBuilder(porxy.getName());
					if(StringUtils.isNotBlank(request.getQueryString())) {
						uri.append("?").append(request.getQueryString());
					}
					return "r:" + context.getLoginUrl() + "?uri="
						+ CFString.urlEncode(uri.toString(), context.getEncoding());
				}
				throw new RuntimeException("Not login in error.");
			}
			return paramInterceptor(porxy, model, request, response);
		} catch (Throwable throwable) {
			throwable = CFThrowable.getLastCause(throwable);
			if(porxy.getAction().value() == CFType.json) {
				// 如果model.error == 0 时,说明没有设置错误信息,强制设置错误码为500, 错误信息为异常信息
				if(model.getError() == 0) {
					model.setError(500).setMessage(throwable.getMessage());
					CFLog.error("Service response error. ", throwable);
				}
				return null;
			}
			throw new ServletException(throwable);
		}
	}

	/**
	 * 绑定参数, 创建数据库连接, 并调用Action调用方法
	 * @param porxy ActionPorxy 对象
	 * @param model 数据模型对象
	 * @param request DefaultRequest 对象
	 * @param response DefaultResponse 对象
	 * @return
	 * @throws Exception
	 */
	protected final String paramInterceptor(CFActionServlet porxy, CFModel model, CFHttpServletRequest request, //
		CFHttpServletResponse response) throws Exception {
		try {
			CFActionParameter parameter = porxy.getParameter();
			for (int i = 0, j = parameter.length(); i < j; i++) {
				try {
					parameter.setValue(i, parameter.getEditor(i).value(parameter.getName(i), //
						parameter.getType(i), request, response));
				} catch (Exception e) {
					throw new CFParamBindException("Param binding error. ", e, parameter.getName(i));
				}
			}
			return new CFActionInvoke(porxy).invoke();
		} finally {
			for (String name : CFDBFactory.getThreadDB().keySet()) {
				CFDBFactory.close(CFDBFactory.getThreadDB().get(name));
			}
		}
	}
}
