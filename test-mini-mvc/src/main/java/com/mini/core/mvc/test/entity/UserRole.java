package com.mini.core.mvc.test.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Table(UserRole.user_role)
@SuperBuilder(toBuilder = true)
public class UserRole implements Serializable {
    public static final String user_role = "user_role";
    public static final String user_id = "user_id";
    public static final String role_id = "role_id";

    @Id
    @Column(user_id)
    private Long userId;

    @Id
    @Column(role_id)
    private Long roleId;

    @Tolerate
    public UserRole() {
    }


}
 
 
