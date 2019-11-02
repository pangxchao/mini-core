package com.mini.web.test.controller.back;

import com.mini.jdbc.util.Paging;
import com.mini.util.DateUtil;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.PageModel;
import com.mini.web.model.factory.ModelType;
import com.mini.web.test.dao.UserDao;
import com.mini.web.test.entity.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController.java
 * @author xchao
 */
@Singleton
@Controller(
        path = "back/user",
        url = "back/user"
)
public class UserController {
    @Inject
    private UserDao userDao;

    /**
     * 实体列表首页
     * @param model 数据模型渲染器
     */
    @Action(
            url = "index.htm"
    )
    public void index(PageModel model) {
    }

    /**
     * 实体列表数据分页
     * @param model  数据模型渲染器
     * @param paging 数据分页工具
     */
    @Action(
            value = ModelType.MAP,
            url = "pages.htm"
    )
    public void pages(MapModel model, Paging paging) {
        List<User> list = userDao.queryAll(paging);
        model.addData("data", list.stream().map(user -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(user.getId()));
            map.put("name", user.getName());
            map.put("password", user.getPassword());
            map.put("phone", user.getPhone());
            map.put("phoneAuth", String.valueOf(user.getPhoneAuth()));
            map.put("fullName", user.getFullName());
            map.put("email", user.getEmail());
            map.put("emailAuth", String.valueOf(user.getEmailAuth()));
            map.put("headUrl", user.getHeadUrl());
            map.put("regionId", String.valueOf(user.getRegionId()));
            map.put("createTime", DateUtil.formatDateTime(user.getCreateTime()));
            return map;
        }).toArray());
        model.addData("count", paging.getTotal());
        model.addData("msg", "获取数据成功");
        model.addData("code", 0);
    }

    /**
     * 添加实体处理
     * @param model      数据模型渲染器
     * @param id         用户ID
     * @param name       用户名
     * @param password   MD5(密码)
     * @param phone      用户手机号
     * @param phoneAuth  0-未认证，1-已谁
     * @param fullName   用户姓名
     * @param email      用户邮箱地址
     * @param emailAuth  0-未认证，1-已认证
     * @param headUrl    用户头像地址
     * @param regionId   用户所属地区ID
     * @param createTime 用户注册时间
     */
    @Action(
            value = ModelType.MAP,
            url = "insert.htm"
    )
    public void insert(MapModel model, long id, String name, String password, String phone,
            int phoneAuth, String fullName, String email, int emailAuth, String headUrl, Integer regionId,
            Date createTime) {
        User.Builder builder = User.newBuilder();
        builder.setId(id);
        builder.setName(name);
        builder.setPassword(password);
        builder.setPhone(phone);
        builder.setPhoneAuth(phoneAuth);
        builder.setFullName(fullName);
        builder.setEmail(email);
        builder.setEmailAuth(emailAuth);
        builder.setHeadUrl(headUrl);
        builder.setRegionId(regionId);
        builder.setCreateTime(createTime);
        userDao.insert(builder.build());
    }

    /**
     * 修改实体处理
     * @param model      数据模型渲染器
     * @param id         用户ID
     * @param name       用户名
     * @param password   MD5(密码)
     * @param phone      用户手机号
     * @param phoneAuth  0-未认证，1-已谁
     * @param fullName   用户姓名
     * @param email      用户邮箱地址
     * @param emailAuth  0-未认证，1-已认证
     * @param headUrl    用户头像地址
     * @param regionId   用户所属地区ID
     * @param createTime 用户注册时间
     */
    @Action(
            value = ModelType.MAP,
            url = "update.htm"
    )
    public void update(MapModel model, long id, String name, String password, String phone,
            int phoneAuth, String fullName, String email, int emailAuth, String headUrl, Integer regionId,
            Date createTime) {
        User.Builder builder = User.newBuilder();
        builder.setId(id);
        builder.setName(name);
        builder.setPassword(password);
        builder.setPhone(phone);
        builder.setPhoneAuth(phoneAuth);
        builder.setFullName(fullName);
        builder.setEmail(email);
        builder.setEmailAuth(emailAuth);
        builder.setHeadUrl(headUrl);
        builder.setRegionId(regionId);
        builder.setCreateTime(createTime);
        userDao.update(builder.build());
    }

    /**
     * 删除实体信息处理
     * @param model 数据模型渲染器
     * @param id    用户ID
     */
    @Action(
            value = ModelType.MAP,
            url = "delete.htm"
    )
    public void delete(MapModel model, long id) {
        userDao.deleteById(id);
    }
}
