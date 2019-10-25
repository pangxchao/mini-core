package com.mini.web.test.controller.back;

import com.mini.jdbc.util.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.PageModel;
import com.mini.web.model.factory.ModelType;
import com.mini.web.test.dao.RegionDao;
import com.mini.web.test.entity.Region;
import com.mini.web.util.IStatus;
import java.lang.String;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class RegionController implements IStatus {
  @Inject
  private RegionDao regionDao;

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
   * @param model 数据模型渲染器 
   * @param paging 数据分页工具 
   */
  @Action(
      value = ModelType.MAP,
      url = "pages.htm"
  )
  public void pages(MapModel model, Paging paging) {
    List<Region> list = regionDao.queryAll(paging);
    model.addData("data", list.stream().map(region-> { 
    	Map<String, String> map = new HashMap<>();
    	map.put("id", String.valueOf(region.getId()));
    	map.put("name", region.getName());
    	map.put("idUri", region.getIdUri());
    	map.put("nameUri", region.getNameUri());
    	map.put("regionId", String.valueOf(region.getRegionId()));
    	return map;
    }).toArray());
    model.addData("count", paging.getTotal());
    model.addData("msg", "获取数据成功");
    model.addData("code", 0);
  }

  /**
   * 添加实体处理 
   * @param model 数据模型渲染器 
   * @param id 地区码/地区ID 
   * @param name 地区名称 
   * @param idUri 地区ID列表 
   * @param nameUri 地区名称列表 
   * @param regionId 上级地区ID 
   */
  @Action(
      value = ModelType.MAP,
      url = "insert.htm"
  )
  public void insert(MapModel model, int id, String name, String idUri, String nameUri,
      int regionId) {
    Region.Builder builder = Region.newBuilder();
    builder.id(id);
    builder.name(name);
    builder.idUri(idUri);
    builder.nameUri(nameUri);
    builder.regionId(regionId);
    regionDao.insert(builder.build());
  }

  /**
   * 修改实体处理 
   * @param model 数据模型渲染器 
   * @param id 地区码/地区ID 
   * @param name 地区名称 
   * @param idUri 地区ID列表 
   * @param nameUri 地区名称列表 
   * @param regionId 上级地区ID 
   */
  @Action(
      value = ModelType.MAP,
      url = "update.htm"
  )
  public void update(MapModel model, int id, String name, String idUri, String nameUri,
      int regionId) {
    Region.Builder builder = Region.newBuilder();
    builder.id(id);
    builder.name(name);
    builder.idUri(idUri);
    builder.nameUri(nameUri);
    builder.regionId(regionId);
    regionDao.update(builder.build());
  }

  /**
   * 删除实体信息处理 
   * @param model 数据模型渲染器 
   * @param id 地区码/地区ID 
   */
  @Action(
      value = ModelType.MAP,
      url = "delete.htm"
  )
  public void delete(MapModel model, int id) {
    regionDao.deleteById(id);
  }
}
