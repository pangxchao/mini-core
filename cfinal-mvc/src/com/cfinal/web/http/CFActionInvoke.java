/**
 * Created the com.cfinal.web.control.CFActionInvocation.java
 * @created 2016年9月28日 下午12:50:08
 * @version 1.0.0
 */
package com.cfinal.web.http;

import java.util.Iterator;

import javax.servlet.http.HttpSession;

import com.cfinal.db.CFDB;
import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.http.interceptor.CFInterceptor;
import com.cfinal.web.model.CFModel;
import com.cfinal.web.model.CFUser;

/**
 * com.cfinal.web.control.CFActionInvocation.java
 * @author XChao
 */
public final class CFActionInvoke // implements InvocationHandler
{
	private CFActionServlet servlet;
	private CFHttpServletRequest request;
	private CFHttpServletResponse response;
	private Iterator<CFInterceptor> iterator;

	public CFActionInvoke(CFActionServlet servlet) {
		iterator = servlet.getInterceptors().iterator();
		this.servlet = servlet;
	}

	public String getName() {
		return servlet.getName();
	}

	public CFAction getAction() {
		return servlet.getAction();
	}

	public CFHttpServletRequest getRequest() {
		return this.request;
	}

	public void setRequest(CFHttpServletRequest request) {
		this.request = request;
	}

	public CFHttpServletResponse getResponse() {
		return this.response;
	}

	public void setResponse(CFHttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		return request.getSession();
	}

	public CFModel getModel() {
		return (CFModel) this.request.getAttribute(//
			CFModel.MODEL_KEY);
	}

	public CFDB getDB(String name) {
		return this.getParameter(name, CFDB.class);
	}

	public CFUser getUser() {
		return (CFUser) this.getSession().getAttribute(//
			CFUser.USER_KEY);
	}

	public CFParameter getParameters() {
		return servlet.getParameter();
	}

	public <T> T getParameter(String name, Class<T> clazz) {
		return clazz.cast(this.request.getAttribute(//
			CFParameter.PARAMETER_KEY + name));
	}

	/** 启用下一个拦截器或者 Action 方法 */
	public String invoke() throws Exception {
		return this.iterator.hasNext() ? iterator.next().intercept(CFActionInvoke.this) //
			: this.servlet.execute(this.getParameters().getValues());
	}

//	/** 启用下一个拦截器或者 Action 方法 */
//	public String invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		return this.iterator.hasNext() ? iterator.next().intercept(CFActionInvoke.this) //
//			: this.servlet.execute(this.getParameters().getValues());
//	}

}
