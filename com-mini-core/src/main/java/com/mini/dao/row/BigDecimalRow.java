package com.mini.dao.row;

import com.mini.dao.IRow;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class BigDecimalRow implements IRow<BigDecimal> {
    public static final BigDecimalRow INSTANCE = new BigDecimalRow();

    private BigDecimalRow() {}

    @Override
    public BigDecimal execute(ResultSet rs, int index) throws SQLException {
        return rs.getBigDecimal(index);
    }


}
