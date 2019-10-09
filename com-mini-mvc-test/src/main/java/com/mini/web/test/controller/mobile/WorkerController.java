package com.mini.web.test.controller.mobile;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import com.mini.util.PKGenerator;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.StringModel;

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
	@Action(value = StringModel.class, url = "id.htm")
	public void id(StringModel model, HttpServletRequest request) {
		model.append("" + PKGenerator.id());
	}

	/**
	 * 获取长整型类型的主键
	 * @param model 数据模型渲染器
	 */
	@Action(value = StringModel.class, url = "uuid.htm")
	public void uuid(StringModel model, HttpServletRequest request) {
		model.append(PKGenerator.uuid());
	}
}
