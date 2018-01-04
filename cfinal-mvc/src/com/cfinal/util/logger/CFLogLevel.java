/**
 * Created the com.cfinal.util.logger.CFLevel.java
 * @created 2017年8月14日 下午5:00:39
 * @version 1.0.0
 */
package com.cfinal.util.logger;

/**
 * com.cfinal.util.logger.CFLevel.java
 * @author XChao
 */
public enum CFLogLevel {

	OFF(0, 0), FATAL(1, 1), ERROR(2, 3), WARN(4, 7), INFO(8, 15), DEBUG(16, 31), TRACE(32, 63), ALL(64, 127);
	private int value = 0;
	private int outs = 0;

	private CFLogLevel(int value, int outs) {
		this.value = value;
		this.outs = outs;
	}

	public int getValue() {
		return this.value;
	}

	public int getOuts() {
		return this.outs;
	}
}
