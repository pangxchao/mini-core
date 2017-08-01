/**
 * Created the com.cfinal.web.interceptor.CFInterceptorPorxy.java
 * @created 2016年9月26日 下午2:58:35
 * @version 1.0.0
 */
package com.cfinal.web.interceptor;

import com.cfinal.web.central.CFAbstractPorxy;
import com.cfinal.web.control.CFActionInvocation;

/**
 * com.cfinal.web.interceptor.CFInterceptorPorxy.java
 * @author XChao
 */
public class CFInterceptorPorxy extends CFAbstractPorxy {
	public String invoke(CFActionInvocation xDefaultActionInvocation) throws Exception {
		return this.execute(xDefaultActionInvocation);
	}
}
