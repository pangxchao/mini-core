package com.mini.core.web.test.controller.front;

import com.alibaba.fastjson.JSON;
import com.mini.core.jdbc.model.Paging;
import com.mini.core.validate.ValidateUtil;
import com.mini.core.web.annotation.Action;
import com.mini.core.web.annotation.Controller;
import com.mini.core.web.model.PageModel;
import com.mini.core.web.test.entity.Region;
import com.mini.core.web.test.entity.User;
import com.mini.core.web.test.service.RegionService;
import com.mini.core.web.test.service.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.StringTokenizer;

/**
 * 该组接口用于演示使用自定义的分页功能做一些页面分页和其它其它功能实现
 * <p>
 * 本示例演示借助Layui table 转换静态表格的功能做一个后台管理页面
 * </p>
 * <p>
 * 该接口与Back/Demo接口的一些功能是相同的，只在演示不同的实现
 * </p>
 * @author xchao
 */
@Singleton
@Controller(path = "front/demo", url = "front/demo")
public class DemoController {
	@Inject
	private UserService userService;

	@Inject
	private RegionService regionService;

	/**
	 * 实体列表首页
	 * @param model 数据模型渲染器
	 */
	@Action(url = "index.htm")
	public void index(PageModel model) {

	}

	/**
	 * 实体列表数据分页
	 * @param model     数据模型渲染器
	 * @param paging    数据分页工具
	 * @param search    搜索关键字
	 * @param sortType  排序方式
	 * @param phone     手机号是否认证
	 * @param email     邮箱是否认证
	 * @param province  省
	 * @param city      市
	 * @param district  区/县
	 * @param startTime 开始时间
	 * @param endTime   开始时间
	 * @param request   HttpServletRequest
	 */
	@Action(url = "pages.htm")
	public void pages(PageModel model, Paging paging, String search, int sortType, int phone, int email, int province, int city,
		int district, LocalDate startTime, LocalDate endTime, HttpServletRequest request) {
		System.out.println("===================front========================");
		System.out.println(JSON.toJSONString(request.getParameterMap()));

		StringBuilder regionIdUri = new StringBuilder();
		if (province > 0) {
			regionIdUri.append(province);
		}
		if (city > 0) {
			regionIdUri.append(".");
			regionIdUri.append(city);
		}
		if (district > 0) {
			regionIdUri.append(".");
			regionIdUri.append(district);
		}
		System.out.println(regionIdUri.toString());
		model.addData("userList", userService.search(paging, search, sortType, phone, email, regionIdUri.toString(), startTime, endTime));
		model.addData("paging", paging);
	}

	/**
	 * 添加用户弹出层页面
	 * @param model 数据模型渲染器
	 */
	@Action(url = "insert.htm")
	public void insert(PageModel model) {

	}

	/**
	 * 添加用户弹出层页面
	 * @param model 数据模型渲染器
	 */
	@Action(url = "update.htm")
	public void update(PageModel model, long id) {
		User user = userService.queryById(id);
		ValidateUtil.isNotNull(user, 600, "用户不存在");

		model.addData("user", user);
		Region region = regionService.queryById(user.getRegionId());
		if (region == null) return;

		String idUri = region.getIdUri();
		if (StringUtils.isBlank(idUri)) {
			return;
		}

		StringTokenizer tokenizer = new StringTokenizer(idUri, ".");
		if (tokenizer.hasMoreTokens()) {
			model.addData("province", tokenizer.nextToken());
		} else return;

		if (tokenizer.hasMoreTokens()) {
			model.addData("city", tokenizer.nextToken());
		} else return;

		if (tokenizer.hasMoreTokens()) {
			model.addData("regionId", tokenizer.nextToken());
		}
	}

	/**
	 * 查看用户信息
	 * @param model 数据模型渲染器
	 * @param id    用户ID
	 */
	@Action(url = "detail.htm")
	public void detail(PageModel model, long id) {
		User user = userService.queryById(id);
		ValidateUtil.isNotNull(user, 600, "用户不存在");

		model.addData("user", user);
		Region region = regionService.queryById(user.getRegionId());
		if (region == null) return;

		String idUri = region.getIdUri();
		if (StringUtils.isBlank(idUri)) {
			return;
		}

		StringTokenizer tokenizer = new StringTokenizer(idUri, ".");
		if (tokenizer.hasMoreTokens()) {
			model.addData("province", tokenizer.nextToken());
		} else return;

		if (tokenizer.hasMoreTokens()) {
			model.addData("city", tokenizer.nextToken());
		} else return;

		if (tokenizer.hasMoreTokens()) {
			model.addData("regionId", tokenizer.nextToken());
		}
	}
}
