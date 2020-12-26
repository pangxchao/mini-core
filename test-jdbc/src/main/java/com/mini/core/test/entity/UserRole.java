package com.mini.core.test.entity;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.*;

import lombok.*;
import lombok.experimental.*;

import java.io.Serializable;


@Data
@SuperBuilder(toBuilder = true)
@Table(UserRole.USER_ROLE)
public class UserRole implements Serializable {

    //
    public static final String USER_ROLE = "user_role";
    //
    public static final String USER_ID = "user_id";
    //
    public static final String ROLE_ID = "role_id";

    // userId
    @Column(USER_ID)
    private Long userId;

    // roleId
    @Column(ROLE_ID)
    private Long roleId;

    @Tolerate
    public UserRole() {
    }


}
 
 
