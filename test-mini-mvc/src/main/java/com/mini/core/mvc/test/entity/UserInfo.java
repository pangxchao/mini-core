package com.mini.core.mvc.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Data
@Table(UserInfo.user_info)
@SuperBuilder(toBuilder = true)
public class UserInfo implements Serializable {
    public static final String user_info = "user_info";
    public static final String user_id = "user_id";
    public static final String user_name = "user_name";
    public static final String user_full_name = "user_full_name";
    public static final String user_email = "user_email";
    public static final String user_age = "user_age";
    public static final String user_region_id = "user_region_id";
    public static final String user_create_time = "user_create_time";

    @Id
    @Column(user_id)
    private Long id;

    @Column(user_name)
    private String name;

    @Column(user_full_name)
    private String fullName;

    @Column(user_email)
    private String email;

    @Column(user_age)
    private Integer age;

    @Column(user_region_id)
    private Long regionId;

    @CreatedDate
    @Column(user_create_time)
    private Date createTime;

    @Tolerate
    public UserInfo() {
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime_DT() {
        return this.createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getCreateTime_D() {
        return this.createTime;
    }
}
 
 
