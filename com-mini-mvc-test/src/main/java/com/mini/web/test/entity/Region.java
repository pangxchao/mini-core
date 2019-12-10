package com.mini.web.test.entity;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.util.JdbcUtil;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Region.java
 * @author xchao
 */
public class Region implements Serializable {

	/**
     * 表名称 common_region
     */
    public static final String TABLE = "common_region";

    /**
     * 地区码/地区ID
     */
    public static final String ID = "region_id";

    /**
     * 地区名称
     */
    public static final String NAME = "region_name";

    /**
     * 地区ID列表
     */
    public static final String ID_URI = "region_id_uri";

    /**
     * 地区名称列表
     */
    public static final String NAME_URI = "region_name_uri";

    /**
     * 上级地区ID
     */
    public static final String REGION_ID = "region_region_id";

    private int id;

    private String name;

    private String idUri;

    private String nameUri;

    private Integer regionId;

    public Region() {
    }

    private Region(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setIdUri(builder.idUri);
        setNameUri(builder.nameUri);
        setRegionId(builder.regionId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdUri() {
        return idUri;
    }

    public void setIdUri(String idUri) {
        this.idUri = idUri;
    }

    public String getNameUri() {
        return nameUri;
    }

    public void setNameUri(String nameUri) {
        this.nameUri = nameUri;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Region copy) {
        Builder builder = new Builder();
        builder.id       = copy.getId();
        builder.name     = copy.getName();
        builder.idUri    = copy.getIdUri();
        builder.nameUri  = copy.getNameUri();
        builder.regionId = copy.getRegionId();
        return builder;
    }

    public static Region mapper(ResultSet rs, int number) throws SQLException {
        Builder builder = Region.newBuilder();
        builder.id       = JdbcUtil.getInt(rs, ID);
        builder.name     = JdbcUtil.getString(rs, NAME);
        builder.idUri    = JdbcUtil.getString(rs, ID_URI);
        builder.nameUri  = JdbcUtil.getString(rs, NAME_URI);
        builder.regionId = JdbcUtil.getInteger(rs, REGION_ID);
        return builder.build();
    }

    public static final class Builder {
        private int id;

        private String name;

        private String idUri;

        private String nameUri;

        private Integer regionId;

        private Builder() {
        }

        public final Builder setId(int id) {
            this.id = id;
            return this;
        }

        public final Builder setName(String name) {
            this.name = name;
            return this;
        }

        public final Builder setIdUri(String idUri) {
            this.idUri = idUri;
            return this;
        }

        public final Builder setNameUri(String nameUri) {
            this.nameUri = nameUri;
            return this;
        }

        public final Builder setRegionId(Integer regionId) {
            this.regionId = regionId;
            return this;
        }

        @Nonnull
        public final Region build() {
            return new Region(this);
        }
    }

    public static class RegionBuilder extends SQLBuilder {
        protected RegionBuilder() {
            select(ID);
            select(NAME);
            select(ID_URI);
            select(NAME_URI);
            select(REGION_ID);
            from(TABLE);
        }
    }
}
