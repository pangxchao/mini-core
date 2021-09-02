package com.mini.core.jdbc.repository;

import com.mini.core.jdbc.MiniJdbcTemplate;

import java.io.Serializable;

public interface MiniRepository extends MiniJdbcTemplate {

    /**
     * 新增一条记录
     *
     * @param entity 实体对象
     * @return 保存结果
     */
    <E extends Serializable> E insert(E entity);

    /**
     * 修改一条记录
     *
     * @param entity 实体对象
     * @return 删除结果
     */
    <E extends Serializable> E update(E entity);
}
