/**
 * Created the com.cfinal.web.CFServlet.java
 * @created 2016年9月24日 下午12:28:55
 * @version 1.0.0
 */
package com.cfinal.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.cfinal.db.CFDBFactory;
import com.cfinal.util.lang.CFString;
import com.cfinal.util.lang.CFThrowable;
import com.cfinal.util.logger.CFLogger;
import com.cfinal.web.annotaion.CFType;
import com.cfinal.web.central.CFContext;
import com.cfinal.web.central.CFInitialize;
import com.cfinal.web.central.CFInvocation;
import com.cfinal.web.control.CFActionInvocation;
import com.cfinal.web.control.CFActionParameter;
import com.cfinal.web.control.CFActionPorxy;
import com.cfinal.web.editor.CFParamBindException;
import com.cfinal.web.entity.CFUser;
import com.cfinal.web.model.CFModel;
import com.cfinal.web.model.CFModelImpl;

/**
 * com.cfinal.web.CFServlet.java
 * @author XChao
 */
@MultipartConfig
public class CFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CFContext context;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		context = CFInitialize.getContext();
	}

	@Override
	protected void doDelete(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
		throws ServletException, IOException {
		super.doDelete(httpRequest, httpResponse);
	}

	@Override
	protected void doHead(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
		throws ServletException, IOException {
		super.doHead(httpRequest, httpResponse);
	}

	@Override
	protected void doOptions(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
		throws ServletException, IOException {
		super.doOptions(httpRequest, httpResponse);
	}

	@Override
	protected void doPut(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
		throws ServletException, IOException {
		super.doPut(httpRequest, httpResponse);
	}

	@Override
	protected void doTrace(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
		throws ServletException, IOException {
		super.doTrace(httpRequest, httpResponse);
	}

	@Override
	protected void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
		throws ServletException, IOException {
		this.doPost(httpRequest, httpResponse);
	}

	@Override
	protected void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
		throws ServletException, IOException {
		try {
			// 重新实现 HttpServletRequest, 方便获取文件数据
			CFRequest request = new CFRequest(httpRequest);
			CFResponse response = new CFResponse(httpResponse);
			// 设置 DefaultRequest、DefaultResponse 对象编码
			request.setCharacterEncoding(context.getEncoding());
			response.setCharacterEncoding(context.getEncoding());
			// 创建Mode对象
			CFModel model = new CFModelImpl();
			// 将 Model 对象存入 HttpServletRequest中
			request.setAttribute(CFModel.MODEL_KEY, model);
			// 获取ActionPorxy 对象
			CFActionPorxy porxy = context.getAction(request.getRequestURI());
			// 如果Action代理对象为空， 表示页面不存在，报出404错误
			if(porxy == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			// 调用登录验证方法 ,交获取指定视图渲染文件地址
			String viewPath = this.loginInterceptor(porxy, model, request, response);
			// 将视图渲染文件地址存入 ActionPorxy 对象
			porxy.setCurrentViewPath(StringUtils.defaultIfEmpty(viewPath, porxy.getDefaultViewPath()));
			// 渲染页面 和 数据
			porxy.getRender().render(model, porxy, request, response);
		} catch (Throwable throwable) {
			throwable = CFThrowable.getLastCause(throwable);
			CFLogger.severe("Service response error. ", throwable);
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, throwable.getMessage());
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
	protected String loginInterceptor(CFActionPorxy porxy, CFModel model, CFRequest request, CFResponse response)
		throws Exception {
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
					CFLogger.severe("Service response error. ", throwable);
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
	protected String paramInterceptor(CFActionPorxy porxy, CFModel model, CFRequest request, CFResponse response)
		throws Exception {
		try {
			CFActionParameter parameter = porxy.getParameter();
			for (int i = 0, j = parameter.length(); i < j; i++) {
				try {
					parameter.setValue(i,
						parameter.getEditor(i).value(parameter.getName(i), parameter.getType(i), request, response));
				} catch (Exception e) {
					throw new CFParamBindException("Param binding error. ", e, parameter.getName(i));
				}
			}
			return actionInvocation(porxy, model, request, response);
		} finally {
			for (String name : CFDBFactory.getThreadDB().keySet()) {
				CFDBFactory.close(CFDBFactory.getThreadDB().get(name));
			}
		}
	}

	/**
	 * Action 调用
	 * @param porxy ActionPorxy 对象
	 * @param model 数据模型对象
	 * @param request DefaultRequest 对象
	 * @param response DefaultResponse 对象
	 * @return
	 * @throws Exception
	 */
	protected String actionInvocation(CFActionPorxy porxy, CFModel model, CFRequest request, CFResponse response)
		throws Exception {
		// 创建Action调用对象 ActionInvocation,并设置ActionPorxy对象
		CFInvocation invocation = new CFActionInvocation(porxy);
		// 设置HttpServletRequest
		invocation.setRequest(request);
		// 设置 HttpServletResponse
		invocation.setResponse(response);
		// 调用过虑器 和 Action 方法
		return invocation.invoke();
	}

}
