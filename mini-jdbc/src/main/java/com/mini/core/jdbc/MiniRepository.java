package com.mini.core.jdbc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface MiniRepository<T, ID> extends PagingAndSortingRepository<T, ID> {

    /**
     * 新增或者修改实体信息
     *
     * @param entity 实体信息
     * @param <S>    实体信息类型
     * @return 新增或者修改后实体信息
     */
    @NotNull
    default <S extends T> S insertOrUpdate(@NotNull S entity) {
        return save(entity);
    }

    /**
     * 根据ID查询实体信息
     *
     * @param id 实体信息ID
     * @return 查询结果
     */
    @Nullable
    default T getById(@NotNull ID id) {
        return findById(id).orElse(null);
    }
}
