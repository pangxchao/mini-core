/**
 * Created the com.cfinal.web.scheduler.matcher.CFTaskMatcher.java
 * @created 2016年10月17日 下午4:51:47
 * @version 1.0.0
 */
package com.cfinal.web.scheduler.matcher;

import com.cfinal.web.central.CFBasics;

/**
 * com.cfinal.web.scheduler.matcher.CFTaskMatcher.java
 * @author XChao
 */
public interface CFTaskMatcher extends CFBasics {
	public boolean match(int value);
}
