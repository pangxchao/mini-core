package com.mini.web.test.entity.extend;

import com.mini.core.jdbc.annotation.Column;
import com.mini.core.jdbc.annotation.Table;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.web.test.entity.Region;
import com.mini.web.test.entity.User;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static com.mini.web.test.util.FileGenerator.getPublicFullUrl;

/**
 * User.java
 * @author xchao
 */
@Table("user_info")
public class UserExt extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(Region.NAME)
    private String regionName;

    @Column(Region.ID_URI)
    private String regionIdUri;

    @Column(Region.NAME_URI)
    private String regionNameUri;


    public String getFullHeadUrl() {
        if (StringUtils.isBlank(getHeadUrl())) { return ""; }
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

    private UserExt(UserExt.Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setPassword(builder.password);
        setPhone(builder.phone);
        setPhoneAuth(builder.phoneAuth);
        setFullName(builder.fullName);
        setEmail(builder.email);
        setEmailAuth(builder.emailAuth);
        setHeadUrl(builder.headUrl);
        setRegionId(builder.regionId);
        setCreateTime(builder.createTime);
        setRegionId(builder.regionId);
        setRegionName(builder.regionName);
        setRegionIdUri(builder.regionIdUri);
        setRegionNameUri(builder.regionNameUri);
    }

    public static Builder extBuilder() {
        return new Builder();
    }

    public static Builder extBuilder(UserExt copy) {
        Builder builder = new Builder();
        builder.id            = copy.getId();
        builder.name          = copy.getName();
        builder.password      = copy.getPassword();
        builder.phone         = copy.getPhone();
        builder.phoneAuth     = copy.getPhoneAuth();
        builder.fullName      = copy.getFullName();
        builder.email         = copy.getEmail();
        builder.emailAuth     = copy.getEmailAuth();
        builder.headUrl       = copy.getHeadUrl();
        builder.regionId      = copy.getRegionId();
        builder.createTime    = copy.getCreateTime();
        builder.regionId      = copy.getRegionId();
        builder.regionName    = copy.getRegionName();
        builder.regionIdUri   = copy.getRegionIdUri();
        builder.regionNameUri = copy.getRegionNameUri();
        return builder;
    }

    public static UserExt mapper(ResultSet rs, int number) throws SQLException {
        Builder builder = UserExt.extBuilder();
        builder.id         = rs.getLong(ID);
        builder.name       = rs.getString(NAME);
        builder.password   = rs.getString(PASSWORD);
        builder.phone      = rs.getString(PHONE);
        builder.phoneAuth  = rs.getInt(PHONE_AUTH);
        builder.fullName   = rs.getString(FULL_NAME);
        builder.email      = rs.getString(EMAIL);
        builder.emailAuth  = rs.getInt(EMAIL_AUTH);
        builder.headUrl    = rs.getString(HEAD_URL);
        builder.regionId   = rs.getInt(REGION_ID);
        builder.createTime = rs.getDate(CREATE_TIME);

        builder.createTime(rs.getDate(User.CREATE_TIME));
        builder.regionName(rs.getString(Region.NAME));
        builder.regionIdUri(rs.getString(Region.ID_URI));
        builder.regionNameUri(rs.getString(Region.NAME_URI));
        return builder.build();
    }

    public static final class Builder {
        private long id;
        private String name;
        private String password;
        private String phone;
        private int phoneAuth;
        private String fullName;
        private String email;
        private int emailAuth;
        private String headUrl;
        private int regionId;
        private String regionName;
        private String regionIdUri;
        private String regionNameUri;
        private Date createTime;

        private Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder phoneAuth(int val) {
            phoneAuth = val;
            return this;
        }

        public Builder fullName(String val) {
            fullName = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder emailAuth(int val) {
            emailAuth = val;
            return this;
        }

        public Builder headUrl(String val) {
            headUrl = val;
            return this;
        }

        public Builder regionId(int val) {
            regionId = val;
            return this;
        }

        public Builder regionName(String val) {
            regionName = val;
            return this;
        }

        public Builder regionIdUri(String val) {
            regionIdUri = val;
            return this;
        }

        public Builder regionNameUri(String val) {
            regionNameUri = val;
            return this;
        }

        public UserExt build() {
            return new UserExt(this);
        }

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }
    }

    public abstract static class UserExtendBuilder extends SQLBuilder {
        public UserExtendBuilder() {
            select(User.ID);
            select(User.NAME);
            select(User.PASSWORD);
            select(User.PHONE);
            select(User.PHONE_AUTH);
            select(User.FULL_NAME);
            select(User.EMAIL);
            select(User.EMAIL_AUTH);
            select(User.HEAD_URL);
            select(User.REGION_ID);
            select(User.CREATE_TIME);
            // 地区名称
            select(Region.NAME);
            select(Region.ID_URI);
            select(Region.NAME_URI);
            from(User.TABLE);
            // 联合地区表
            join("%s ON %s = %s", Region.TABLE, User.REGION_ID, Region.ID);
        }
    }

}
