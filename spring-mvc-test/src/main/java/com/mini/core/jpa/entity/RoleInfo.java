package com.mini.core.jpa.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.hash;

@Entity
@Table(name = "role_info")
public class RoleInfo implements Serializable {
    @Id
    @Column(name = "role_id")
    private long id;

    @Column(name = "role_name")
    private String name;

    @ManyToMany(targetEntity = UserInfo.class, mappedBy = "roleList")
    private List<UserInfo> userList;

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

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleInfo roleInfo = (RoleInfo) o;
        return id == roleInfo.id &&
                Objects.equals(name, roleInfo.name) &&
                Objects.equals(userList, roleInfo.userList);
    }

    @Override
    public int hashCode() {
        return hash(id, name, userList);
    }
}
 
 
