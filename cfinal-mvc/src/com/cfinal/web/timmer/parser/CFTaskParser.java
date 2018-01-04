/**
 * Created the com.cfinal.web.scheduler.parser.CFTaskParser.java
 * @created 2016年10月17日 下午5:01:31
 * @version 1.0.0
 */
package com.cfinal.web.timmer.parser;

/**
 * com.cfinal.web.scheduler.parser.CFTaskParser.java
 * @author XChao
 */
public interface CFTaskParser {
	public int parse(String value) throws Exception;

	public int getMinValue();

	public int getMaxValue();
}
