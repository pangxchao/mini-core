package com.mini.core.test.entity;

import com.mini.core.jdbc.common.LongId;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.*;

import lombok.*;
import lombok.experimental.*;

import java.io.Serializable;


@Data
@SuperBuilder(toBuilder = true)
@Table(RegionInfo.REGION_INFO)
public class RegionInfo implements LongId, Serializable {
    public static final String REGION_INFO = "region_info";
    public static final String REGION_ID = "region_id";
    public static final String REGION_NAME = "region_name";
    public static final String REGION_PARENT_ID = "region_parent_id";

    // id
    @Id
    @Column(REGION_ID)
    private Long id;

    // name
    @Column(REGION_NAME)
    private String name;

    // parentId
    @Column(REGION_PARENT_ID)
    private Long parentId;

    @Tolerate
    public RegionInfo() {
    }


}
 
 
