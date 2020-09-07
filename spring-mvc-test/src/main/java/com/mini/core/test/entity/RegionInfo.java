package com.mini.core.test.entity;

import com.mini.core.jdbc.support.Column;
import com.mini.core.jdbc.support.Comment;
import com.mini.core.jdbc.support.Id;
import com.mini.core.jdbc.support.Table;

import java.io.Serializable;

@Table(RegionInfo.region_info)
public class RegionInfo implements Serializable {
    @Comment("")
    public static final String region_info = "region_info";
    @Comment("")
    public static final String region_id = "region_id";
    @Comment("")
    public static final String region_name = "region_name";
    @Comment("")
    public static final String region_parent_id = "region_parent_id";

    @Id
    @Column(region_id)
    private Long id;

    @Column(region_name)
    private String name;

    @Column(region_parent_id)
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
 
 
