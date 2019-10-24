package com.mini.jdbc.mapper;

import com.mini.util.ObjectUtil;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mini.jdbc.util.JdbcUtil.getResultSetValue;

public class IMapperSingle<T> implements IMapper<T> {
    private final Class<T> type;

    public IMapperSingle(Class<T> type) {
        this.type = type;
    }

    @Nonnull
    @Override
    public T get(ResultSet rs, int number) throws SQLException {
        ObjectUtil.require(rs.getMetaData().getColumnCount() == 1);
        return type.cast(getResultSetValue(rs, 1, type));
    }
}
