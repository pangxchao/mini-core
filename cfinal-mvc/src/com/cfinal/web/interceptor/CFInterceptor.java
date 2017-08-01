/**
 * Created the com.cfinal.web.interceptor.CFInterceptor.java
 * @created 2016年9月24日 下午7:58:12
 * @version 1.0.0
 */
package com.cfinal.web.interceptor;

import com.cfinal.web.central.CFBasics;
import com.cfinal.web.central.CFInvocation;

/**
 * 过虑器(拦截器)接口
 * @author XChao
 */
public interface CFInterceptor extends CFBasics {

	/**
	 * 过虑器执行方法
	 * @param invocation
	 * @throws Exception
	 */
	String intercept(CFInvocation invocation) throws Exception;

}
