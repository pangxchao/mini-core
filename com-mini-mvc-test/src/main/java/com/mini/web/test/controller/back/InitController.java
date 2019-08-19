package com.mini.web.test.controller.back;

import com.alibaba.fastjson.JSON;
import com.mini.jdbc.util.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.PageModel;
import com.mini.web.model.factory.ModelType;
import com.mini.web.test.entity.Init;
import com.mini.web.test.service.InitService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

/**
 * InitController.java
 * @author xchao
 */
@Singleton
@Controller(path = "back/init", url = "back/init")
public class InitController {
    @Inject
    private InitService initService;

    /**
     * 实体列表首页
     * @param model 数据模型渲染器
     */
    @Action(url = "index.htm")
    public void index(PageModel model) {
    }

    /**
     * 实体列表数据分页
     * @param model   数据模型渲染器
     * @param paging  数据分页工具
     * @param request HttpServletRequest
     */
    @Action(value = ModelType.MAP, url = "pages.htm")
    public void pages(MapModel model, Paging paging, HttpServletRequest request) {
        System.out.println(JSON.toJSONString(request.getParameterMap()));
        model.addData("data", initService.queryAll(paging));
        model.addData("count", paging.getTotal());
        model.addData("msg", "获取数据成功");
        model.addData("code", 0);
    }

    /**
     * 添加实体处理
     * @param model 数据模型渲染器
     * @param init  实体信息
     */
    @Action(value = ModelType.MAP, url = "insert.htm")
    public void insert(MapModel model, Init init) {
        initService.insert(init);
    }

    /**
     * 修改实体处理
     * @param model 数据模型渲染器
     * @param init  实体信息
     */
    @Action(value = ModelType.MAP, url = "update.htm")
    public void update(MapModel model, Init init) {
        initService.update(init);
    }

    /**
     * 删除实体信息处理
     * @param model 数据模型渲染器
     * @param id    参数键
     */
    @Action(value = ModelType.MAP, url = "delete.htm")
    public void delete(MapModel model, int id) {
        initService.deleteById(id);
    }
}
