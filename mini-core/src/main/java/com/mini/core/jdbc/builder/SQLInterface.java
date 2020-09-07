package com.mini.core.jdbc.builder;

import java.io.Serializable;
import java.util.EventListener;

public interface SQLInterface extends Serializable, EventListener {
    SQLInterface sqlInterface = new SQLInterfaceDef();

    /**
     * 根据实例创建“REPLACE INTO”语句
     *
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    <T> void createReplace(SQLBuilder builder, T instance);

    /**
     * 根据类型创建“INSERT INTO”语句
     *
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    <T> void createInsert(SQLBuilder builder, T instance);

    /**
     * 根据类型创建“DELETE”语句
     *
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    <T> void createDelete(SQLBuilder builder, T instance);

    /**
     * 根据类型创建“UPDATE”语句
     *
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    <T> void createUpdate(SQLBuilder builder, T instance);

    /**
     * 根据类型创建“INSERT ON DUPLICATE KEY UPDATE”语句
     *
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    <T> void createInsertOnUpdate(SQLBuilder builder, T instance);

    /**
     * 根据类型创建“SELECT”语句-不带ID条件
     *
     * @param builder [SQLBuilder]
     * @param type    实例类型
     */
    <T> void createSelect(SQLBuilder builder, Class<T> type);

}