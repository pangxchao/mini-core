package com.mini.core.jdbc;

import javax.annotation.Nonnull;

public interface MiniJdbcRepository extends MiniJdbcTemplate {
    /**
     * 新增实体信息
     *
     * @param entity   实体信息
     * @param <Entity> 实体类型
     * @return 实体信息
     */
    @Nonnull
    <Entity> Entity insert(@Nonnull Entity entity);

    /**
     * 修改实体信息
     *
     * @param entity   实体信息
     * @param <Entity> 实体类型
     * @return 实体信息
     */
    @Nonnull
    <Entity> Entity update(@Nonnull Entity entity);
}
