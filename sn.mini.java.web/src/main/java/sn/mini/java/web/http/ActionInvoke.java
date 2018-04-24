/**
 * Created the sn.mini.java.web.http.ActionInvoke.java
 * @created 2017年10月9日 下午6:01:06
 * @version 1.0.0
 */
package sn.mini.java.web.http;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sn.mini.java.util.lang.reflect.SNMethod;
import sn.mini.java.util.lang.reflect.SNParameter;
import sn.mini.java.web.annotaion.Action;
import sn.mini.java.web.annotaion.Control;
import sn.mini.java.web.http.interceptor.Interceptor;
import sn.mini.java.web.http.rander.IRender;
import sn.mini.java.web.http.view.IView;
import sn.mini.java.web.model.IModel;
import sn.mini.java.web.util.IUser;
import sn.mini.java.web.util.WebUtil;

/**
 * sn.mini.java.web.http.ActionInvoke.java
 * @author XChao
 */
public class ActionInvoke {

	private final ActionProxy proxy;
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final Iterator<Interceptor> iterator;
	private final IModel model;

	public ActionInvoke(ActionProxy proxy, HttpServletRequest request, HttpServletResponse response, IModel model) {
		this.iterator = proxy.getInterceptors().iterator();
		this.proxy = proxy;
		this.request = request;
		this.response = response;
		this.model = model;
	}

	public ActionProxy getProxy() {
		return proxy;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return this.request.getSession();
	}

	public Iterator<Interceptor> getIterator() {
		return iterator;
	}

	public IModel getModel() {
		return model;
	}

	public IUser getUser() {
		return WebUtil.getAttribute(this.getSession(), IUser.USER_KEY, IUser.class);
	}

	public Controller getController() {
		return this.proxy.getController();
	}

	public Control getControl() {
		return this.proxy.getControl();
	}

	public SNMethod getMethod() {
		return this.proxy.getMethod();
	}

	public Action getAction() {
		return this.proxy.getAction();
	}

	public String getName() {
		return this.proxy.getName();
	}

	public String getPath() {
		return this.proxy.getPath();
	}

	public String getUrl() {
		return this.proxy.getUrl();
	}

	public IRender getRender() {
		return this.proxy.getRender();
	}

	public IView getView() {
		return this.proxy.getView();
	}

	public List<Interceptor> getInterceptors() {
		return this.proxy.getInterceptors();
	}

	public SNParameter[] getParameters() {
		return this.proxy.getParameters();
	}

	public SNParameter getParameter(String name) {
		return this.proxy.getParameter(name);
	}

	public SNParameter getParameter(int index) {
		return this.proxy.getParameter(index);
	}

	public Object getParameterValue(String name) {
		return this.proxy.getParameterValue(name);
	}

	public Object getParameterValue(int index) {
		return this.proxy.getParameterValue(index);
	}

	public <T> T getParameterValue(String name, Class<T> clazz) {
		return this.proxy.getParameterValue(name, clazz);
	}

	public <T> T getParameterValue(int index, Class<T> clazz) {
		return this.proxy.getParameterValue(index, clazz);
	}

	public void setParameterValue(String name, Object value) {
		this.proxy.setParameterValue(name, value);
	}

	public void setParameterValue(int index, Object value) {
		this.proxy.setParameterValue(index, value);
	}

	public Object[] getParameterValues() {
		return this.proxy.getParameterValues();
	}

	public <T> void setParameterValues(T t) {
		for (SNParameter parameter : this.getParameters()) {
			if(t.getClass().isAssignableFrom(parameter.getType())) {
				this.setParameterValue(parameter.getName(), t);
			}
		}
	}

	public String invoke() throws Exception {
		return this.iterator.hasNext() ? this.iterator.next().interceptor(this) : this.proxy.invoke();
	}
}
