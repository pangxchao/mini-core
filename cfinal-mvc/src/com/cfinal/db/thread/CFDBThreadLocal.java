/**
 * Created the com.cfinal.db.thread.CFDBThreadLocal.java
 * @created 2017年2月23日 上午11:13:26
 * @version 1.0.0
 */
package com.cfinal.db.thread;

import java.util.HashMap;
import java.util.Map;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.thread.CFDBThreadLocal.java
 * @author XChao
 */
public class CFDBThreadLocal extends ThreadLocal<Map<String, CFDB>> {
	public Map<String, CFDB> initialValue() {
		return new HashMap<String, CFDB>();
	}
}
