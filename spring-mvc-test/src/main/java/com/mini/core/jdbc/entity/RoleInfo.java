package com.mini.core.jdbc.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Table(RoleInfo.role_info)
@SuperBuilder(toBuilder = true)
public class RoleInfo implements Serializable {
    public static final String role_info = "role_info";
    public static final String role_id = "role_id";
    public static final String role_name = "role_name";

    @Id
    @Column(role_id)
    private long id;

    @Column(role_name)
    private String name;

    @Tolerate
    public RoleInfo() {
    }
}
 
 
