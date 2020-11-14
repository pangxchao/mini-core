package com.mini.core.test.entity;

import com.mini.core.data.common.LongId;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;


@Data
@SuperBuilder(toBuilder = true)
@Table(RoleInfo.ROLE_INFO)
public class RoleInfo implements LongId, Serializable {
    public static final String ROLE_INFO = "role_info";
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_NAME = "role_name";

    // id
    @Id
    @Column(ROLE_ID)
    private Long id;

    // name
    @Column(ROLE_NAME)
    private String name;

    @Tolerate
    public RoleInfo() {
    }


}
 
 
