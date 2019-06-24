package com.mini.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface IMapper<T> {
    /**
     * 结果集映射器
     * @param rs     结果集
     * @param number Row Number
     * @return 解析结果
     * @throws SQLException 转换错误
     */
    T execute(ResultSet rs, int number) throws SQLException;
}
