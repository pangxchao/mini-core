package com.mini.core.test.form;

import com.mini.core.test.entity.UserInfo;
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
    @Email(message = "{Email}")
    @NotBlank
    private String email;

    // 年龄
    @NotNull
    @Positive
    private Integer age;

    private Long regionId;

    public UserInfo toUserInfo() {
        return UserInfo.builder()
                .createTime(new Date())
                .regionId(regionId)
                .fullName(fullName)
                .email(email)
                .name(name)
                .age(age)
                .id(id)
                .build();
    }
}
