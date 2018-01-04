/**
 * Created the com.cfinal.web.scheduler.matcher.CFTaskIntegerMatcher.java
 * @created 2016年10月17日 下午4:53:24
 * @version 1.0.0
 */
package com.cfinal.web.timmer.matcher;

import java.util.List;

/**
 * com.cfinal.web.scheduler.matcher.CFTaskIntegerMatcher.java
 * @author XChao
 */
public class CFTaskIntegerMatcher implements CFTaskMatcher {
	private List<Integer> values;

	public CFTaskIntegerMatcher(List<Integer> values) {
		this.values = values;
	}

	public boolean match(int value) {
		for (Integer v : values) {
			if (v != null && v.intValue() == value) {
				return true;
			}
		}
		return false;
	}
}
