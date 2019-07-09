package com.mini.jdbc.transaction;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Iterator;
import java.util.List;

import static com.mini.jdbc.util.JdbcUtil.getConnection;
import static com.mini.jdbc.util.JdbcUtil.releaseConnection;

/**
 * JDBC 事务实现
 * @author xchao
 */
@Singleton
public final class TransactionalManagerJdbc implements TransactionalManager {
    private List<DataSource> dataSourceList;

    /**
     * The value of dataSourceList
     * @param dataSourceList The value of dataSourceList
     */
    @Inject
    public void setDataSourceList(List<DataSource> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }

    @Override
    public <T> T open(TransactionalManagerCallback<T> callback) throws Throwable {
        if (dataSourceList == null) return callback.apply();
        return open(dataSourceList.iterator(), callback);
    }

    private <T> T open(Iterator<DataSource> iterator, TransactionalManagerCallback<T> callback) throws Throwable {
        if (!iterator.hasNext()) return callback.apply();
        Connection connection = null;
        DataSource dataSource = null;
        try {
            // 获取连接和连接池
            dataSource = iterator.next();
            connection = getConnection(dataSource);

            // 字义回滚点
            Savepoint point = null;
            try {
                // 设置不自动提交，并设置回调点
                connection.setAutoCommit(false);
                point = connection.setSavepoint();

                // 开启下一个数据连接池的事务
                T result = open(iterator, callback);

                // 提交事务并返回
                connection.commit();
                return result;
            } catch (SQLException e) {
                if (point == null) {
                    connection.rollback();
                } else connection.rollback(point);
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } finally {
            releaseConnection(connection, dataSource);
        }
    }
}
