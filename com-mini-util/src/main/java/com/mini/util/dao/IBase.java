package com.mini.util.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface IBase<T> {

    IDao getWriteDao();

    IDao getReadDao();

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     */
    default int execute(String sql, Object... params) throws SQLException {
        return getWriteDao().execute(sql, params);
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return 执行结果
     */
    default int execute(SQL sql) throws SQLException {
        return getWriteDao().execute(sql);
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @return 查询结果
     */
    default List<T> query(String sql, IMapper<T> mapper, Object... params) throws SQLException {
        return getReadDao().query(sql, mapper, params);
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @return 查询结果
     */
    default List<T> query(SQL sql, IMapper<T> mapper) throws SQLException {
        return getReadDao().query(sql, mapper);
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @return 查询结果
     */
    default T queryOne(String sql, IMapper<T> mapper, Object... params) throws SQLException {
        return getReadDao().queryOne(sql, mapper, params);
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @return 查询结果
     */
    default T queryOne(SQL sql, IMapper<T> mapper) throws SQLException {
        return getReadDao().queryOne(sql, mapper);
    }

    /**
     * 查询String
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default String queryString(String sql, Object... params) throws SQLException {
        return getReadDao().queryString(sql, params);
    }

    /**
     * 查询String
     * @param sql SQL
     * @return 查询结果
     */
    default String queryString(SQL sql) throws SQLException {
        return getReadDao().queryString(sql);
    }

    /**
     * 查询Long
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default long queryLong(String sql, Object... params) throws SQLException {
        return getReadDao().queryLong(sql, params);
    }

    /**
     * 查询Long
     * @param sql SQL
     * @return 查询结果
     */
    default long queryLong(SQL sql) throws SQLException {
        return getReadDao().queryLong(sql);
    }

    /**
     * 查询Integer
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default int queryInt(String sql, Object... params) throws SQLException {
        return getReadDao().queryInt(sql, params);
    }


    /**
     * 查询Integer
     * @param sql SQL
     * @return 查询结果
     */
    default int queryInt(SQL sql) throws SQLException {
        return getReadDao().queryInt(sql);
    }


    /**
     * 查询Short
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default short queryShort(String sql, Object... params) throws SQLException {
        return getReadDao().queryShort(sql, params);
    }

    /**
     * 查询Short
     * @param sql SQL
     * @return 查询结果
     */
    default short queryShort(SQL sql) throws SQLException {
        return getReadDao().queryShort(sql);
    }


    /**
     * 查询Byte
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default byte queryByte(String sql, Object... params) throws SQLException {
        return getReadDao().queryByte(sql, params);
    }

    /**
     * 查询Byte
     * @param sql SQL
     * @return 查询结果
     */
    default byte queryByte(SQL sql) throws SQLException {
        return getReadDao().queryByte(sql);
    }

    /**
     * 查询Double
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default double queryDouble(String sql, Object... params) throws SQLException {
        return getReadDao().queryDouble(sql, params);
    }

    /**
     * 查询Double
     * @param sql SQL
     * @return 查询结果
     */
    default double queryDouble(SQL sql) throws SQLException {
        return getReadDao().queryDouble(sql);
    }

    /**
     * 查询Float
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default float queryFloat(String sql, Object... params) throws SQLException {
        return getReadDao().queryFloat(sql, params);
    }

    /**
     * 查询Float
     * @param sql SQL
     * @return 查询结果
     */
    default float queryFloat(SQL sql) throws SQLException {
        return getReadDao().queryFloat(sql);
    }

    /**
     * 查询Boolean
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default boolean queryBoolean(String sql, Object... params) throws SQLException {
        return getReadDao().queryBoolean(sql, params);
    }

    /**
     * 查询Boolean
     * @param sql SQL
     * @return 查询结果
     */
    default boolean queryBoolean(SQL sql) throws SQLException {
        return getReadDao().queryBoolean(sql);
    }

    /**
     * 查询Timestamp
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Timestamp queryTimestamp(String sql, Object... params) throws SQLException {
        return getReadDao().queryTimestamp(sql, params);
    }

    /**
     * 查询Timestamp
     * @param sql SQL
     * @return 查询结果
     */
    default Timestamp queryTimestamp(SQL sql) throws SQLException {
        return getReadDao().queryTimestamp(sql);
    }

    /**
     * 查询Date
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Date queryDate(String sql, Object... params) throws SQLException {
        return getReadDao().queryDate(sql, params);
    }

    /**
     * 查询Date
     * @param sql SQL
     * @return 查询结果
     */
    default Date queryDate(SQL sql) throws SQLException {
        return getReadDao().queryDate(sql);
    }

    /**
     * 查询Time
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Time queryTime(String sql, Object... params) throws SQLException {
        return getReadDao().queryTime(sql, params);
    }

    /**
     * 查询Time
     * @param sql SQL
     * @return 查询结果
     */
    default Time queryTime(SQL sql) throws SQLException {
        return getReadDao().queryTime(sql);
    }


    /**
     * 查询结果
     * @param paging paging 分页器
     * @param sql    SQL
     * @param mapper 解析器
     * @param params 参数
     * @return 查询结果
     */
    default List<T> query(Paging paging, String sql, IMapper<T> mapper, Object... params) throws SQLException {
        return getReadDao().query(paging, sql, mapper, params);
    }

    /**
     * 查询结果
     * @param paging 分页器
     * @param sql    SQL
     * @param mapper 解析器
     * @return 查询结果
     */
    default List<T> query(Paging paging, SQL sql, IMapper<T> mapper) throws SQLException {
        return getReadDao().query(paging, sql, mapper);
    }

    /**
     * 添加实体信息
     * @param info 实体信息
     */
    void insert(T info)  throws SQLException;

    /**
     * 删除用户信息
     * @param info 用户信息
     */
    void delete(T info)  throws SQLException;


    /**
     * 修改实体信息
     * @param info 用户信息
     */
    void update(T info)  throws SQLException;
}
