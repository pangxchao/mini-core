package com.mini.web.test.controller.back;

import com.mini.jdbc.util.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.PageModel;

import com.mini.web.test.entity.Region;
import com.mini.web.test.service.RegionService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * RegionController.java
 * @author xchao
 */
@Singleton
@Controller(
        path = "back/region",
        url = "back/region"
)
public class RegionController {
    @Inject
    private RegionService regionService;

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
            url = "pages.htm"
    )
    public void pages(PageModel model, Paging paging) {
        model.addData("data", regionService.queryAll(paging));
        model.addData("count", paging.getTotal());
        model.addData("msg", "查询成功");
        model.addData("code", 0);
    }

    /**
     * 添加实体页面
     * @param model 数据模型渲染器
     */
    @Action(
            url = "insert.htm"
    )
    public void insert(PageModel model) {
    }

    /**
     * 添加实体处理
     * @param model  数据模型渲染器
     * @param region 实体信息
     */
    @Action(
            value = MapModel.class,
            url = "hInsert.htm"
    )
    public void hInsert(MapModel model, Region region) {
        regionService.insert(region);
    }

    /**
     * 修改实体页面
     * @param model 数据模型渲染器
     * @param id    地区码/地区ID
     */
    @Action(
            url = "update.htm"
    )
    public void update(PageModel model, int id) {
        regionService.queryById(id);
    }

    /**
     * 修改实体处理
     * @param model  数据模型渲染器
     * @param region 实体信息
     */
    @Action(
            value = MapModel.class,
            url = "hUpdate.htm"
    )
    public void hUpdate(MapModel model, Region region) {
        regionService.update(region);
    }

    /**
     * 删除实体信息处理
     * @param model 数据模型渲染器
     * @param id    地区码/地区ID
     */
    @Action(
            value = MapModel.class,
            url = "delete.htm"
    )
    public void delete(MapModel model, int id) {
        regionService.deleteById(id);
    }
}
