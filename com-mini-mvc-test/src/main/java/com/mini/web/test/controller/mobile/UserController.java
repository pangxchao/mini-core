package com.mini.web.test.controller.mobile;

import com.mini.jdbc.util.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.factory.ModelType;
import com.mini.web.test.entity.User;
import com.mini.web.test.service.UserService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * UserController.java
 * @author xchao
 */
@Singleton
@Controller(path = "mobile/user", url = "mobile/user")
public class UserController {
    @Inject
    private UserService userService;

    /**
     * 实体列表查询方法
     * @param model  数据模型渲染器
     * @param paging 数据分页工具
     */
    @Action(value = ModelType.MAP, url = "list.htm")
    public void list(MapModel model, Paging paging) {
        model.addData("data", userService.queryAll(paging));
        model.addData("paging", paging);
    }

    /**
     * 添加实体处理
     * @param model 数据模型渲染器
     * @param user  实体信息
     */
    @Action(value = ModelType.MAP, url = "insert.htm")
    public void insert(MapModel model, User user) {
        userService.insert(user);
    }

    /**
     * 修改实体处理
     * @param model 数据模型渲染器
     * @param user  实体信息
     */
    @Action(value = ModelType.MAP, url = "update.htm")
    public void update(MapModel model, User user) {
        userService.update(user);
    }

    /**
     * 删除实体信息处理
     * @param model 数据模型渲染器
     * @param id    用户ID
     */
    @Action(value = ModelType.MAP, url = "delete.htm")
    public void delete(MapModel model, long id) {
        userService.deleteById(id);
    }
}
