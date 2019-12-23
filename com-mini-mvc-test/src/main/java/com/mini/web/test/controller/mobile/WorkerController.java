package com.mini.web.test.controller.mobile;

import com.mini.core.util.PKGenerator;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.JsonModel;
import com.mini.web.model.factory.ModelType;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

/**
 * 主键生成控制器
 * @author xchao
 */
@Singleton
@Controller(path = "worker", url = "worker")
public final class WorkerController {

	/**
	 * 获取长整型类型的主键
	 * @param model 数据模型渲染器
	 */
	@Action(value = ModelType.JSON, url = "id.htm")
	public void id(JsonModel model, HttpServletRequest request) {
		model.setData("" + PKGenerator.id());
	}

	/**
	 * 获取长整型类型的主键
	 * @param model 数据模型渲染器
	 */
	@Action(value = ModelType.JSON, url = "uuid.htm")
	public void uuid(JsonModel model, HttpServletRequest request) {
		model.setData(PKGenerator.uuid());
	}
}
