/**
 * Created the com.cfinal.web.scheduler.CFTaskPorxy.java
 * @created 2016年9月26日 下午3:37:16
 * @version 1.0.0
 */
package com.cfinal.web.scheduler;

import com.cfinal.web.central.CFAbstractPorxy;

/**
 * com.cfinal.web.scheduler.CFTaskPorxy.java
 * @author XChao
 */
public class CFTaskPorxy extends CFAbstractPorxy {
	private String rule = "* * * * *";
	
	/**
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}

}
