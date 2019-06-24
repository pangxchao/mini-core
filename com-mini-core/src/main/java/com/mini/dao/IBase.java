package com.mini.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface IBase<T> {

    /**
     * 获取数据写入 IDaoTemplate 对象
     * @return IDaoTemplate 对象
     */
    IDaoTemplate getWriteDaoTemplate();

    /**
     * 获取数据读取 IDaoTemplate 对象
     * @return IDaoTemplate 对象
     */
    IDaoTemplate getReadDaoTemplate();


    /**
     * 获取 IMapper 对象
     * @return IMapper 对象
     */
    IMapper<T> getMapper();

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     */
    default int execute(String sql, Object... params) throws SQLException {
        return getWriteDaoTemplate().execute(sql, params);
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return 执行结果
     */
    default int execute(SQL sql) throws SQLException {
        return getWriteDaoTemplate().execute(sql);
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default List<T> query(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().query(sql, getMapper(), params);
    }

    /**
     * 查询结果
     * @param sql SQL
     * @return 查询结果
     */
    default List<T> query(SQL sql) throws SQLException {
        return getReadDaoTemplate().query(sql, getMapper());
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default T queryOne(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryOne(sql, getMapper(), params);
    }

    /**
     * 查询结果
     * @param sql SQL
     * @return 查询结果
     */
    default T queryOne(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryOne(sql, getMapper());
    }

    /**
     * 查询String
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default String queryString(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryString(sql, params);
    }

    /**
     * 查询String
     * @param sql SQL
     * @return 查询结果
     */
    default String queryString(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryString(sql);
    }

    /**
     * 查询Long
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default long queryLong(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryLong(sql, params);
    }

    /**
     * 查询Long
     * @param sql SQL
     * @return 查询结果
     */
    default long queryLong(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryLong(sql);
    }

    /**
     * 查询Integer
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default int queryInt(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryInt(sql, params);
    }


    /**
     * 查询Integer
     * @param sql SQL
     * @return 查询结果
     */
    default int queryInt(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryInt(sql);
    }


    /**
     * 查询Short
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default short queryShort(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryShort(sql, params);
    }

    /**
     * 查询Short
     * @param sql SQL
     * @return 查询结果
     */
    default short queryShort(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryShort(sql);
    }


    /**
     * 查询Byte
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default byte queryByte(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryByte(sql, params);
    }

    /**
     * 查询Byte
     * @param sql SQL
     * @return 查询结果
     */
    default byte queryByte(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryByte(sql);
    }

    /**
     * 查询Double
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default double queryDouble(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryDouble(sql, params);
    }

    /**
     * 查询Double
     * @param sql SQL
     * @return 查询结果
     */
    default double queryDouble(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryDouble(sql);
    }

    /**
     * 查询Float
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default float queryFloat(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryFloat(sql, params);
    }

    /**
     * 查询Float
     * @param sql SQL
     * @return 查询结果
     */
    default float queryFloat(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryFloat(sql);
    }

    /**
     * 查询Boolean
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default boolean queryBoolean(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryBoolean(sql, params);
    }

    /**
     * 查询Boolean
     * @param sql SQL
     * @return 查询结果
     */
    default boolean queryBoolean(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryBoolean(sql);
    }

    /**
     * 查询Timestamp
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Timestamp queryTimestamp(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryTimestamp(sql, params);
    }

    /**
     * 查询Timestamp
     * @param sql SQL
     * @return 查询结果
     */
    default Timestamp queryTimestamp(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryTimestamp(sql);
    }

    /**
     * 查询Date
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Date queryDate(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryDate(sql, params);
    }

    /**
     * 查询Date
     * @param sql SQL
     * @return 查询结果
     */
    default Date queryDate(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryDate(sql);
    }

    /**
     * 查询Time
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Time queryTime(String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().queryTime(sql, params);
    }

    /**
     * 查询Time
     * @param sql SQL
     * @return 查询结果
     */
    default Time queryTime(SQL sql) throws SQLException {
        return getReadDaoTemplate().queryTime(sql);
    }

    /**
     * 查询结果
     * @param paging paging 分页器
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default List<T> query(Paging paging, String sql, Object... params) throws SQLException {
        return getReadDaoTemplate().query(paging, sql, getMapper(), params);
    }

    /**
     * 查询结果
     * @param paging 分页器
     * @param sql    SQL
     * @return 查询结果
     */
    default List<T> query(Paging paging, SQL sql) throws SQLException {
        return getReadDaoTemplate().query(paging, sql, getMapper());
    }

    /**
     * 添加实体信息
     * @param info 实体信息
     */
    int insert(T info) throws SQLException;

    /**
     * 删除用户信息
     * @param info 用户信息
     */
    int delete(T info) throws SQLException;


    /**
     * 修改实体信息
     * @param info 用户信息
     */
    int update(T info) throws SQLException;

    /**
     * 根据ID删除数据
     * @param id 参数ID
     */
    default int deleteById(long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据ID 查询实体信息
     * @param id 参数ID
     * @return 实体信息
     */
    default T queryById(long id) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
