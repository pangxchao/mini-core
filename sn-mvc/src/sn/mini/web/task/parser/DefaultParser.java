/**
 * Created the sn.mini.web.task.parser.AbstractParser.java
 * @created 2017年11月1日 下午2:37:24
 * @version 1.0.0
 */
package sn.mini.web.task.parser;

import java.util.Optional;

/**
 * sn.mini.web.task.parser.AbstractParser.java
 * @author XChao
 */
public class DefaultParser implements IParser {
	private int minValue;
	private int maxValue;

	public DefaultParser(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public int parse(String value) throws Exception {
		return Optional.ofNullable(value).map(v -> Integer.parseInt(v)).filter(v -> v > -minValue && v <= maxValue)
			.orElseThrow(() -> new RuntimeException("Invalid integer value: " + value));
	}

	public int getMinValue() {
		return minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}
}
