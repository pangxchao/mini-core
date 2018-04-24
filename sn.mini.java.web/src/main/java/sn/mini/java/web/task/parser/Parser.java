/**
 * Created the sn.mini.java.web.task.parser.Parser.java
 * @created 2017年11月1日 上午11:55:36
 * @version 1.0.0
 */
package sn.mini.java.web.task.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import sn.mini.java.util.lang.StringUtil;
import sn.mini.java.web.task.matcher.DefaultMatcher;
import sn.mini.java.web.task.matcher.IMatcher;

/**
 * sn.mini.java.web.task.parser.Parser.java
 * @author XChao
 */
public final class Parser {
	private static final IParser min_parser = new DefaultParser(0, 59);
	private static final IParser hou_parser = new DefaultParser(0, 23);
	private static final IParser day_parser = new DefaultParser(1, 31);
	private static final IParser mon_parser = new DefaultParser(1, 12);
	private static final IParser wee_parser = new DefaultParser(0, 7);

	private final IMatcher minute = new DefaultMatcher();
	private final IMatcher hour = new DefaultMatcher();
	private final IMatcher day = new DefaultMatcher();
	private final IMatcher month = new DefaultMatcher();
	private final IMatcher week = new DefaultMatcher();

	private String pattern;

	public Parser(String pattern) {
		this.pattern = pattern; //
		if(StringUtil.isBlank(pattern)) {
			throw new RuntimeException("Invalid patterns. Error parsing Blank. ");
		}
		StringTokenizer token = new StringTokenizer(pattern, " \t");
		if(token == null || token.countTokens() != 5) {
			throw new RuntimeException(String.format("Invalid pattern \"%s\"", pattern));
		}
		try {
			this.buildValueMatcher(minute, token.nextToken().trim(), min_parser);
			this.buildValueMatcher(hour, token.nextToken().trim(), hou_parser);
			this.buildValueMatcher(day, token.nextToken().trim(), day_parser);
			this.buildValueMatcher(month, token.nextToken().trim(), mon_parser);
			this.buildValueMatcher(week, token.nextToken().trim(), wee_parser);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Invalid pattern \"%s\".", pattern));
		}
	}

	private void buildValueMatcher(IMatcher matcher, String pattern, IParser parser) throws Exception {
		if(pattern.length() == 1 && pattern.equals("*")) {
			for (int i = parser.getMinValue(), l = parser.getMaxValue(); i <= l; i++) {
				matcher.add(i);
			}
			return;
		}
		StringTokenizer token = new StringTokenizer(pattern, ",");
		if(token != null && token.countTokens() > 1) {
			while (token.hasMoreTokens()) {
				buildValueMatcher(matcher, token.nextToken().trim(), parser);
			}
			return;
		}
		StringTokenizer patternTokanizer = new StringTokenizer(pattern, "/");
		int countTokens = patternTokanizer.countTokens();
		if(countTokens < 1 || countTokens > 2) {
			throw new RuntimeException(String.format("Invalid pattern \"%s\"", this.pattern));
		}
		String patternV1String = patternTokanizer.nextToken();
		List<Integer> values = this.buildValueMatcherRange(patternV1String, parser);
		if(countTokens == 2) {
			String patternV2String = patternTokanizer.nextToken();
			int patternV2Integer = parser.parse(patternV2String);
			if(patternV2Integer < 1) {
				throw new RuntimeException(String.format("Invalid pattern \"%s\".", pattern));
			}
			List<Integer> values2 = new ArrayList<Integer>();
			for (int i = 0; i < values.size(); i += patternV2Integer) {
				values2.add(values.get(i));
			}
			values = values2;
		}
		if(values == null || values.size() == 0) {
			throw new RuntimeException(String.format("Invalid pattern \"%s\".", pattern));
		}
		matcher.addAll(values);
	}

	private List<Integer> buildValueMatcherRange(String pattern, IParser parser) throws Exception {
		List<Integer> values = new ArrayList<Integer>();
		if(pattern.equals("*")) {
			for (int i = parser.getMinValue(), l = parser.getMaxValue(); i <= l; i++) {
				values.add(i);
			}
			return values;
		}
		StringTokenizer patternTokanizer = new StringTokenizer(pattern, "-");
		int countTokens = patternTokanizer.countTokens();
		if(countTokens < 1 || countTokens > 2) {
			throw new RuntimeException(String.format("Invalid pattern \"%s\"", this.pattern));
		}
		String patternV1String = patternTokanizer.nextToken();
		int patternV1Integer = parser.parse(patternV1String);
		if(countTokens == 1) {
			values.add(patternV1Integer);
			return values;
		}
		String patternV2String = patternTokanizer.nextToken();
		int patternV2Integer = parser.parse(patternV2String);
		if(patternV1Integer < patternV2Integer) {
			for (int i = patternV1Integer; i <= patternV2Integer; i++) {
				values.add(Integer.valueOf(i));
			}
		} else if(patternV1Integer > patternV2Integer) {
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
		GregorianCalendar calendar = new GregorianCalendar(timezone);
		calendar.setTimeInMillis(millis);
		return this.minute.match(calendar.get(Calendar.MINUTE)) //
			&& this.hour.match(calendar.get(Calendar.HOUR_OF_DAY)) //
			&& this.day.match(calendar.get(Calendar.DAY_OF_MONTH)) //
			&& this.month.match((calendar.get(Calendar.MONTH) + 1)) //
			&& this.week.match(calendar.get(Calendar.DAY_OF_WEEK));
	}

	public boolean match(long millis) {
		return match(TimeZone.getDefault(), millis);
	}

	@Override
	public String toString() {
		return this.pattern;
	}

	public static List<Parser> build(String patterns) {
		if(StringUtil.isBlank(patterns)) {
			throw new RuntimeException("Invalid patterns. Error parsing Blank. ");
		}
		StringTokenizer patternsToken = new StringTokenizer(patterns, "|");
		List<Parser> result = new ArrayList<>();
		while (patternsToken != null && patternsToken.hasMoreTokens()) {
			result.add(new Parser(patternsToken.nextToken()));
		}
		return result;
	}
}
