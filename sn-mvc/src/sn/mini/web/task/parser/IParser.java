/**
 * Created the sn.mini.web.task.parser.IParser.java
 * @created 2017年11月1日 上午11:06:55
 * @version 1.0.0
 */
package sn.mini.web.task.parser;

/**
 * sn.mini.web.task.parser.IParser.java
 * @author XChao
 */
public interface IParser {

	public int parse(String value) throws Exception;

	public int getMinValue();

	public int getMaxValue();

}
