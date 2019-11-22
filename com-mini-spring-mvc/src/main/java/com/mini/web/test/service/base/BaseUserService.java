package com.mini.web.test.service.base;

import com.mini.jdbc.model.Paging;
import com.mini.web.test.dao.UserDao;
import com.mini.web.test.entity.User;

import java.util.List;

/**
 * BaseUserService.java
 * @author xchao
 */
public interface BaseUserService {
    /**
     * 获取UserDao对象
     * @return UserDao对象
     */
    UserDao getUserDao();

    /**
     * 添加实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    default int insert(User user) {
        return getUserDao().insert(user);
    }

    /**
     * 修改实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    default int update(User user) {
        return getUserDao().update(user);
    }

    /**
     * 删除实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    default int delete(User user) {
        return getUserDao().delete(user);
    }

    /**
     * 根据ID删除实体信息
     * @param id 用户ID
     * @return 执行结果
     */
    default int deleteById(long id) {
        return getUserDao().deleteById(id);
    }

    /**
     * 根据ID查询实体信息
     * @param id 用户ID
     * @return 实体信息
     */
    default User queryById(long id) {
        return getUserDao().queryById(id);
    }

    /**
     * 查询所有实体信息
     * @return 实体信息列表
     */
    default List<User> queryAll() {
        return getUserDao().queryAll();
    }

    /**
     * 查询所有实体信息
     * @param paging 分布工具
     * @return 实体信息列表
     */
    default List<User> queryAll(Paging paging) {
        return getUserDao().queryAll(paging);
    }
}
