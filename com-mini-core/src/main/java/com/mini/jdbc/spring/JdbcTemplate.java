package com.mini.jdbc.spring;

import com.mini.jdbc.sql.SQL;
import com.mini.jdbc.util.Paging;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Nonnull;
import java.sql.*;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public abstract class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate {

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     */
    public final int execute(String sql, Object... params) {
        JtaTransactionManager a;
        javax.transaction.UserTransaction l;
        // javax.enterprise.context.NormalScope
        return this.update(sql, params);
    }

    /**
     * 执行SQL
     * @param holder 拥有者
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     */
    public final int execute(KeyHolder holder, String sql, Object... params) {
        return this.update(new GeneratedKeyCreator(sql, params), holder);
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return 执行结果
     */
    public final int execute(SQL sql) {
        return execute(sql.content(), sql.params());
    }

    /**
     * 执行SQL
     * @param holder 拥有者
     * @param sql    SQL
     * @return 执行结果
     */
    public final int execute(KeyHolder holder, SQL sql) {
        return execute(holder, sql.content(), sql.params());
    }

    /**
     * 查询结果
     * @param sql       SQL
     * @param rowMapper 映射器
     * @param params    参数
     * @param <T>       解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(@Nonnull String sql, RowMapper<T> rowMapper, Object... params) {
        return this.query(sql, params, rowMapper);
    }


    /**
     * 查询结果
     * @param sql       SQL
     * @param rowMapper 映射器
     * @param <T>       解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(SQL sql, RowMapper<T> rowMapper) {
        return query(sql.content(), rowMapper, sql.params());
    }


    /**
     * 查询结果
     * @param sql       SQL
     * @param rowMapper 映射器
     * @param params    参数
     * @param <T>       解析器类型
     * @return 查询结果
     */
    public final <T> T queryOne(String sql, RowMapper<T> rowMapper, Object... params) {
        return this.queryForObject(sql, params, rowMapper);
    }

    /**
     * 查询结果
     * @param sql       SQL
     * @param rowMapper 映射器
     * @param <T>       解析器类型
     * @return 查询结果
     */
    public final <T> T queryOne(SQL sql, RowMapper<T> rowMapper) {
        return queryOne(sql.content(), rowMapper, sql.params());
    }

    /**
     * 查询String
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final String queryString(String sql, Object... params) {
        return queryForObject(sql, params, String.class);
    }

    /**
     * 查询String
     * @param sql SQL
     * @return 查询结果
     */
    public final String queryString(SQL sql) {
        return queryString(sql.content(), sql.params());
    }

    /**
     * 查询Long
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Long queryLong(String sql, Object... params) {
        return queryForObject(sql, params, Long.class);
    }

    /**
     * 查询Long
     * @param sql SQL
     * @return 查询结果
     */
    public final Long queryLong(SQL sql) {
        return queryLong(sql.content(), sql.params());
    }

    /**
     * 查询Integer
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Integer queryInt(String sql, Object... params) {
        return queryForObject(sql, params, Integer.class);
    }


    /**
     * 查询Integer
     * @param sql SQL
     * @return 查询结果
     */
    public final Integer queryInt(SQL sql) {
        return queryInt(sql.content(), sql.params());
    }


    /**
     * 查询Short
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Short queryShort(String sql, Object... params) {
        return queryForObject(sql, params, Short.class);
    }

    /**
     * 查询Short
     * @param sql SQL
     * @return 查询结果
     */
    public final Short queryShort(SQL sql) {
        return queryShort(sql.content(), sql.params());
    }


    /**
     * 查询Byte
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Byte queryByte(String sql, Object... params) {
        return queryForObject(sql, params, Byte.class);
    }

    /**
     * 查询Byte
     * @param sql SQL
     * @return 查询结果
     */
    public final Byte queryByte(SQL sql) {
        return queryByte(sql.content(), sql.params());
    }

    /**
     * 查询Double
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Double queryDouble(String sql, Object... params) {
        return queryForObject(sql, params, Double.class);
    }

    /**
     * 查询Double
     * @param sql SQL
     * @return 查询结果
     */
    public final Double queryDouble(SQL sql) {
        return queryDouble(sql.content(), sql.params());
    }

    /**
     * 查询Float
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Float queryFloat(String sql, Object... params) {
        return queryForObject(sql, params, Float.class);
    }

    /**
     * 查询Float
     * @param sql SQL
     * @return 查询结果
     */
    public final Float queryFloat(SQL sql) {
        return queryFloat(sql.content(), sql.params());
    }

    /**
     * 查询Boolean
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Boolean queryBoolean(String sql, Object... params) {
        return queryForObject(sql, params, Boolean.class);
    }

    /**
     * 查询Boolean
     * @param sql SQL
     * @return 查询结果
     */
    public final Boolean queryBoolean(SQL sql) {
        return queryBoolean(sql.content(), sql.params());
    }

    /**
     * 查询Timestamp
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Timestamp queryTimestamp(String sql, Object... params) {
        return queryForObject(sql, params, Timestamp.class);
    }

    /**
     * 查询Timestamp
     * @param sql SQL
     * @return 查询结果
     */
    public final Timestamp queryTimestamp(SQL sql) {
        return queryTimestamp(sql.content(), sql.params());
    }

    /**
     * 查询Date
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Date queryDate(String sql, Object... params) {
        return queryForObject(sql, params, Date.class);
    }

    /**
     * 查询Date
     * @param sql SQL
     * @return 查询结果
     */
    public final Date queryDate(SQL sql) {
        return queryDate(sql.content(), sql.params());
    }

    /**
     * 查询Time
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Time queryTime(String sql, Object... params) {
        return queryForObject(sql, params, Time.class);
    }

    /**
     * 查询Time
     * @param sql SQL
     * @return 查询结果
     */
    public final Time queryTime(SQL sql) {
        return queryTime(sql.content(), sql.params());
    }

    /**
     * 查询结果
     * @param paging    paging 分页器
     * @param sql       SQL
     * @param rowMapper 解析器
     * @param params    参数
     * @param <T>       解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(Paging paging, String sql, RowMapper<T> rowMapper, Object... params) {
        paging.setTotal(this.queryInt(this.totals(sql), params));
        return query(paging(paging, sql), rowMapper, params);
    }

    /**
     * 查询结果
     * @param paging    分页器
     * @param sql       SQL
     * @param rowMapper 解析器
     * @param <T>       解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(Paging paging, SQL sql, RowMapper<T> rowMapper) {
        return query(paging, sql.content(), rowMapper, sql.params());
    }

    /**
     * 返回查询分页总条数的SQL
     * @param sql SQL
     * @return 查询总条数的SQL
     */
    protected abstract String totals(String sql);

    /**
     * 根据分页参数，组装分页查询SQL
     * @param paging 分页参数
     * @param sql    基础查询SQL
     * @return 分页查询SQL
     */
    protected abstract String paging(Paging paging, String sql);

    /**
     * 自动获取自增主键创建器
     * @author xchao
     */
    private static class GeneratedKeyCreator implements PreparedStatementCreator {
        private final Object[] params;
        private final String sql;

        public GeneratedKeyCreator(String sql, Object[] params) {
            this.params = params;
            this.sql    = sql;
        }

        @Nonnull
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement statement = con.prepareStatement(sql, RETURN_GENERATED_KEYS);
            for (int i = 0; params != null && i < params.length; i++) {
                statement.setObject((i + 1), params[i]);
            }
            return statement;
        }
    }
}
