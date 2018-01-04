/**
 * Created the sn.mini.jsp.parser.ParseException.java
 * @created 2017年11月28日 下午2:38:08
 * @version 1.0.0
 */
package sn.mini.jsp.compiler;

/**
 * sn.mini.jsp.parser.ParseException.java
 * @author XChao
 */
public class ParseException extends Exception {
	private static final long serialVersionUID = 1L;

	public ParseException(String message) {
		super(message);
	}

	public ParseException(Throwable throwable) {
		super(throwable);
	}

	public ParseException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
