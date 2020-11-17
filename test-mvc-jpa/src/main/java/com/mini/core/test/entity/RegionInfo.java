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
@Table(name = "region_info")
@SuperBuilder(toBuilder = true)
public class RegionInfo implements Serializable {

    // id
    @Id
    @Column(name = "region_id")
    private Long id;

    // name
    @Column(name = "region_name")
    private String name;

    // parentId
    @Column(name = "region_parent_id")
    private Long parentId;

    @Tolerate
    public RegionInfo() {
    }


}
 
 
