package com.mini.web.test.controller.mobile;

import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.ListModel;
import com.mini.web.model.MapModel;
import com.mini.web.model.factory.ModelType;
import com.mini.web.test.entity.Region;
import com.mini.web.test.service.RegionService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * RegionController.java
 * @author xchao
 */
@Singleton
@Controller(path = "mobile/region", url = "mobile/region")
public class RegionController {
    @Inject
    private RegionService regionService;

    /**
     * 实体列表查询方法
     * @param model 数据模型渲染器
     * @param id    父级地区ID
     */
    @Action(value = ModelType.LIST, url = "list.htm")
    public void list(ListModel model, Long id) {
        if (id == null) return;
        model.addDataAll(regionService.queryByParent(id));
    }

    /**
     * 添加实体处理
     * @param model  数据模型渲染器
     * @param region 实体信息
     */
    @Action(value = ModelType.MAP, url = "insert.htm")
    public void insert(MapModel model, Region region) {
        regionService.insert(region);
    }

    /**
     * 修改实体处理
     * @param model  数据模型渲染器
     * @param region 实体信息
     */
    @Action(value = ModelType.MAP, url = "update.htm")
    public void update(MapModel model, Region region) {
        regionService.update(region);
    }

    /**
     * 删除实体信息处理
     * @param model 数据模型渲染器
     * @param id    地区码/地区ID
     */
    @Action(value = ModelType.MAP, url = "delete.htm")
    public void delete(MapModel model, int id) {
        regionService.deleteById(id);
    }
}
