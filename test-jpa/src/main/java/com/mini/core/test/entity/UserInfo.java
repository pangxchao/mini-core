package com.mini.core.test.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

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

    // regionId
    @Column(name = "user_region_id")
    private Long regionId;

    // createTime
    @Column(name = "user_create_time")
    private Date createTime;

    @Tolerate
    public UserInfo() {
    }


}
 
 
