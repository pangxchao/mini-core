package com.mini.test.mybatis.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.mini.test.mybatis.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pangchao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_info")
public class UserInfo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId("user_id")
    private Long id;

    /**
     *
     */
    @TableField("user_name")
    private String name;

    /**
     *
     */
    @TableField("user_full_name")
    private String fullName;

    /**
     *
     */
    @TableField("user_email")
    private String email;

    /**
     *
     */
    @TableField("user_age")
    private Integer age;

    /**
     *
     */
    @TableField(value = "user_gender")
    private GenderEnum gender;

    /**
     *
     */
    @TableField("user_region_id")
    private Long regionId;

    /**
     *
     */
    @TableField("user_create_time")
    private Date createTime;

    @JsonGetter("created")
    public Date getCreateTime() {
        return createTime;
    }
}
