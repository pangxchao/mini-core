/**
 * Created the com.cfinal.web.control.CFActionPorxy.java
 * @created 2016年9月26日 下午2:52:23
 * @version 1.0.0
 */
package com.cfinal.web.control;

import java.util.ArrayList;
import java.util.List;

import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.annotaion.CFControl;
import com.cfinal.web.central.CFAbstractPorxy;
import com.cfinal.web.interceptor.CFInterceptorPorxy;
import com.cfinal.web.render.CFRender;
import com.cfinal.web.view.CFViewPorxy;

/**
 * com.cfinal.web.control.CFActionPorxy.java
 * @author XChao
 */
public class CFActionPorxy extends CFAbstractPorxy {

	private CFAction action;

	private CFControl control;

	private CFRender render;

	private String defaultViewPath;

	private String currentViewPath;

	private CFViewPorxy viewPorxy;

	private CFActionParameter parameter;

	private final List<CFInterceptorPorxy> interceptorsPorxy = new ArrayList<>();

	/**
	 * @return the action
	 */
	public CFAction getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(CFAction action) {
		this.action = action;
	}

	/**
	 * @return the control
	 */
	public CFControl getControl() {
		return control;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(CFControl control) {
		this.control = control;
	}

	/**
	 * @return the render
	 */
	public CFRender getRender() {
		return render;
	}

	/**
	 * @param render the render to set
	 */
	public void setRender(CFRender render) {
		this.render = render;
	}

	/**
	 * @return the defaultViewPath
	 */
	public String getDefaultViewPath() {
		return defaultViewPath;
	}

	/**
	 * @param defaultViewPath the defaultViewPath to set
	 */
	public void setDefaultViewPath(String defaultViewPath) {
		this.defaultViewPath = defaultViewPath;
	}

	/**
	 * @return the currentViewPath
	 */
	public String getCurrentViewPath() {
		return currentViewPath;
	}

	/**
	 * @param currentViewPath the currentViewPath to set
	 */
	public void setCurrentViewPath(String currentViewPath) {
		this.currentViewPath = currentViewPath;
	}

	/**
	 * @return the viewPorxy
	 */
	public CFViewPorxy getViewPorxy() {
		return viewPorxy;
	}

	/**
	 * @param viewPorxy the viewPorxy to set
	 */
	public void setViewPorxy(CFViewPorxy viewPorxy) {
		this.viewPorxy = viewPorxy;
	}

	/**
	 * @return the parameter
	 */
	public CFActionParameter getParameter() {
		return parameter;
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(CFActionParameter parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the interceptorsPorxy
	 */
	public List<CFInterceptorPorxy> getInterceptorsPorxy() {
		return interceptorsPorxy;
	}

	/**
	 * 添加一个拦截器
	 * @param interceptorPorxy
	 */
	public void addInterceptorPorxy(CFInterceptorPorxy interceptorPorxy) {
		this.interceptorsPorxy.add(interceptorPorxy);
	}
}
