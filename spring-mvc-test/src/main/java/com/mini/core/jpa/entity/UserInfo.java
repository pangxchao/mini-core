package com.mini.core.jpa.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.hash;

@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_full_name")
    private String fullName;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_age")
    private Integer age;

    @Column(name = "user_region_id")
    private Long regionId;

    @Column(name = "user_create_time")
    private Date createTime;

    @ManyToOne(targetEntity = RegionInfo.class)
    @JoinColumn(name = "user_region_id", referencedColumnName = "region_id")
    private RegionInfo regionInfo;

    @ManyToMany(targetEntity = RoleInfo.class)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private List<RoleInfo> roleList;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public RegionInfo getRegionInfo() {
        return regionInfo;
    }

    public void setRegionInfo(RegionInfo regionInfo) {
        this.regionInfo = regionInfo;
    }

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(id, userInfo.id) &&
                Objects.equals(name, userInfo.name) &&
                Objects.equals(fullName, userInfo.fullName) &&
                Objects.equals(email, userInfo.email) &&
                Objects.equals(age, userInfo.age) &&
                Objects.equals(regionId, userInfo.regionId) &&
                Objects.equals(createTime, userInfo.createTime) &&
                Objects.equals(regionInfo, userInfo.regionInfo) &&
                Objects.equals(roleList, userInfo.roleList);
    }

    @Override
    public int hashCode() {
        return hash(id, name, fullName, email, age, regionId, createTime, regionInfo, roleList);
    }
}
 
 
