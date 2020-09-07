package com.mini.core.test.dao;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.model.Page;
import com.mini.core.test.dao.base.UserInfoBaseDao;
import com.mini.core.test.entity.UserInfo;

public interface UserInfoDao extends UserInfoBaseDao {

    default Page<UserInfo> find(int page, int limit, String keyword) {
        return this.queryPage(page, limit, new SQLBuilder(it -> {
            it.SELECT_FROM_JOIN(UserInfo.class);
            if (keyword != null && !keyword.isBlank()) {
                it.WHERE("user_name like ?%");
                it.ARGS(keyword);
            }
        }), UserInfo.class);
    }
} 
