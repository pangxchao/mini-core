package com.mini.core.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "region_info")
public class RegionInfo implements Serializable {

    @Id
    @Column(name = "region_id")
    private Long id;

    @Column(name = "region_name")
    private String name;

    @ManyToOne(targetEntity = RegionInfo.class)
    @JoinColumn(name = "region_parent_id", referencedColumnName = "region_id")
    private RegionInfo parent;

    @OneToMany(targetEntity = RegionInfo.class, mappedBy = "parent")
    private List<RegionInfo> regionInfoList;

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

    public RegionInfo getParent() {
        return parent;
    }

    public void setParent(RegionInfo parent) {
        this.parent = parent;
    }

    public List<RegionInfo> getRegionInfoList() {
        return regionInfoList;
    }

    public void setRegionInfoList(List<RegionInfo> regionInfoList) {
        this.regionInfoList = regionInfoList;
    }
}
 
 
