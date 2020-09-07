package com.mini.core.jdbc.entity;


import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

import static com.mini.core.jdbc.entity.RegionInfo.region_id;

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

    @Column(user_create_time)
    private Date createTime;

    @MappedCollection(keyColumn = user_region_id, idColumn = region_id)

    private RegionInfo regionInfo;

    @Tolerate
    public UserInfo() {
    }
}
 
 
