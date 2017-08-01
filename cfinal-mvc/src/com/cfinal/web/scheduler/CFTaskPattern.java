/**
 * Created the com.cfinal.web.scheduler.CFTaskPattern.java
 * @created 2016年10月9日 上午11:10:52
 * @version 1.0.0
 */
package com.cfinal.web.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import com.cfinal.web.scheduler.matcher.CFTaskMatcher;
import com.cfinal.web.scheduler.matcher.CFTaskAlwaysMatcher;
import com.cfinal.web.scheduler.matcher.CFTaskDayOfMonthMatcher;
import com.cfinal.web.scheduler.matcher.CFTaskIntegerMatcher;
import com.cfinal.web.scheduler.parser.CFTaskParser;
import com.cfinal.web.scheduler.parser.CFTaskDayOfMonthParser;
import com.cfinal.web.scheduler.parser.CFTaskDayOfWeekParser;
import com.cfinal.web.scheduler.parser.CFTaskHourParser;
import com.cfinal.web.scheduler.parser.CFTaskMinuteParser;
import com.cfinal.web.scheduler.parser.CFTaskMonthParser;

/**
 * com.cfinal.web.scheduler.CFTaskPattern.java
 * @author XChao
 */
public class CFTaskPattern {
	private static final CFTaskParser MINUTE_VALUE_PARSER = new CFTaskMinuteParser();
	private static final CFTaskParser HOUR_VALUE_PARSER = new CFTaskHourParser();
	private static final CFTaskParser DAY_OF_MONTH_VALUE_PARSER = new CFTaskDayOfMonthParser();
	private static final CFTaskParser MONTH_VALUE_PARSER = new CFTaskMonthParser();
	private static final CFTaskParser DAY_OF_WEEK_VALUE_PARSER = new CFTaskDayOfWeekParser();

	protected CFTaskMatcher minuteMatcher;
	protected CFTaskMatcher hourMatcher;
	protected CFTaskMatcher dayOfMonthMatcher;
	protected CFTaskMatcher monthMatcher;
	protected CFTaskMatcher dayOfWeekMatcher;
	private String schedulingPattern;

	protected CFTaskPattern(String pattern) {
		this.schedulingPattern = pattern;
		StringTokenizer patternTokenizer = new StringTokenizer(pattern, " \t");
		if (patternTokenizer.countTokens() != 5) {
			throw new RuntimeException("invalid pattern: \"" + pattern + "\"");
		}
		try {
			this.minuteMatcher = buildValueMatcher(patternTokenizer.nextToken(), MINUTE_VALUE_PARSER);
		} catch (Exception e) {
			throw new RuntimeException("invalid pattern \"" + pattern + "\". Error parsing minutes field: " + e.getMessage() + ".");
		}
		try {
			this.hourMatcher = buildValueMatcher(patternTokenizer.nextToken(), HOUR_VALUE_PARSER);
		} catch (Exception e) {
			throw new RuntimeException("invalid pattern \"" + pattern + "\". Error parsing hours field: " + e.getMessage() + ".");
		}
		try {
			this.dayOfMonthMatcher = buildValueMatcher(patternTokenizer.nextToken(), DAY_OF_MONTH_VALUE_PARSER);
		} catch (Exception e) {
			throw new RuntimeException("invalid pattern \"" + pattern + "\". Error parsing days of month field: " + e.getMessage() + ".");
		}
		try {
			this.monthMatcher = buildValueMatcher(patternTokenizer.nextToken(), MONTH_VALUE_PARSER);
		} catch (Exception e) {
			throw new RuntimeException("invalid pattern \"" + pattern + "\". Error parsing months field: " + e.getMessage() + ".");
		}
		try {
			this.dayOfWeekMatcher = buildValueMatcher(patternTokenizer.nextToken(), DAY_OF_WEEK_VALUE_PARSER);
		} catch (Exception e) {
			throw new RuntimeException("invalid pattern \"" + pattern + "\". Error parsing days of week field: " + e.getMessage() + ".");
		}
	}

