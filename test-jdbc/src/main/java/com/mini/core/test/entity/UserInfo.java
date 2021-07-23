package com.mini.core.test.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Data
@Table("user_info")
public class UserInfo implements Serializable {
    // id
    @Id
    @Column("user_id")
    private Long id;

    // name
    @Column("user_name")
    private String name;

    // fullName
    @Column("user_full_name")
    private String fullName;

    // email
    @Column("user_email")
    private String email;

    // age
    @Column("user_age")
    private Integer age;

    // regionId
    @Column("user_region_id")
    private Long regionId;

    // createTime
    @CreatedDate
    @Column("user_create_time")
    private Date createTime = new Date();

}
 
 
