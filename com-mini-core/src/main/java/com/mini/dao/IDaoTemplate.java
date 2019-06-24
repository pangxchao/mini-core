package com.mini.dao;

import com.mini.dao.sql.SQLBuilder;
import com.mini.util.Function;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public abstract class IDaoTemplate {
    @Nonnull
    private final DataSource dataSource;

    public IDaoTemplate(@Nonnull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Gets the value of dataSource.
     * @return The value of dataSource
     */
    @Nonnull
    public final DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 获取Dao连接
     * @return dao
     * @throws SQLException 执行错误
     */
    public abstract IDao getDao() throws SQLException;


    /**
     * 批量操作
     * @param sql    SQL
     * @param length 数据长度
     * @param batch  回调接口
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    public final int[] batch(String sql, int length, Function.FR1<Object[], Integer> batch) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.batch(sql, length, batch);
        }
    }

    /**
     * 批量操作
     * @param sql    SQL
     * @param params 参数
     * @return SQL执行结果
     * @throws SQLException 执行错误
     */
    public final int[] batch(String sql, Object[][] params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.batch(sql, params);
        }
    }

    /**
     * 批量操作
     * @param sql        SQL
     * @param dataLength 数据长度
     * @param batch      回调接口
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    public final int[] batch(SQLBuilder sql, int dataLength, Function.FR1<Object[], Integer> batch) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.batch(sql, dataLength, batch);
        }
    }

    /**
     * 批量操作
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    public final int[] batch(SQLBuilder sql, Object[][] params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.batch(sql, params);
        }
    }

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    public final int execute(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.execute(sql, params);
        }
    }

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return ID
     * @throws SQLException 执行错误
     */
    public final long automatic(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.automatic(sql, params);
        }
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    public final int execute(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.execute(sql);
        }
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return ID
     * @throws SQLException 执行错误
     */
    public final long automatic(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.automatic(sql);
        }
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final ResultSet query(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.query(sql, params);
        }
    }

    /**
     * 查询结果
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final ResultSet query(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.query(sql);
        }
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final <T> List<T> query(String sql, IMapper<T> m, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.query(sql, m, params);
        }
    }

    /**
     * 查询结果
     * @param sql SQL
     * @param m   映射器
     * @param <T> 解析器类型
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final <T> List<T> query(SQL sql, IMapper<T> m) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.query(sql, m);
        }
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final <T> T queryOne(String sql, IMapper<T> m, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryOne(sql, m, params);
        }
    }

    /**
     * 查询结果
     * @param sql SQL
     * @param m   映射器
     * @param <T> 解析器类型
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final <T> T queryOne(SQL sql, IMapper<T> m) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryOne(sql, m);
        }
    }

    /**
     * 查询String
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final String queryString(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryString(sql, params);
        }
    }

    /**
     * 查询String
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final String queryString(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryString(sql);
        }
    }

    /**
     * 查询Long
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final long queryLong(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryLong(sql, params);
        }
    }

    /**
     * 查询Long
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final long queryLong(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryLong(sql);
        }
    }

    /**
     * 查询Integer
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final int queryInt(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryInt(sql, params);
        }
    }


    /**
     * 查询Integer
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final int queryInt(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryInt(sql);
        }
    }


    /**
     * 查询Short
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final short queryShort(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryShort(sql, params);
        }
    }

    /**
     * 查询Short
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final short queryShort(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryShort(sql);
        }
    }


    /**
     * 查询Byte
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final byte queryByte(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryByte(sql, params);
        }
    }

    /**
     * 查询Byte
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final byte queryByte(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryByte(sql);
        }
    }

    /**
     * 查询Double
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final double queryDouble(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryDouble(sql, params);
        }
    }

    /**
     * 查询Double
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final double queryDouble(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryDouble(sql);
        }
    }

    /**
     * 查询Float
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final float queryFloat(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryFloat(sql, params);
        }
    }

    /**
     * 查询Float
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final float queryFloat(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryFloat(sql);
        }
    }

    /**
     * 查询Boolean
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final boolean queryBoolean(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryBoolean(sql, params);
        }
    }

    /**
     * 查询Boolean
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final boolean queryBoolean(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryBoolean(sql);
        }
    }

    /**
     * 查询Timestamp
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final Timestamp queryTimestamp(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryTimestamp(sql, params);
        }
    }

    /**
     * 查询Timestamp
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final Timestamp queryTimestamp(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryTimestamp(sql);
        }
    }

    /**
     * 查询Date
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final Date queryDate(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryDate(sql, params);
        }
    }

    /**
     * 查询Date
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final Date queryDate(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryDate(sql);
        }
    }

    /**
     * 查询Time
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final Time queryTime(String sql, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryTime(sql, params);
        }
    }

    /**
     * 查询Time
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    public final Time queryTime(SQL sql) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.queryTime(sql);
        }
    }


    /**
     * 查询结果
     * @param paging paging 分页器
     * @param sql    SQL
     * @param m      解析器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(Paging paging, String sql, IMapper<T> m, Object... params) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.query(paging, sql, m, params);
        }
    }

    /**
     * 查询结果
     * @param paging 分页器
     * @param sql    SQL
     * @param m      解析器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(Paging paging, SQL sql, IMapper<T> m) throws SQLException {
        try (IDao dao = IDaoTemplate.this.getDao()) {
            return dao.query(paging, sql, m);
        }
    }
}
