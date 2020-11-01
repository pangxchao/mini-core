package com.mini.core.mvc.test.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@SuperBuilder(toBuilder = true)
@Table(RegionInfo.region_info)
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

    @Column(region_parent_id)
    private Long parentId;

    @Tolerate
    public RegionInfo() {
    }


//    public static final class RegionInfoBuilder {
//        private Long id;
//        private String name;
//        private Long parentId;
//
//        private RegionInfoBuilder() {
//        }
//
//        public RegionInfoBuilder id(Long id) {
//            this.id = id;
//            return this;
//        }
//
//        public RegionInfoBuilder name(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public RegionInfoBuilder parentId(Long parentId) {
//            this.parentId = parentId;
//            return this;
//        }
//
//        public RegionInfo build() {
//            RegionInfo regionInfo = new RegionInfo();
//            regionInfo.id = this.id;
//            regionInfo.name = this.name;
//            regionInfo.parentId = this.parentId;
//            return regionInfo;
//        }
//    }
}
 
 
