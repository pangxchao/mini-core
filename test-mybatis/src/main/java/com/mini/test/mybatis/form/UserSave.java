package com.mini.test.mybatis.form;

import com.mini.test.mybatis.domain.UserInfo;
import com.mini.test.mybatis.enums.GenderEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserSave implements Serializable {
    // 用户ID
    @Positive
    private Long id;

    // 用户名
    @NotBlank
    private String name;

    // 姓名
    @NotBlank
    private String fullName;

    // 邮箱
    @Email
    @NotBlank
    private String email;

    // 年龄
    @NotNull
    @Positive
    private Integer age;

    @NotNull
    private GenderEnum gender;

    @Positive
    private Long regionId;

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setCreateTime(new Date());
        userInfo.setFullName(fullName);
        userInfo.setRegionId(regionId);
        userInfo.setGender(gender);
        userInfo.setEmail(email);
        userInfo.setName(name);
        userInfo.setAge(age);
        userInfo.setId(id);
        return userInfo;
    }
}
