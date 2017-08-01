/**
 * Created the com.cfinal.web.control.CFActionInvocation.java
 * @created 2016年9月28日 下午12:50:08
 * @version 1.0.0
 */
package com.cfinal.web.control;

import java.util.Iterator;

import javax.servlet.http.HttpSession;

import com.cfinal.db.CFDB;
import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.central.CFInvocation;
import com.cfinal.web.central.CFParameter;
import com.cfinal.web.entity.CFUser;
import com.cfinal.web.interceptor.CFInterceptorPorxy;
import com.cfinal.web.model.CFModel;

/**
 * com.cfinal.web.control.CFActionInvocation.java
 * @author XChao
 */
public class CFActionInvocation implements CFInvocation {
	private CFActionPorxy actionPorxy;
	private CFRequest request;
	private CFResponse response;
	private Iterator<CFInterceptorPorxy> iterator;

	public CFActionInvocation(CFActionPorxy actionPorxy) {
		iterator = actionPorxy.getInterceptorsPorxy().iterator();
		this.actionPorxy = actionPorxy;
	}

	public String getName() {
		return actionPorxy.getName();
	}

	public CFAction getAction() {
		return actionPorxy.getAction();
	}

	public CFRequest getRequest() {
		return this.request;
	}

	public void setRequest(CFRequest request) {
		this.request = request;
	}

	public CFResponse getResponse() {
		return this.response;
	}

	public void setResponse(CFResponse response) {
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
		return actionPorxy.getParameter();
	}

	public <T> T getParameter(String name, Class<T> clazz) {
		return clazz.cast(this.request.getAttribute(//
			CFParameter.PARAMETER_KEY + name));
	}

	public String invoke() throws Exception {
		if(this.iterator.hasNext()) {
			CFInterceptorPorxy interceptorPorxy = iterator.next();
			return interceptorPorxy.invoke(CFActionInvocation.this);
		}
		return this.actionPorxy.execute(this.getParameters().getValues());
	}

}
