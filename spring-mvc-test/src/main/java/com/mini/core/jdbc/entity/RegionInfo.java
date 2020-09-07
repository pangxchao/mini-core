package com.mini.core.jdbc.entity;


import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Table(RegionInfo.region_info)
@SuperBuilder(toBuilder = true)
public class RegionInfo implements Serializable {
    public static final String region_info = "region_info";
    public static final String region_id = "region_id";
    public static final String region_name = "region_name";
    public static final String region_parent_id = "region_parent_id";

    @Id
    @Column(region_id)
    private Long id;

    @Column(region_name)
    private String name;

    @MappedCollection(keyColumn = region_parent_id, idColumn = region_id)
    private RegionInfo parent;

    @Tolerate
    public RegionInfo() {
    }
}
 
 