	private CFTaskMatcher buildValueMatcher(String pattern, CFTaskParser parser) throws Exception {
		if (pattern.length() == 1 && pattern.equals("*")) {
			return new CFTaskAlwaysMatcher();
		}
		StringTokenizer patternTokanizer = new StringTokenizer(pattern, "/");
		int countTokens = patternTokanizer.countTokens();
		if (countTokens < 1 || countTokens > 2) {
			throw new Exception("invalid field \"" + pattern + "\". ");
		}
		List<Integer> values = null;
		String patternV1String = patternTokanizer.nextToken();
		values = this.buildValueMatcherRange(patternV1String, parser);
		if (countTokens == 2) {
			String patternV2String = patternTokanizer.nextToken();
			int patternV2Integer = -1;
			try {
				patternV2Integer = parser.parse(patternV2String);
			} catch (Exception e) {
				throw new Exception("invalid value \"" + patternV2String + "\", " + e.getMessage());
			}
			if (patternV2Integer < 1) {
				throw new Exception("non positive divisor \"" + patternV2Integer + "\"");
			}
			List<Integer> values2 = new ArrayList<Integer>();
			for (int i = 0; i < values.size(); i += patternV2Integer) {
				values2.add(values.get(i));
			}
			values = values2;
		}
		try {
			if (values == null || values.size() == 0) {
				throw new Exception("invalid field \"" + pattern + "\"");
			}
			if (parser == DAY_OF_MONTH_VALUE_PARSER) {
				return new CFTaskDayOfMonthMatcher(values);
			} else {
				return new CFTaskIntegerMatcher(values);
			}
		} catch (Exception e) {
			throw new Exception("invalid field \"" + pattern + "\"," + e.getMessage());
		}
	}

	private List<Integer> buildValueMatcherRange(String pattern, CFTaskParser parser) throws Exception {
		if (pattern.equals("*")) {
			int min = parser.getMinValue();
			int max = parser.getMaxValue();
			List<Integer> values = new ArrayList<Integer>();
			for (int i = min; i <= max; i++) {
				values.add(Integer.valueOf(i));
			}
			return values;
		}
		StringTokenizer patternTokanizer = new StringTokenizer(pattern, "-");
		int countTokens = patternTokanizer.countTokens();
		if (countTokens < 1 || countTokens > 2) {
			throw new Exception("invalid field \"" + pattern + "\". ");
		}
		String patternV1String = patternTokanizer.nextToken();
		int patternV1Integer = -1;
		try {
			patternV1Integer = parser.parse(patternV1String);
		} catch (Exception e) {
			throw new Exception("invalid value \"" + patternV1String + "\", " + e.getMessage());
		}
		if (countTokens == 1) {
			List<Integer> values = new ArrayList<Integer>();
			values.add(patternV1Integer);
			return values;
		}
		String patternV2String = patternTokanizer.nextToken();
		int patternV2Integer = -1;
		try {
			patternV2Integer = parser.parse(patternV2String);
		} catch (Exception e) {
			throw new Exception("invalid value \"" + patternV2String + "\", " + e.getMessage());
		}
		List<Integer> values = new ArrayList<Integer>();
		if (patternV1Integer < patternV2Integer) {
			for (int i = patternV1Integer; i <= patternV2Integer; i++) {
				values.add(Integer.valueOf(i));
			}
		} else if (patternV1Integer > patternV2Integer) {
			for (int i = patternV1Integer; i <= parser.getMaxValue(); i++) {
				values.add(Integer.valueOf(i));
			}
			for (int i = parser.getMinValue(); i <= patternV2Integer; i++) {
				values.add(Integer.valueOf(i));
			}
		} else {
			values.add(Integer.valueOf(patternV1Integer));
		}
		return values;

	}

	public boolean match(TimeZone timezone, long millis) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(millis);
		gc.setTimeZone(timezone);
		int minute = gc.get(Calendar.MINUTE);
		int hour = gc.get(Calendar.HOUR_OF_DAY);
		int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
		int month = gc.get(Calendar.MONTH) + 1;
		int dayOfWeek = gc.get(Calendar.DAY_OF_WEEK) - 1;
		int year = gc.get(Calendar.YEAR);
		boolean result = minuteMatcher.match(minute) && hourMatcher.match(hour);
		if (dayOfMonthMatcher instanceof CFTaskDayOfMonthMatcher) {
			result = result && ((CFTaskDayOfMonthMatcher) dayOfMonthMatcher).match(dayOfMonth, month, gc.isLeapYear(year));
		} else {
			result = result && dayOfMonthMatcher.match(dayOfMonth);
		}
		return result && monthMatcher.match(month) && dayOfWeekMatcher.match(dayOfWeek);
	}

	public boolean match(long millis) {
		return match(TimeZone.getDefault(), millis);
	}

	public String toString() {
		return this.schedulingPattern;
	}
}
