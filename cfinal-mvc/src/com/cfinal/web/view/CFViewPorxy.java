/**
 * Created the com.cfinal.web.view.CFViewPorxy.java
 * @created 2017年2月23日 下午3:24:02
 * @version 1.0.0
 */
package com.cfinal.web.view;

import com.cfinal.web.central.CFAbstractPorxy;

/**
 * com.cfinal.web.view.CFViewPorxy.java 
 * @author XChao
 */
public class CFViewPorxy extends CFAbstractPorxy {

	private String pageSuffix;

	/**
	 * @return the pageSuffix
	 */
	public String getPageSuffix() {
		return pageSuffix;
	}

	/**
	 * @param pageSuffix the pageSuffix to set
	 */
	public void setPageSuffix(String pageSuffix) {
		this.pageSuffix = pageSuffix;
	}
}
