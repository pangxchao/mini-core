package com.mini.core.test.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@Entity
@Table(name = "role_info")
@SuperBuilder(toBuilder = true)
public class RoleInfo implements Serializable {

    // id
    @Id
    @Column(name = "role_id")
    private Long id;

    // name
    @Column(name = "role_name")
    private String name;

    @Tolerate
    public RoleInfo() {
    }


}
 
 
