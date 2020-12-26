package com.mini.core.test.entity;

import com.mini.core.jdbc.common.LongId;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
@Table(UserInfo.USER_INFO)
public class UserInfo implements LongId, Serializable {
    public static final String USER_INFO = "user_info";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_FULL_NAME = "user_full_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_AGE = "user_age";
    public static final String USER_REGION_ID = "user_region_id";
    public static final String USER_CREATE_TIME = "user_create_time";

    // id
    @Id
    @Column(USER_ID)
    private Long id;

    // name
    @Column(USER_NAME)
    private String name;

    // fullName
    @Column(USER_FULL_NAME)
    private String fullName;

    // email
    @Column(USER_EMAIL)
    private String email;

    // age
    @Column(USER_AGE)
    private Integer age;

    // regionId
    @Column(USER_REGION_ID)
    private Long regionId;

    // createTime
    @CreatedDate
    @Builder.Default
    @Column(USER_CREATE_TIME)
    private Date createTime = new Date();

    @MappedCollection(idColumn = "region_id", keyColumn = "user_region_id")
    private RegionInfo regionInfo;

    @Tolerate
    public UserInfo() {
    }
}
 
 
