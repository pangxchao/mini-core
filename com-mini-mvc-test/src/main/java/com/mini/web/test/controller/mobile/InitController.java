package com.mini.web.test.controller.mobile;

import com.mini.jdbc.util.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.factory.ModelType;
import com.mini.web.test.entity.Init;
import com.mini.web.test.service.InitService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * InitController.java
 * @author xchao
 */
@Singleton
@Controller(
        path = "mobile/init",
        url = "mobile/init"
)
public class InitController {
    @Inject
    private InitService initService;

    /**
     * 实体列表查询方法
     * @param model  数据模型渲染器
     * @param paging 数据分页工具
     */
    @Action(
            value = ModelType.MAP,
            url = "list.htm"
    )
    public void list(MapModel model, Paging paging) {
        model.addData("data", initService.queryAll(paging));
        model.addData("paging", paging);
    }

    /**
     * 添加实体处理
     * @param model 数据模型渲染器
     * @param init  实体信息
     */
    @Action(
            value = ModelType.MAP,
            url = "insert.htm"
    )
    public void insert(MapModel model, Init init) {
        initService.insert(init);
    }

    /**
     * 修改实体处理
     * @param model 数据模型渲染器
     * @param init  实体信息
     */
    @Action(
            value = ModelType.MAP,
            url = "update.htm"
    )
    public void update(MapModel model, Init init) {
        initService.update(init);
    }

    /**
     * 删除实体信息处理
     * @param model 数据模型渲染器
     * @param id    参数键
     */
    @Action(
            value = ModelType.MAP,
            url = "delete.htm"
    )
    public void delete(MapModel model, int id) {
        initService.deleteById(id);
    }
}
