package com.mini.web.test.entity.mapper;

import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.mapper.IMapper;
import com.mini.web.test.entity.Init;

import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * InitMapper.java
 * @author xchao
 */
@Singleton
@Named("initMapper")
public class InitMapper implements IMapper<Init> {
    @Override
    public Init get(ResultSet rs, int number) throws SQLException {
        Init init = new Init();
        // 参数键
        init.setId(rs.getInt(Init.ID));
        // 参数值
        init.setValue(rs.getString(Init.VALUE));
        // 参数说明
        init.setRemarks(rs.getString(Init.REMARKS));
        return init;
    }

    public static void init(SQLBuilder builder) {
        // 参数键
        builder.select(Init.ID);
        // 参数值
        builder.select(Init.VALUE);
        // 参数说明
        builder.select(Init.REMARKS);
        // 表名称
        builder.from(Init.TABLE);
    }
}
