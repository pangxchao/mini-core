package com.mini.core.test.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user_info")
@SuperBuilder(toBuilder = true)
public class UserInfo implements Serializable {

    // id
    @Id
    @Column(name = "user_id")
    private Long id;

    // name
    @Column(name = "user_name")
    private String name;

    // fullName
    @Column(name = "user_full_name")
    private String fullName;

    // email
    @Column(name = "user_email")
    private String email;

    // age
    @Column(name = "user_age")
    private Integer age;

    // createTime
    @Column(name = "user_create_time")
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "user_region_id", referencedColumnName = "region_id")
    private RegionInfo regionInfo;

    @ManyToMany(targetEntity = RoleInfo.class)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")}
    )
    public List<RoleInfo> roleInfoList;

    @Tolerate
    public UserInfo() {
    }


}
 
 
