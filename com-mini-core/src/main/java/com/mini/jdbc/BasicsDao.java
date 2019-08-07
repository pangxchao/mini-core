package com.mini.jdbc;

import com.mini.jdbc.mapper.IMapper;
import com.mini.jdbc.util.Paging;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface BasicsDao<T> {
    /**
     * 获取数据写入 DaoTemplate 对象
     * @return DaoTemplate 对象
     */
    JdbcTemplate writeTemplate();

    /**
     * 获取数据读取 DaoTemplate 对象
     * @return DaoTemplate 对象
     */
    JdbcTemplate readTemplate();


    /**
     * 获取 IMapper 对象
     * @return IMapper 对象
     */
    IMapper<T> getMapper();

    /**
     * 执行SQL
     * @param str    SQL
     * @param params 参数
     * @return 执行结果
     */
    default int execute(String str, Object... params) {
        return writeTemplate().execute(str, params);
    }

    /**
     * 执行SQL
     * @param builder SQLBuilder
     * @return 执行结果
     */
    default int execute(SQLBuilder builder) {
        return writeTemplate().execute(builder);
    }

    /**
     * 查询结果
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default List<T> query(String str, Object... params) {
        return readTemplate().query(str, getMapper(), params);
    }

    /**
     * 查询结果
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default List<T> query(SQLBuilder builder) {
        return readTemplate().query(builder, getMapper());
    }

    /**
     * 查询结果
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default T queryOne(String str, Object... params) {
        return readTemplate().queryOne(str, getMapper(), params);
    }

    /**
     * 查询结果
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default T queryOne(SQLBuilder builder) {
        return readTemplate().queryOne(builder, getMapper());
    }

    /**
     * 查询String
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default String queryString(String str, Object... params) {
        return readTemplate().queryString(str, params);
    }

    /**
     * 查询String
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default String queryString(SQLBuilder builder) {
        return readTemplate().queryString(builder);
    }

    /**
     * 查询Long
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default long queryLong(String str, Object... params) {
        return readTemplate().queryLong(str, params);
    }

    /**
     * 查询Long
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default long queryLong(SQLBuilder builder) {
        return readTemplate().queryLong(builder);
    }

    /**
     * 查询Integer
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default int queryInt(String str, Object... params) {
        return readTemplate().queryInt(str, params);
    }


    /**
     * 查询Integer
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default int queryInt(SQLBuilder builder) {
        return readTemplate().queryInt(builder);
    }


    /**
     * 查询Short
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default short queryShort(String str, Object... params) {
        return readTemplate().queryShort(str, params);
    }

    /**
     * 查询Short
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default short queryShort(SQLBuilder builder) {
        return readTemplate().queryShort(builder);
    }


    /**
     * 查询Byte
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default byte queryByte(String str, Object... params) {
        return readTemplate().queryByte(str, params);
    }

    /**
     * 查询Byte
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default byte queryByte(SQLBuilder builder) {
        return readTemplate().queryByte(builder);
    }

    /**
     * 查询Double
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default double queryDouble(String str, Object... params) {
        return readTemplate().queryDouble(str, params);
    }

    /**
     * 查询Double
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default double queryDouble(SQLBuilder builder) {
        return readTemplate().queryDouble(builder);
    }

    /**
     * 查询Float
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default float queryFloat(String str, Object... params) {
        return readTemplate().queryFloat(str, params);
    }

    /**
     * 查询Float
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default float queryFloat(SQLBuilder builder) {
        return readTemplate().queryFloat(builder);
    }

    /**
     * 查询Boolean
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default boolean queryBoolean(String str, Object... params) {
        return readTemplate().queryBoolean(str, params);
    }

    /**
     * 查询Boolean
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default boolean queryBoolean(SQLBuilder builder) {
        return readTemplate().queryBoolean(builder);
    }

    /**
     * 查询Timestamp
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Timestamp queryTimestamp(String str, Object... params) {
        return readTemplate().queryTimestamp(str, params);
    }

    /**
     * 查询Timestamp
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default Timestamp queryTimestamp(SQLBuilder builder) {
        return readTemplate().queryTimestamp(builder);
    }

    /**
     * 查询Date
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Date queryDate(String str, Object... params) {
        return readTemplate().queryDate(str, params);
    }

    /**
     * 查询Date
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default Date queryDate(SQLBuilder builder) {
        return readTemplate().queryDate(builder);
    }

    /**
     * 查询Time
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Time queryTime(String str, Object... params) {
        return readTemplate().queryTime(str, params);
    }

    /**
     * 查询Time
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default Time queryTime(SQLBuilder builder) {
        return readTemplate().queryTime(builder);
    }

    /**
     * 查询结果
     * @param paging paging 分页器
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default List<T> query(Paging paging, String str, Object... params) {
        return readTemplate().query(paging, str, getMapper(), params);
    }

    /**
     * 查询结果
     * @param paging  分页器
     * @param builder SQLBuilder
     * @return 查询结果
     */
    default List<T> query(Paging paging, SQLBuilder builder) {
        return readTemplate().query(paging, builder, getMapper());
    }
}
