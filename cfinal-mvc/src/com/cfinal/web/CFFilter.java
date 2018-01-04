/**
 * Created the com.cfinal.web.core.CFFilter.java
 * @created 2017年8月21日 下午5:36:43
 * @version 1.0.0
 */
package com.cfinal.web;

import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;

import com.cfinal.util.logger.CFLog;

/**
 * com.cfinal.web.core.CFFilter.java
 * @author XChao
 */
public abstract class CFFilter implements CFService, Filter {
	public final void onStartup(CFServletContext context) {
		try {
			context.addBean(this);
			Dynamic register = context.addFilter(this.getClass().getName(), this);
			// 用URLMapping形式注册Filter
			List<String> urlMapping = this.getUrlMapping();
			if(urlMapping != null && urlMapping.size() > 0) {
				register.addMappingForUrlPatterns(this.getEnumSet(), true, //
					urlMapping.toArray(new String[urlMapping.size()]));
			}
			// 用ServletName形式注册Filter
			List<String> servletNames = this.getServletNames();
			if(servletNames != null && servletNames.size() > 0) {
				register.addMappingForServletNames(this.getEnumSet(), true, //
					servletNames.toArray(new String[servletNames.size()]));
			}
		} catch (Throwable throwable) {
			CFLog.error("CFFilter.onStartup error. ", throwable);
		}
	}

	protected abstract EnumSet<DispatcherType> getEnumSet();

	protected abstract List<String> getUrlMapping();

	protected abstract List<String> getServletNames();
}
