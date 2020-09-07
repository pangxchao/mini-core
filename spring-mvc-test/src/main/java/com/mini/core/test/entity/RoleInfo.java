package com.mini.core.test.entity;

import com.mini.core.jdbc.support.Column;
import com.mini.core.jdbc.support.Comment;
import com.mini.core.jdbc.support.Id;
import com.mini.core.jdbc.support.Table;

import java.io.Serializable;

@Table(RoleInfo.role_info)
public class RoleInfo implements Serializable {
    @Comment("角色信息")
    public static final String role_info = "role_info";
    @Comment("")
    public static final String role_id = "role_id";
    @Comment("")
    public static final String role_name = "role_name";

    @Id
    @Column(role_id)
    private long id;

    @Column(role_name)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
 
 
