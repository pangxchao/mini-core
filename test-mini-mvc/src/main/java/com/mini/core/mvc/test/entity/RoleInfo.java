package com.mini.core.mvc.test.entity;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(RoleInfo.role_info)
@SuperBuilder(toBuilder = true)
public class RoleInfo implements Serializable {
    public static final String role_info = "role_info";
    public static final String role_id = "role_id";
    public static final String role_name = "role_name";

    @Id
    @Column(role_id)
    private Long id;

    @Column(role_name)
    private String name;

    @Tolerate
    public RoleInfo() {
    }


}
 
 
