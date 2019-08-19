package com.mini.web.test.service.base;

import com.mini.jdbc.util.Paging;
import com.mini.web.test.dao.InitDao;
import com.mini.web.test.entity.Init;

import java.util.List;

/**
 * BaseInitService.java
 * @author xchao
 */
public interface BaseInitService {
    /**
     * 获取InitDao对象
     * @return InitDao对象
     */
    InitDao getInitDao();

    /**
     * 添加实体信息
     * @param init 实体信息
     * @return 执行结果
     */
    default int insert(Init init) {
        return getInitDao().insert(init);
    }

    /**
     * 修改实体信息
     * @param init 实体信息
     * @return 执行结果
     */
    default int update(Init init) {
        return getInitDao().update(init);
    }

    /**
     * 删除实体信息
     * @param init 实体信息
     * @return 执行结果
     */
    default int delete(Init init) {
        return getInitDao().delete(init);
    }

    /**
     * 根据ID删除实体信息
     * @param id 参数键
     * @return 执行结果
     */
    default int deleteById(int id) {
        return getInitDao().deleteById(id);
    }

    /**
     * 根据ID查询实体信息
     * @param id 参数键
     * @return 实体信息
     */
    default Init queryById(int id) {
        return getInitDao().queryById(id);
    }

    /**
     * 查询所有实体信息
     * @return 实体信息列表
     */
    default List<Init> queryAll() {
        return getInitDao().queryAll();
    }

    /**
     * 查询所有实体信息
     * @param paging 分布工具
     * @return 实体信息列表
     */
    default List<Init> queryAll(Paging paging) {
        return getInitDao().queryAll(paging);
    }
}
