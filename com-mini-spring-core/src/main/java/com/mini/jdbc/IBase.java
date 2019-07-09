package com.mini.jdbc;

import com.mini.jdbc.sql.SQL;
import com.mini.jdbc.template.DaoTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface IBase<T> {

    /**
     * 获取数据写入 IDao 对象
     * @return IDao 对象
     */
    DaoTemplate getWriteTemplate();

    /**
     * 获取数据读取 IDao 对象
     * @return IDao 对象
     */
    DaoTemplate getReadTemplate();

    /**
     * 获取 IMapper 对象
     * @return IMapper 对象
     */
    RowMapper<T> getMapper();

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     */
    default int execute(String sql, Object... params) {
        return getWriteTemplate().execute(sql, params);
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return 执行结果
     */
    default int execute(SQL sql) {
        return getWriteTemplate().execute(sql);
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default List<T> query(String sql, Object... params) {
        return getReadTemplate().query(sql, getMapper(), params);
    }

    /**
     * 查询结果
     * @param sql SQL
     * @return 查询结果
     */
    default List<T> query(SQL sql) {
        return getReadTemplate().query(sql, getMapper());
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default T queryOne(String sql, Object... params) {
        return getReadTemplate().queryOne(sql, getMapper(), params);
    }

    /**
     * 查询结果
     * @param sql SQL
     * @return 查询结果
     */
    default T queryOne(SQL sql) {
        return getReadTemplate().queryOne(sql, getMapper());
    }

    /**
     * 查询String
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default String queryString(String sql, Object... params) {
        return getReadTemplate().queryString(sql, params);
    }

    /**
     * 查询String
     * @param sql SQL
     * @return 查询结果
     */
    default String queryString(SQL sql) {
        return getReadTemplate().queryString(sql);
    }

    /**
     * 查询Long
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default long queryLong(String sql, Object... params) {
        return getReadTemplate().queryLong(sql, params);
    }

    /**
     * 查询Long
     * @param sql SQL
     * @return 查询结果
     */
    default long queryLong(SQL sql) {
        return getReadTemplate().queryLong(sql);
    }

    /**
     * 查询Integer
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default int queryInt(String sql, Object... params) {
        return getReadTemplate().queryInt(sql, params);
    }


    /**
     * 查询Integer
     * @param sql SQL
     * @return 查询结果
     */
    default int queryInt(SQL sql) {
        return getReadTemplate().queryInt(sql);
    }


    /**
     * 查询Short
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default short queryShort(String sql, Object... params) {
        return getReadTemplate().queryShort(sql, params);
    }

    /**
     * 查询Short
     * @param sql SQL
     * @return 查询结果
     */
    default short queryShort(SQL sql) {
        return getReadTemplate().queryShort(sql);
    }


    /**
     * 查询Byte
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default byte queryByte(String sql, Object... params) {
        return getReadTemplate().queryByte(sql, params);
    }

    /**
     * 查询Byte
     * @param sql SQL
     * @return 查询结果
     */
    default byte queryByte(SQL sql) {
        return getReadTemplate().queryByte(sql);
    }

    /**
     * 查询Double
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default double queryDouble(String sql, Object... params) {
        return getReadTemplate().queryDouble(sql, params);
    }

    /**
     * 查询Double
     * @param sql SQL
     * @return 查询结果
     */
    default double queryDouble(SQL sql) {
        return getReadTemplate().queryDouble(sql);
    }

    /**
     * 查询Float
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default float queryFloat(String sql, Object... params) {
        return getReadTemplate().queryFloat(sql, params);
    }

    /**
     * 查询Float
     * @param sql SQL
     * @return 查询结果
     */
    default float queryFloat(SQL sql) {
        return getReadTemplate().queryFloat(sql);
    }

    /**
     * 查询Boolean
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default boolean queryBoolean(String sql, Object... params) {
        return getReadTemplate().queryBoolean(sql, params);
    }

    /**
     * 查询Boolean
     * @param sql SQL
     * @return 查询结果
     */
    default boolean queryBoolean(SQL sql) {
        return getReadTemplate().queryBoolean(sql);
    }

    /**
     * 查询Timestamp
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Timestamp queryTimestamp(String sql, Object... params) {
        return getReadTemplate().queryTimestamp(sql, params);
    }

    /**
     * 查询Timestamp
     * @param sql SQL
     * @return 查询结果
     */
    default Timestamp queryTimestamp(SQL sql) {
        return getReadTemplate().queryTimestamp(sql);
    }

    /**
     * 查询Date
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Date queryDate(String sql, Object... params) {
        return getReadTemplate().queryDate(sql, params);
    }

    /**
     * 查询Date
     * @param sql SQL
     * @return 查询结果
     */
    default Date queryDate(SQL sql) {
        return getReadTemplate().queryDate(sql);
    }

    /**
     * 查询Time
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Time queryTime(String sql, Object... params) {
        return getReadTemplate().queryTime(sql, params);
    }

    /**
     * 查询Time
     * @param sql SQL
     * @return 查询结果
     */
    default Time queryTime(SQL sql) {
        return getReadTemplate().queryTime(sql);
    }

    /**
     * 查询结果
     * @param paging paging 分页器
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default List<T> query(Paging paging, String sql, Object... params) {
        return getReadTemplate().query(paging, sql, getMapper(), params);
    }

    /**
     * 查询结果
     * @param paging 分页器
     * @param sql    SQL
     * @return 查询结果
     */
    default List<T> query(Paging paging, SQL sql) {
        return getReadTemplate().query(paging, sql, getMapper());
    }

    /**
     * 添加实体信息
     * @param info 实体信息
     */
    default int insert(T info) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除用户信息
     * @param info 用户信息
     */
    default int delete(T info) {
        throw new UnsupportedOperationException();
    }


    /**
     * 修改实体信息
     * @param info 用户信息
     */
    default int update(T info) {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据ID删除数据
     * @param id 参数ID
     */
    default int deleteById(long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据ID 查询实体信息
     * @param id 参数ID
     * @return 实体信息
     */
    default T queryById(long id) {
        throw new UnsupportedOperationException();
    }
}
