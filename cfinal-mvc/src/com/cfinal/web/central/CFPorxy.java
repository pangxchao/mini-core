/**   
 * Created the com.cfinal.web.central.CFPorxy.java
 * @created 2016年9月29日 上午10:55:29 
 * @version 1.0.0 
 */
package com.cfinal.web.central;


import java.lang.reflect.Method;

/**
 * com.cfinal.web.central.CFPorxy.java
 * @author XChao
 */
public interface CFPorxy extends CFBasics {
	
	public String getName();

	public void setName(String name);

	public Object getInstence();

	public void setInstence(Object instence);

	public Method getMethod();

	public void setMethod(Method method);

	public String execute(Object... args) throws Exception;
}
