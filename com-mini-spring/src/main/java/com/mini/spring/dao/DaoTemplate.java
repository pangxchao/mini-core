package com.mini.spring.dao;

import com.mini.util.dao.IMapper;
import com.mini.util.dao.Paging;
import com.mini.util.dao.SQL;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public abstract class DaoTemplate extends JdbcTemplate {
    public DaoTemplate(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     */
    public int execute(String sql, Object... params) {
        return this.update(sql, params);
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return 执行结果
     */
    public int execute(SQL sql) {
        return execute(sql.content(), sql.params());
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public <T> List<T> query(String sql, IMapper<T> mapper, Object... params) {
        return this.query(sql, params, mapper::execute);
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public <T> List<T> query(SQL sql, IMapper<T> mapper) {
        return query(sql.content(), mapper, sql.params());
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public <T> T queryOne(String sql, IMapper<T> mapper, Object... params) {
        return this.queryForObject(sql, params, mapper::execute);
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public <T> T queryOne(SQL sql, IMapper<T> mapper) {
        return queryOne(sql.content(), mapper, sql.params());
    }

    /**
     * 查询String
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public String queryString(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getString(1), params);
    }

    /**
     * 查询String
     * @param sql SQL
     * @return 查询结果
     */
    public String queryString(SQL sql) {
        return queryString(sql.content(), sql.params());
    }

    /**
     * 查询Long
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public long queryLong(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getLong(1), params);
    }

    /**
     * 查询Long
     * @param sql SQL
     * @return 查询结果
     */
    public long queryLong(SQL sql) {
        return queryLong(sql.content(), sql.params());
    }

    /**
     * 查询Integer
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public int queryInt(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getInt(1), params);
    }


    /**
     * 查询Integer
     * @param sql SQL
     * @return 查询结果
     */
    public int queryInt(SQL sql) {
        return queryInt(sql.content(), sql.params());
    }


    /**
     * 查询Short
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public short queryShort(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getShort(1), params);
    }

    /**
     * 查询Short
     * @param sql SQL
     * @return 查询结果
     */
    public short queryShort(SQL sql) {
        return queryShort(sql.content(), sql.params());
    }


    /**
     * 查询Byte
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public byte queryByte(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getByte(1), params);
    }

    /**
     * 查询Byte
     * @param sql SQL
     * @return 查询结果
     */
    public byte queryByte(SQL sql) {
        return queryByte(sql.content(), sql.params());
    }

    /**
     * 查询Double
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public double queryDouble(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getDouble(1), params);
    }

    /**
     * 查询Double
     * @param sql SQL
     * @return 查询结果
     */
    public double queryDouble(SQL sql) {
        return queryDouble(sql.content(), sql.params());
    }

    /**
     * 查询Float
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public float queryFloat(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getFloat(1), params);
    }

    /**
     * 查询Float
     * @param sql SQL
     * @return 查询结果
     */
    public float queryFloat(SQL sql) {
        return queryFloat(sql.content(), sql.params());
    }

    /**
     * 查询Boolean
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public boolean queryBoolean(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getBoolean(1), params);
    }

    /**
     * 查询Boolean
     * @param sql SQL
     * @return 查询结果
     */
    public boolean queryBoolean(SQL sql) {
        return queryBoolean(sql.content(), sql.params());
    }

    /**
     * 查询Timestamp
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public Timestamp queryTimestamp(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getTimestamp(1), params);
    }

    /**
     * 查询Timestamp
     * @param sql SQL
     * @return 查询结果
     */
    public Timestamp queryTimestamp(SQL sql) {
        return queryTimestamp(sql.content(), sql.params());
    }

    /**
     * 查询Date
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public Date queryDate(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getDate(1), params);
    }

    /**
     * 查询Date
     * @param sql SQL
     * @return 查询结果
     */
    public Date queryDate(SQL sql) {
        return queryDate(sql.content(), sql.params());
    }

    /**
     * 查询Time
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public Time queryTime(String sql, Object... params) {
        return queryOne(sql, (rs, n) -> rs.getTime(1), params);
    }

    /**
     * 查询Time
     * @param sql SQL
     * @return 查询结果
     */
    public Time queryTime(SQL sql) {
        return queryTime(sql.content(), sql.params());
    }


    /**
     * 查询结果
     * @param paging paging 分页器
     * @param sql    SQL
     * @param row    解析器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public <T> List<T> query(Paging paging, String sql, IMapper<T> row, Object... params) {
        paging.setTotal(this.queryInt(this.totals(sql), params));
        return query(paging(paging, sql), row, params);
    }

    /**
     * 查询结果
     * @param paging 分页器
     * @param sql    SQL
     * @param row    解析器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public <T> List<T> query(Paging paging, SQL sql, IMapper<T> row) {
        return query(paging, sql.content(), row, sql.params());
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
}
