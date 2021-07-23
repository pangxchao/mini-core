package com.mini.core.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;


@Data
@Table("user_role")
public class UserRole implements Serializable {

    @Id
    @Column("user_role_id")
    private Long id;

    // userId
    @Column("user_role_user_id")
    private Long userId;

    // roleId
    @Column("user_role_role_id")
    private Long roleId;
}
 
 
