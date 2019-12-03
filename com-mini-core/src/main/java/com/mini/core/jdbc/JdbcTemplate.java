package com.mini.core.jdbc;

import com.mini.core.jdbc.mapper.IMapper;
import com.mini.core.jdbc.model.Paging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static com.mini.core.jdbc.util.JdbcUtil.full;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * 数据库操作对象
 * @author xchao
 */
public abstract class JdbcTemplate extends JdbcAccessor implements JdbcInterface {
    private final DataSource dataSource;

    public JdbcTemplate(@Nonnull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Nonnull
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 处理普通的 Connection 对象
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public <T> T execute(ConnectionCallback<T> callback) {
        try (Connection con = getConnection(dataSource)) {
            return callback.apply(con);
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 处理普通的 DatabaseMetaData 对象
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public <T> T execute(DatabaseMetaDataCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) (connection) -> {
            return callback.apply(connection.getMetaData()); //
        });
    }

    /**
     * 处理普通的 PreparedStatement 对象
     * @param creator  创建回调函数
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public <T> T execute(PreparedStatementCreator creator, PreparedStatementCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) (connection) -> {
            return callback.apply(creator.apply(connection)); //
        });
    }

    /**
     * 处理普通的 CallableStatement 对象
     * @param creator  创建回调函数
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public <T> T execute(CallableStatementCreator creator, CallableStatementCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) (connection) -> {
            return callback.apply(creator.apply(connection)); //
        });
    }

    /**
     * 指执行SQL
     * @param str    SQL
     * @param setter 预处理设置器
     * @return 执行结果
     */
    @Override
    public int[] executeBatch(String str, PreparedStatementSetter setter) {
        return this.execute((ConnectionCallback<int[]>) (connection) -> {
            PreparedStatement statement = connection.prepareStatement(str);
            for (int i = 0, size = setter.getBatchSize(); i < size; i++) {
                setter.setValues(statement, i);
                statement.addBatch();
            }
            return statement.executeBatch();
        });
    }

    @Override
    public int execute(String str, Object... params) {
        return execute((PreparedStatementCreator) con -> {
            return con.prepareStatement(str); //
        }, stm -> full(stm, params).executeUpdate());
    }

    @Override
    public int execute(HolderGenerated holder, String str, Object... params) {
        return execute((PreparedStatementCreator) con -> con.prepareStatement( //
                str, RETURN_GENERATED_KEYS), stm -> {
            int result = full(stm, params).executeUpdate();
            try (ResultSet rs = stm.getGeneratedKeys()) {
                holder.setValue(rs);
            }
            return result;
        });
    }

    @Override
    public <T> T query(String str, ResultSetCallback<T> callback, Object... params) {
        return execute((PreparedStatementCreator) con -> con.prepareStatement(str, //
                TYPE_FORWARD_ONLY, CONCUR_READ_ONLY), statement -> {
            try (ResultSet rs = full(statement, params).executeQuery()) {
                return callback.apply(rs);
            }
        });
    }

    /**
     * 返回查询分页总条数的SQL
     * @param str SQL
     * @return 查询总条数的SQL
     */
    @Nonnull
    protected abstract String totals(String str);

    /**
     * 根据分页参数，组装分页查询SQL
     * @param start 查询起始位置
     * @param limit 查询条数
     * @param str   基础查询SQL
     * @return 分页查询SQL
     */
    @Nonnull
    protected abstract String paging(int start, int limit, String str);


    @Nonnull
    @Override
    public final <T> List<T> queryList(int start, int limit, String str, IMapper<T> m, Object... params) {
        return queryList(paging(start, limit, str), m, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(Paging paging, String str, IMapper<T> m, Object... params) {
        paging.setTotal(query(totals(str), (rs -> rs.next() ? rs.getInt(1) : 0), params));
        return queryList(paging(paging.getSkip(), paging.getLimit(), str), m, params);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String str, IMapper<T> m, Object... params) {
        return query(paging(0, 1, str), rs -> rs.next() ? m.get(rs, //
                rs.getRow()) : null, params);
    }

    @FunctionalInterface
    public interface ConnectionCallback<T> {
        T apply(Connection con) throws SQLException;
    }

    @FunctionalInterface
    public interface DatabaseMetaDataCallback<T> {
        T apply(DatabaseMetaData metaData) throws SQLException;
    }

    @FunctionalInterface
    public interface PreparedStatementCreator {
        PreparedStatement apply(Connection con) throws SQLException;
    }

    @FunctionalInterface
    public interface PreparedStatementCallback<T> {
        T apply(PreparedStatement stm) throws SQLException;
    }

    @FunctionalInterface
    public interface CallableStatementCreator {
        CallableStatement apply(Connection con) throws SQLException;
    }

    @FunctionalInterface
    public interface CallableStatementCallback<T> {
        T apply(CallableStatement stm) throws SQLException;
    }
}