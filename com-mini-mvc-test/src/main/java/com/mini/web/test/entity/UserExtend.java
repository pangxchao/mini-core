package com.mini.web.test.entity;

import com.mini.util.StringUtil;

import java.io.Serializable;

import static com.mini.web.test.util.FileGenerator.getPublicFullUrl;

/**
 * User.java
 * @author xchao
 */
public class UserExtend extends User implements Serializable {
    private static final long serialVersionUID = -1L;

    private int regionId;
    private String regionName;
    private String regionIdUri;
    private String regionNameUri;

    @Override
    public int getRegionId() {
        return regionId;
    }

    @Override
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getFullHeadUrl() {
        if (StringUtil.isBlank(getHeadUrl())) {
            return "";
        }
        return getPublicFullUrl(getHeadUrl());
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionIdUri() {
        return regionIdUri;
    }

    public void setRegionIdUri(String regionIdUri) {
        this.regionIdUri = regionIdUri;
    }

    public String getRegionNameUri() {
        return regionNameUri;
    }

    public void setRegionNameUri(String regionNameUri) {
        this.regionNameUri = regionNameUri;
    }


}
