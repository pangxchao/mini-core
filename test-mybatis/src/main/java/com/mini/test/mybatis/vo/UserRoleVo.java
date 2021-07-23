package com.mini.test.mybatis.vo;

import com.mini.test.mybatis.entity.RoleInfo;
import com.mini.test.mybatis.entity.UserInfo;
import com.mini.test.mybatis.entity.UserRole;
import lombok.Data;

@Data
public class UserRoleVo extends UserRole {

    private RoleInfo roleInfo;

    private UserInfo userInfo;

    @Override
    public String toString() {
        return "com.mini.test.mybatis.vo.UserRoleVo{" +
                "super=" + super.toString() +
                ", userInfo=" + userInfo +
                ", roleInfo=" + roleInfo +
                "} ";
    }
}
 
 
