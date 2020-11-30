package com.mini.core.test.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


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

    @ManyToOne
    @JoinColumn(name = "region_parent_id", referencedColumnName = "region_id")
    private RegionInfo regionInfo;

    @OneToMany(mappedBy = "regionInfo")
    //@JoinColumn(name = "region_parent_id", referencedColumnName = "region_id")
    private List<RegionInfo> children;

    @OneToMany(targetEntity = UserInfo.class, mappedBy = "")
    private List<UserInfo> userInfoList;

    @Tolerate
    public RegionInfo() {
    }


}
 
 
