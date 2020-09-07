package com.mini.core.test.controller;

import com.mini.core.mvc.R;
import com.mini.core.mvc.model.JsonModel;
import com.mini.core.test.dao.UserInfoDao;
import com.mini.core.test.entity.UserInfo;
import com.mini.core.util.PKGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.File;
import java.util.List;


@Validated
@RestController
@RequestMapping("user")
public class UserController {

    private final UserInfoDao userInfoDao;

    @Autowired
    public UserController(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @RequestMapping("save")
    public JsonModel save(@Validated @NotNull UserInfo userInfo, @NotBlank String name) {
        System.out.println("------------------------save--------------------------");
        System.out.println(name);
        System.out.println(userInfo);
        userInfoDao.insertOnUpdate(userInfo);
        userInfo.setId(PKGenerator.id());
        return R.json();
    }

    @RequestMapping("info")
    public JsonModel info(@Positive Long id) {
        System.out.println("------------------------info--------------------------");
        UserInfo userInfo = userInfoDao.queryById(id);
        return R.json().setData(userInfo);
    }

    @RequestMapping("list")
    public JsonModel list(int page, int limit, String keyword) {
        System.out.println("------------------------list--------------------------");
        var list = userInfoDao.find(page, limit, keyword);
        return R.json().setData(list);
    }

    @RequestMapping("delete")
    public JsonModel delete(@Positive Long id) {
        System.out.println("------------------------delete--------------------------");
        if (userInfoDao.deleteById(id) == 1) {
            return R.json().ok();
        }
        return R.json().error("删除用户失败");
    }

    // http://localhost:8088/user/head?id=1
    @RequestMapping("head")
    public ResponseEntity<StreamingResponseBody> head(@Positive Long id, List<HttpRange> httpRangeList) {
        System.out.println("------------------------head--------------------------");
        UserInfo userInfo = userInfoDao.queryById(id);
        if (userInfo == null) return R.stream().error();
        File file = new File("D:/My Docs/Test_Download.txt");
        // TODO: 写入文件内容
        return R.stream().fileLength(file.length())
                .httpRangeList(httpRangeList)
                .fileName(file.getName())
                .ok();
    }
}
