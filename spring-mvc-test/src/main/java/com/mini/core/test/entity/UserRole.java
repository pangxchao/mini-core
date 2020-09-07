package com.mini.core.test.entity;

import com.mini.core.jdbc.support.Column;
import com.mini.core.jdbc.support.Comment;
import com.mini.core.jdbc.support.Id;
import com.mini.core.jdbc.support.Table;

import java.io.Serializable;

@Table(UserRole.user_role)
public class UserRole implements Serializable {
    @Comment("用户角色信息")
    public static final String user_role = "user_role";
    @Comment("")
    public static final String user_id = "user_id";
    @Comment("")
    public static final String role_id = "role_id";

    @Id
    @Column(user_id)
    private long userId;

    @Id
    @Column(role_id)
    private long roleId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
 
 
