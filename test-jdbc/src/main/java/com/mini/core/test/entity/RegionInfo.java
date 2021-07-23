package com.mini.core.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;


@Data
@Table("region_info")
public class RegionInfo implements Serializable {
    // id
    @Id
    @Column("region_id")
    private Long id;

    // name
    @Column("region_name")
    private String name;

    // parentId
    @Column("region_parent_id")
    private Long parentId;
}
 
 
