package com.mini.dao.template;

import com.mini.dao.IDao;
import com.mini.dao.IDaoTemplate;
import com.mini.dao.implement.OracleDao;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.SQLException;

public class OracleDaoTemplate extends IDaoTemplate {

    public OracleDaoTemplate(@Nonnull DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public final IDao getDao() throws SQLException {
        return new OracleDao(getDataSource());
    }
}
