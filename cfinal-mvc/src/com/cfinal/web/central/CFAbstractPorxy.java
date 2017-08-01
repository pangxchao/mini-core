/**
 * Created the com.cfinal.web.central.CFAbstractPorxy.java
 * @created 2016年9月27日 下午6:29:10
 * @version 1.0.0
 */
package com.cfinal.web.central;

import java.lang.reflect.Method;

/**
 * com.cfinal.web.central.CFAbstractPorxy.java
 * @author XChao
 */
public abstract class CFAbstractPorxy implements CFPorxy {

	private String name;
	private Object instence;
	private Method method;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the instence
	 */
	public Object getInstence() {
		return instence;
	}

	/**
	 * @param instence the instence to set
	 */
	public void setInstence(Object instence) {
		this.instence = instence;
	}

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	@Override
	public String execute(Object... args) throws Exception {
		Object result = method.invoke(instence, args);
		if (result != null && String.class.isInstance(result)) {
			return String.valueOf(result);
		}
		return null;
	}
}
