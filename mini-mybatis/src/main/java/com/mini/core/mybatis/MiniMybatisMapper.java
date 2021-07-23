package com.mini.core.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNullElse;

@SuppressWarnings("UnusedReturnValue")
public interface MiniMybatisMapper<T> extends BaseMapper<T> {

    /**
     * 保存一条记录
     *
     * @param entity 实体对象
     * @return 保存结果
     */
    int save(T entity);

    /**
     * 删除一条记录
     *
     * @param entity 实体对象
     * @return 删除结果
     */
    int replace(T entity);

    /**
     * 更新一条记录
     *
     * @param entity 实体对象
     * @return 更新结果
     */
    default int update(@Param(Constants.ENTITY) T entity) {
        return updateById(entity);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    default int updateByWrapper(T entity, @Nullable Wrapper<T> updateWrapper) {
        return this.update(entity, updateWrapper);
    }

    /**
     * 删除一条记录
     *
     * @param entity 实体对象
     * @return 删除结果
     */
    int remove(@Param(Constants.ENTITY) T entity);

    /**
     * 删除一条记录
     *
     * @param entity 实体对象
     * @return 删除结果
     */
    default int delete(@Param(Constants.ENTITY) T entity) {
        return this.remove(entity);
    }

    /**
     * 根据ID列表批量删除记录
     *
     * @param idList ID列表,不能为NULL以及EMPTY
     * @return 删除结果
     */
    default int deleteByIdList(Collection<? extends Serializable> idList) {
        return deleteBatchIds(idList);
    }

    /**
     * 根据Wrapper条件删除记录
     *
     * @param queryWrapper 实体对象封装操作类
     * @return 删除结果
     */
    default int deleteByWrapper(@Param(Constants.WRAPPER) @Nullable Wrapper<T> queryWrapper) {
        return this.delete(queryWrapper);
    }

    /**
     * 根据ID查询条件条件的记录数量
     *
     * @param id 记录ID
     * @return 查询结果
     */
    int countById(Serializable id);

    /**
     * 根据Wrapper条件查询符合条件的记录数量
     *
     * @param queryWrapper 实体对象封装操作类
     * @return 查询结果
     */
    default int countByWrapper(@Nullable Wrapper<T> queryWrapper) {
        return requireNonNullElse(selectCount(null), 0);
    }

    /**
     * 查询所有实体数据条数
     *
     * @return 查询结果
     */
    default int count() {
        return countByWrapper(null);
    }

    /**
     * 根据ID查询是否存在记录
     *
     * @param id ID
     * @return 查询结果
     */
    default boolean existsById(Serializable id) {
        return countById(id) > 0;
    }

    /**
     * 根据Wrapper条件是否存在记录
     *
     * @param queryWrapper 实体对象封装操作类
     * @return 查询结果
     */
    default boolean existsByWrapper(@Nullable Wrapper<T> queryWrapper) {
        return countByWrapper(queryWrapper) > 0;
    }

    /**
     * 根据ID查询记录
     *
     * @param id ID
     * @return 查询结果
     */
    default T getById(Serializable id) {
        return selectById(id);
    }

    /**
     * 根据ID查询记录
     *
     * @param id ID
     * @return 查询结果
     */
    default Optional<T> findById(@Nonnull Serializable id) {
        return Optional.ofNullable(getById(id));
    }

    /**
     * 根据Wrapper条件查询记录
     *
     * @param queryWrapper 实体对象封装操作类
     * @return 查询结果
     */
    default T getByWrapper(@Nullable QueryWrapper<T> queryWrapper) {
        return selectOne(queryWrapper);
    }

    /**
     * 根据ID批量查询记录
     *
     * @param idList ID列表,不能为NULL以及EMPTY
     * @return 查询结果
     */
    default List<T> findByIdList(@Nonnull Collection<? extends Serializable> idList) {
        return selectBatchIds(idList);
    }

    /**
     * 根据Wrapper条件查询记录
     *
     * @param queryWrapper 实体对象封装操作类
     * @return 查询结果
     */
    default List<T> findByWrapper(QueryWrapper<T> queryWrapper) {
        return selectList(queryWrapper);
    }
}
