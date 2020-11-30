package com.mini.core.test.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "role_info")
@SuperBuilder(toBuilder = true)
public class RoleInfo implements Serializable {

    // id
    @Id
    @Column(name = "role_id")
    private Long id;

    // name
    @Column(name = "role_name")
    private String name;

    @ManyToMany(targetEntity = UserInfo.class, mappedBy = "roleInfoList")
    private List<UserInfo> userInfoList;

    @Tolerate
    public RoleInfo() {
    }


}
 
 
