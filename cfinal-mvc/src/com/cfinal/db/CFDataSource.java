/**
 * Created the com.cfinal.db.CFDataSource.java
 * @created 2017年2月23日 下午11:35:13
 * @version 1.0.0
 */
package com.cfinal.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * com.cfinal.db.CFDataSource.java
 * @author XChao
 */
public class CFDataSource implements DataSource {
	private DataSource dataSource;
	private String jndiname;

	private synchronized DataSource getDataSource() {
		try {
			if(dataSource == null || jndiname == null || "".equals(jndiname)) {
				InitialContext context = new InitialContext();
				dataSource = (DataSource) context.lookup(jndiname);
			}
			return dataSource;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the jndiname
	 */
	public String getJndiname() {
		return jndiname;
	}

	/**
	 * @param jndiname the jndiname to set
	 */
	public void setJndiname(String jndiname) {
		this.jndiname = jndiname;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		getDataSource().setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		getDataSource().setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return getDataSource().getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return getDataSource().unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return getDataSource().isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getDataSource().getConnection(username, password);
	}
}
