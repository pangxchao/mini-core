package com.mini.core.util;

import java.io.Serializable;
import java.util.EventListener;

import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang3.Validate.isTrue;

public final class WorkerId implements Serializable, EventListener {
	private static final long serialVersionUID = 27599062740655580L;
	// 2016-01-01 08:00:00+8:00, 2016-01-01 00:00:00+0:00
	private static final long BASE_TIME = 1451606400000L;
	// 自增长序列部分（设计14位）
	private static final long MAX_SEQUENCE = ~(-1L << 14);
	// 集群编号部分(设计8位)
	private static final long MAX_WORK = ~(-1L << 8);
	//
	private long lastTimestamp = -1L;
	// 集群编号
	private final long workerId;
	// 自增序列
	private long sequence = 0;

	public WorkerId(long workerId) {
		isTrue(workerId <= MAX_WORK);
		this.workerId = workerId;
	}
	
	public final synchronized long nextId() {
		long now = System.currentTimeMillis();
		while (sequence > MAX_SEQUENCE) {
			now = currentTimeMillis();
			if (lastTimestamp < now) {
				sequence = 0;
			}
		}
		this.lastTimestamp = now;
		var time = (lastTimestamp - BASE_TIME) << 22;
		var seq = (sequence++ & MAX_SEQUENCE) << 8;
		return time | seq | (workerId & MAX_WORK);
	}
	
	/**
	 * 根据主键获取生成主键时的时间缀
	 * @param id 主键
	 * @return 时间戳
	 */
	public static long millis(long id) {
		return (id >> 22) + BASE_TIME;
	}
}
