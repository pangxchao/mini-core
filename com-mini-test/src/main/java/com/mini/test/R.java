package com.mini.test;

import javax.inject.Inject;
import javax.inject.Named;

import static org.apache.commons.lang3.math.NumberUtils.toLong;

public final class R {
	// 云节点相关配置
	@Inject
	@Named("CLOUD_ID")
	private static String cloudId;
	
	@Inject
	@Named("CLOUD_CODE")
	private static String cloudCode;
	
	public static long getCloudId() {
		return toLong(cloudId);
	}
	
	public static String getCloudCode() {
		return cloudCode;
	}
	
}
