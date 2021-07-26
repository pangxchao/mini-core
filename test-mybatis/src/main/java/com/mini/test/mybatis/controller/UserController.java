package com.mini.test.mybatis.controller;

import com.mini.core.mvc.model.JsonModel;
import com.mini.test.mybatis.entity.UserInfo;
import com.mini.test.mybatis.form.UserSave;
import com.mini.test.mybatis.mapper.UserInfoMapper;
import com.mini.test.mybatis.mapper.UserRoleMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.Locale;

@Primary
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserInfoMapper userInfoMapper;
    private final UserRoleMapper userRoleMapper;

    public UserController(UserInfoMapper userInfoMapper, UserRoleMapper userRoleMapper) {
        this.userInfoMapper = userInfoMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @RequestMapping(path = "/list")
    public ResponseEntity<ModelMap> list(JsonModel model, int page, int limit) {
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(735041375153684480L);
        userInfo.setCreateTime(new Date());
        userInfo.setFullName("fullName3");
        userInfo.setEmail("323456@qq.com");
        userInfo.setName("name3");
        userInfo.setAge(30);

        System.out.println("==============================save==============================");
        System.out.println(userInfoMapper.save(userInfo));

        System.out.println("===========================replace=================================");
        System.out.println(userInfoMapper.replace(userInfo));

        System.out.println("============================update================================");
        System.out.println(userInfoMapper.update(userInfo));

        System.out.println("============================remove================================");
        System.out.println(userInfoMapper.remove(userInfo));

        System.out.println("============================insert================================");
        System.out.println(userInfoMapper.insert(userInfo));

        System.out.println("============================delete================================");
        System.out.println(userInfoMapper.delete(userInfo));

        System.out.println("============================countById================================");
        System.out.println(userInfoMapper.countById(735041375153684480L));

        System.out.println("============================countById================================");
        System.out.println(userInfoMapper.countById(userInfo));

        System.out.println("============================count================================");
        System.out.println(userInfoMapper.count());

        return model.build();
    }

    @RequestMapping(path = "/id_list")
    public ResponseEntity<ModelMap> id_list(JsonModel model, Long[] idList) {
        final UserInfo userInfo = new UserInfo();
        //userInfo.setCreateTime(new Date());
        userInfo.setFullName("fullName9");
        userInfo.setEmail("923456@qq.com");
        userInfo.setName("name9");
        userInfo.setAge(25);
        userInfo.setId(9L);
        System.out.println(userInfoMapper.insert(userInfo));
        return model.build();
    }

    @RequestMapping(path = "/info")
    public ResponseEntity<ModelMap> info(JsonModel model, @Positive(message = "{Positive}") Long id, @NotBlank String name, Locale locale) {

        return model.build();
    }

    @RequestMapping(path = "/save")
    public ResponseEntity<ModelMap> save(JsonModel model, @Validated UserSave userSave) {
        final UserInfo userInfo = new UserInfo();
        userInfo.setFullName("-fullName8-");
        userInfo.setName("-name8-");
        userInfo.setId(8L);
        userInfoMapper.updateNameAndFullNameById(userInfo);
        return model.build();
    }

    @RequestMapping(path = "/remove")
    public ResponseEntity<ModelMap> remove(JsonModel model, @Positive Long id) {

        return model.build();
    }

    @RequestMapping(path = "/delete")
    public ResponseEntity<ModelMap> delete(JsonModel model, @Positive Long id) {

        return model.build();
    }

}
