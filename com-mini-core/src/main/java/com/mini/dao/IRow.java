package com.mini.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface IRow<T> {
    /**
     * 字段映射器
     * @param rs    结果集
     * @param index 字段索引
     * @return 获取数据结果
     * @throws SQLException 数据获取异常
     */
    T execute(ResultSet rs, int index) throws SQLException;
}
