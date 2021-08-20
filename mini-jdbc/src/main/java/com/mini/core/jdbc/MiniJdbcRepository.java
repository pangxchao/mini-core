package com.mini.core.jdbc;

import javax.annotation.Nonnull;
import java.io.Serializable;

public interface MiniJdbcRepository extends MiniJdbcTemplate {
    /**
     * 新增实体信息
     *
     * @param entity   实体信息
     * @param <Entity> 实体类型
     * @return 实体信息
     */
    @Nonnull
    <Entity extends Serializable> Entity insert(@Nonnull Entity entity);

    /**
     * 修改实体信息
     *
     * @param entity   实体信息
     * @param <Entity> 实体类型
     * @return 实体信息
     */
    @Nonnull
    <Entity extends Serializable> Entity update(@Nonnull Entity entity);
}
