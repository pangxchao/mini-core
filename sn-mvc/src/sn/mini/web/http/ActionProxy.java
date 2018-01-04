/**
 * Created the sn.mini.web.http.ActionProxy.java
 * @created 2017年10月18日 上午9:54:48
 * @version 1.0.0
 */
package sn.mini.web.http;

import java.util.ArrayList;
import java.util.List;

import sn.mini.util.lang.reflect.SNMethod;
import sn.mini.util.lang.reflect.SNParameter;
import sn.mini.web.annotaion.Action;
import sn.mini.web.annotaion.Control;
import sn.mini.web.http.interceptor.Interceptor;
import sn.mini.web.http.rander.IRender;
import sn.mini.web.http.view.IView;

/**
 * sn.mini.web.http.ActionProxy.java
 * @author XChao
 */
public final class ActionProxy {

	private final Controller controller;
	private final Control control;
	private final SNMethod method;
	private final Action action;

	private final String name;
	private final String path;
	private final String url;

	private final IRender render;
	private final IView view;

	private final List<Interceptor> interceptors = new ArrayList<>();

	public ActionProxy(Controller controller, Control control, SNMethod method, Action action, String name, String path,
		String url, IRender render, IView view) {
		this.controller = controller;
		this.control = control;
		this.method = method;
		this.action = action;
		this.name = name;
		this.path = path;
		this.url = url;
		this.render = render;
		this.view = view;
	}

	public Controller getController() {
		return controller;
	}

	public Control getControl() {
		return control;
	}

	public SNMethod getMethod() {
		return method;
	}

	public Action getAction() {
		return action;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getUrl() {
		return url;
	}

	public IRender getRender() {
		return render;
	}

	public IView getView() {
		return view;
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	public void addInterceptor(Interceptor interceptor) {
		this.interceptors.add(interceptor);
	}

	public SNParameter[] getParameters() {
		return this.method.getParameters();
	}

	public SNParameter getParameter(String name) {
		return this.method.getParameter(name);
	}

	public SNParameter getParameter(int index) {
		return this.method.getParameter(index);
	}

	public Object getParameterValue(String name) {
		return this.method.getParameterValue(name);
	}

	public Object getParameterValue(int index) {
		return this.method.getParameterValue(index);
	}

	public <T> T getParameterValue(String name, Class<T> clazz) {
		return this.method.getParameterValue(name, clazz);
	}

	public <T> T getParameterValue(int index, Class<T> clazz) {
		return this.method.getParameterValue(index, clazz);
	}

	public void setParameterValue(String name, Object value) {
		this.method.setParameterValue(name, value);
	}

	public void setParameterValue(int index, Object value) {
		this.method.setParameterValue(index, value);
	}

	public Object[] getParameterValues() {
		return this.method.getParameterValues();
	}

	public String invoke() throws Exception {
		Object result = this.method.invoke(this.controller, this.getParameterValues());
		return String.class.isInstance(result) ? String.valueOf(result) : this.getPath();
	}

}
