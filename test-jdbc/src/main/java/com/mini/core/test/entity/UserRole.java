package com.mini.core.test.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;


@Data
@Table(UserRole.USER_ROLE)
@SuperBuilder(toBuilder = true)
public class UserRole implements Serializable {
    public static final String USER_ROLE = "user_role";
    public static final String USER_ID = "user_id";
    public static final String ROLE_ID = "role_id";


    // userId
    @Column(UserRole.USER_ID)
    private Long userId;

    // roleId
    @Column(UserRole.ROLE_ID)
    private Long roleId;

    @Tolerate
    public UserRole() {
    }

}



 
