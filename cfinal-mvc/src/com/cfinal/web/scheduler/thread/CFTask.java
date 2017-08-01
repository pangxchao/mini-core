/**
 * Created the com.cfinal.web.scheduler.thread.CFTask.java
 * @created 2016年9月29日 上午10:56:06
 * @version 1.0.0
 */
package com.cfinal.web.scheduler.thread;

import com.cfinal.web.central.CFBasics;

/**
 * com.cfinal.web.scheduler.thread.CFTask.java
 * @author XChao
 */
public interface CFTask extends CFBasics {
	public void execute();
}
