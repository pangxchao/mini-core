package com.mini.test.mybatis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.core.mvc.model.JsonModel;
import com.mini.core.mvc.util.Jackson;
import com.mini.test.mybatis.domain.UserInfo;
import com.mini.test.mybatis.form.UserSave;
import com.mini.test.mybatis.mapper.UserInfoMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserInfoMapper userInfoMapper;

    private ObjectMapper objectMapper;

    public UserController(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @RequestMapping(path = "/list")
    public ResponseEntity<ModelMap> list(JsonModel model, @Positive long page, @Positive long limit) {
        List<UserInfo> userList = userInfoMapper.findByAgeLessThanEqual(30);
        String json = objectMapper.writeValueAsString(userList);
        userList = Jackson.parseArray(json, UserInfo.class);
        model.setData(userList);
        return model.build();
    }

    @RequestMapping(path = "/id_list")
    public ResponseEntity<ModelMap> id_list(JsonModel model, @NotEmpty long[] idList) {
        List<Long> collect = stream(idList).boxed().collect(toList());
        model.setData(userInfoMapper.findByIdList(collect));
        return model.build();
    }

    @RequestMapping(path = "/info")
    public ResponseEntity<ModelMap> info(JsonModel model, @Positive long id) {
        model.setData(userInfoMapper.getById(id));
        return model.build();
    }

    @RequestMapping(path = "/save")
    public ResponseEntity<ModelMap> save(JsonModel model, @Validated @NotNull UserSave userSave) {
        UserInfo userInfo = userSave.toUserInfo();
        userInfoMapper.save(userInfo);
        return model.build();
    }

    @RequestMapping(path = "/remove")
    public ResponseEntity<ModelMap> remove(JsonModel model, @Positive long id) {
        userInfoMapper.deleteById(id);
        return model.build();
    }

    @RequestMapping(path = "/delete")
    public ResponseEntity<ModelMap> delete(JsonModel model, @Positive long id) {
        userInfoMapper.deleteById(id);
        return model.build();
    }

}
