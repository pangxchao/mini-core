package com.mini.core.test.entity;

import com.mini.core.jdbc.support.*;

import java.io.Serializable;
import java.util.Date;

@Table(UserInfo.user_info)
public class UserInfo implements Serializable {
    @Comment("用户信息")
    public static final String user_info = "user_info";

    @Comment("")
    public static final String user_id = "user_id";

    @Comment("")
    public static final String user_name = "user_name";

    @Comment("")
    public static final String user_full_name = "user_full_name";

    @Comment("")
    public static final String user_email = "user_email";

    @Comment("")
    public static final String user_age = "user_age";

    @Comment("")
    public static final String user_region_id = "user_region_id";

    @Comment("")
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

    @CreateAt
    @Column(user_create_time)
    private Date createTime;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
 
 
