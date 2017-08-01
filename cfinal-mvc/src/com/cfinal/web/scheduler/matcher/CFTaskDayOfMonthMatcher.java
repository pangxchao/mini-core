/**
 * Created the com.cfinal.web.scheduler.matcher.CFTaskDayOfMonthMatcher.java
 * @created 2016年10月17日 下午4:55:30
 * @version 1.0.0
 */
package com.cfinal.web.scheduler.matcher;

import java.util.List;

/**
 * com.cfinal.web.scheduler.matcher.CFTaskDayOfMonthMatcher.java
 * @author XChao
 */
public class CFTaskDayOfMonthMatcher extends CFTaskIntegerMatcher {
	private static final int[] lastDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public CFTaskDayOfMonthMatcher(List<Integer> values) {
		super(values);
	}

	public boolean match(int value, int month, boolean isLeapYear) {
		return (super.match(value) || isLastDayOfMonth(value, month, isLeapYear));
	}

	public boolean isLastDayOfMonth(int value, int month, boolean isLeapYear) {
		if (isLeapYear && month == 2) {
			return value == 29;
		} else {
			return value == lastDays[month - 1];
		}
	}
}
