package com.mini.web.test.dao.base;

import com.mini.jdbc.BasicsDao;
import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.util.Paging;
import com.mini.web.test.entity.User;
import com.mini.web.test.entity.mapper.UserMapper;
import com.mini.web.test.entity.mapper.UserMapper.UserBuilder;

import java.util.List;

/**
 * BaseUserDao.java
 * @author xchao
 */
public interface BaseUserDao extends BasicsDao {
    UserMapper getUserMapper();

    /**
     * 添加实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    default int insert(User user) {
        return execute(new SQLBuilder() {{
            insert_into(User.TABLE);
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
            replace_into(User.TABLE);
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
     * 根据ID删除实体信息
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
        return queryOne(new UserBuilder() {{
            // 用户ID
            where("%s = ?", User.ID);
            params(id);
        }}, getUserMapper());
    }

    /**
     * 查询所有实体信息
     * @return 实体信息列表
     */
    default List<User> queryAll() {
        return query(new UserBuilder() {{
            //
        }}, getUserMapper());
    }

    /**
     * 查询所有实体信息
     * @param paging 分布工具
     * @return 实体信息列表
     */
    default List<User> queryAll(Paging paging) {
        return query(paging, new UserBuilder() {{
            //
        }}, getUserMapper());
    }
}
