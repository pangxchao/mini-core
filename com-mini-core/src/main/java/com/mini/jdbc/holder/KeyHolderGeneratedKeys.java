package com.mini.jdbc.holder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyHolderGeneratedKeys implements KeyHolder {
	private Object value;

	@Override
	public void set(ResultSet rs) throws SQLException {
		if (rs != null && rs.next()) {
			value = rs.getLong(1);
		}
	}

	@Override
	public Object get() {
		return value;
	}
}
