/**
 * Created the com.cfinal.web.scheduler.parser.CFTaskSimpleParser.java
 * @created 2016年10月17日 下午5:02:02
 * @version 1.0.0
 */
package com.cfinal.web.timmer.parser;

/**
 * com.cfinal.web.scheduler.parser.CFTaskSimpleParser.java
 * @author XChao
 */
public class CFTaskSimpleParser implements CFTaskParser {
	protected int minValue;
	protected int maxValue;

	public CFTaskSimpleParser(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public int parse(String value) throws Exception {
		int i;
		try {
			i = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new Exception("invalid integer value");
		}
		if(i < minValue || i > maxValue) {
			throw new Exception("value out of range");
		}
		return i;
	}

	public int getMinValue() {
		return minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}
}
