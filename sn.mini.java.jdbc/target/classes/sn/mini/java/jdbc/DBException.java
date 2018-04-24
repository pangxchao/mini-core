/**
 * Created the sn.mini.dao.DBException.java
 * @created 2017年11月20日 下午4:39:21
 * @version 1.0.0
 */
package sn.mini.java.jdbc;

/**
 * sn.mini.dao.DBException.java
 * @author XChao
 */
public class DBException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DBException(String message) {
		super(message);
	}

	public DBException(Throwable throwable) {
		super(throwable);
	}

	public DBException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
