package com.mini.web.test.dao.base;

import com.mini.core.jdbc.JdbcInterface;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.model.Paging;
import com.mini.web.test.entity.User;

import java.util.List;

/**
 * UserBaseDao.java
 * @author xchao
 */
public interface UserBaseDao extends JdbcInterface {
    /**
     * 添加实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    default int insert(User user) {
        return execute(new SQLBuilder() {{
            insertInto(User.TABLE);
            // 用户ID
            values(User.ID);
            params(user.getId());
            // 用户名
            values(User.NAME);
            params(user.getName());
            // MD5(密码)
            values(User.PASSWORD);
            params(user.getPassword());
            // 用户手机号
            values(User.PHONE);
            params(user.getPhone());
            // 0-未认证，1-已谁
            values(User.PHONE_AUTH);
            params(user.getPhoneAuth());
            // 用户姓名
            values(User.FULL_NAME);
            params(user.getFullName());
            // 用户邮箱地址
            values(User.EMAIL);
            params(user.getEmail());
            // 0-未认证，1-已认证
            values(User.EMAIL_AUTH);
            params(user.getEmailAuth());
            // 用户头像地址
            values(User.HEAD_URL);
            params(user.getHeadUrl());
            // 用户所属地区ID
            values(User.REGION_ID);
            params(user.getRegionId());
            // 用户注册时间
            values(User.CREATE_TIME);
            params(user.getCreateTime());
        }});
    }

    /**
     * 添加实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    default int replace(User user) {
        return execute(new SQLBuilder() {{
            replaceInto(User.TABLE);
            // 用户ID
            values(User.ID);
            params(user.getId());
            // 用户名
            values(User.NAME);
            params(user.getName());
            // MD5(密码)
            values(User.PASSWORD);
            params(user.getPassword());
            // 用户手机号
            values(User.PHONE);
            params(user.getPhone());
            // 0-未认证，1-已谁
            values(User.PHONE_AUTH);
            params(user.getPhoneAuth());
            // 用户姓名
            values(User.FULL_NAME);
            params(user.getFullName());
            // 用户邮箱地址
            values(User.EMAIL);
            params(user.getEmail());
            // 0-未认证，1-已认证
            values(User.EMAIL_AUTH);
            params(user.getEmailAuth());
            // 用户头像地址
            values(User.HEAD_URL);
            params(user.getHeadUrl());
            // 用户所属地区ID
            values(User.REGION_ID);
            params(user.getRegionId());
            // 用户注册时间
            values(User.CREATE_TIME);
            params(user.getCreateTime());
        }});
    }

    /**
     * 修改实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    default int update(User user) {
        return execute(new SQLBuilder() {{
            update(User.TABLE);
            // 用户ID
            set("%s = ?", User.ID);
            params(user.getId());
            // 用户名
            set("%s = ?", User.NAME);
            params(user.getName());
            // MD5(密码)
            set("%s = ?", User.PASSWORD);
            params(user.getPassword());
            // 用户手机号
            set("%s = ?", User.PHONE);
            params(user.getPhone());
            // 0-未认证，1-已谁
            set("%s = ?", User.PHONE_AUTH);
            params(user.getPhoneAuth());
            // 用户姓名
            set("%s = ?", User.FULL_NAME);
            params(user.getFullName());
            // 用户邮箱地址
            set("%s = ?", User.EMAIL);
            params(user.getEmail());
            // 0-未认证，1-已认证
            set("%s = ?", User.EMAIL_AUTH);
            params(user.getEmailAuth());
            // 用户头像地址
            set("%s = ?", User.HEAD_URL);
            params(user.getHeadUrl());
            // 用户所属地区ID
            set("%s = ?", User.REGION_ID);
            params(user.getRegionId());
            // 用户注册时间
            set("%s = ?", User.CREATE_TIME);
            params(user.getCreateTime());
            // 用户ID
            where("%s = ?", User.ID);
            params(user.getId());
        }});
    }

    /**
     * 删除实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    default int delete(User user) {
        return execute(new SQLBuilder() {{
            delete().from(User.TABLE);
            // 用户ID
            where("%s = ?", User.ID);
            params(user.getId());
        }});
    }

    /**
     * 删除实体信息
     * @param id 用户ID
     * @return 执行结果
     */
    default int deleteById(long id) {
        return execute(new SQLBuilder() {{
            delete().from(User.TABLE);
            // 用户ID
            where("%s = ?", User.ID);
            params(id);
        }});
    }

    /**
     * 根据ID查询实体信息
     * @param id 用户ID
     * @return 实体信息
     */
    default User queryById(long id) {
        return queryObject(new User.UserBuilder() {{
            // 用户ID
            where("%s = ?", User.ID);
            params(id);
        }}, User::mapper);
    }

    /**
     * 查询所有实体信息
     * @return 实体信息列表
     */
    default List<User> queryAll() {
        return queryList(new User.UserBuilder() {{
            //
        }}, User::mapper);
    }

    /**
     * 查询所有实体信息
     * @param paging 分页工具
     * @return 实体信息列表
     */
    default List<User> queryAll(Paging paging) {
        return queryList(paging, new User.UserBuilder() {{
            //
        }}, User::mapper);
    }
}
