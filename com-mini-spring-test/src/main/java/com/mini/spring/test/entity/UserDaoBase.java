package com.mini.spring.test.entity;

import com.mini.util.dao.IBase;
import com.mini.util.dao.IMapper;
import com.mini.util.dao.sql.SQLDelete;
import com.mini.util.dao.sql.SQLInsert;
import com.mini.util.dao.sql.SQLSelect;
import com.mini.util.dao.sql.SQLUpdate;

import java.sql.SQLException;



public interface UserDaoBase extends IBase<User> {

    /**
     * 获取 Mapper 对象
     * @return #Mapper
     */
    IMapper<User> getUserMapper();

    /**
     * 添加用户信息
     * @param userInfo 用户信息
     */
    default void insert(User userInfo) throws SQLException {
        SQLInsert insert = new SQLInsert(User.TABLE);
        insert.put(User.ID).params(userInfo.getId());
        execute(insert);
    }

    /**
     * 删除用户信息
     * @param userInfo 用户信息
     */
    default void delete(User userInfo) throws SQLException {
        SQLDelete delete = new SQLDelete().from(User.TABLE);
        delete.where(User.ID).params(userInfo.getId());
        execute(delete);
    }

    /**
     * 根据ID删除用户信息
     * @param id ID
     */
    default void deleteById(long id) throws SQLException {
        SQLDelete delete = new SQLDelete().from(User.TABLE);
        delete.where(User.ID).params(id);
        execute(delete);
    }

    /**
     * 修改用户信息
     * @param userInfo 用户信息
     */
    default void update(User userInfo) throws SQLException {
        SQLUpdate update = new SQLUpdate().from(User.TABLE);
        update.put(User.ID).params(userInfo.getId());
        update.where(User.ID).params(userInfo.getId());
        execute(update);
    }

    /**
     * 根据ID查询用户信息
     * @param id 实体信息ID
     * @return 用户信息
     */
    default User queryById(long id) throws SQLException {
        SQLSelect select = UserMapper.sql();
        select.where(User.ID).params(id);
        return queryOne(select, getUserMapper());
    }
}
