package com.mini.web.test.controller.back;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import com.mini.jdbc.util.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.annotation.RequestName;
import com.mini.web.model.MapModel;
import com.mini.web.model.PageModel;
import com.mini.web.test.entity.User;
import com.mini.web.test.service.UserService;

/**
 * 后台用户管理
 * @author xchao
 */
@Singleton
@Controller(path = "back/user", url = "back/user")
public class UserController {

	@Inject
	private UserService userService;

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
	 * @param district  地区ID
	 * @param token     用户登录TOKEN
	 * @param search    搜索关键字
	 * @param sortField 排序字段名称
	 * @param sortType  排序方式
	 * @param types     用户类型
	 */
	@Action(url = "pages.htm")
	public void pages(PageModel model, Paging paging, int district, String token, String search, String sortField, int sortType,
			@RequestName("types[]") int[] types, HttpServletRequest request) {
		List<User> userInfoList = new ArrayList<>();
		for (int i = 1; i <= 598; i++) {
			User user = new User();
			user.setId(i);
			user.setName("Name" + i);
			user.setPhone("13000000001");
			user.setEmail("13100000001@qq.com");
			user.setHeadUrl("path/user/1.jpg");
			user.setCreateTime(new Date());
			user.setFullName("超超" + i);
			userInfoList.add(user);
		}

		Object[] result = sortType == 1
				? userInfoList.stream().sorted((o1, o2) -> (int) (o2.getId() - o1.getId())).skip(paging.getSkip()).limit(paging.getLimit())
						.toArray() //
				: userInfoList.stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).skip(paging.getSkip()).limit(paging.getLimit())
						.toArray();

		paging.setTotal(userInfoList.size());
		model.addData("data", result);
		model.addData("count", paging.getTotal());
		model.addData("msg", "查询成功");
		model.addData("code", 0);
	}

	/**
	 * 添加实体处理
	 * @param model 数据模型渲染器
	 * @param user  实体信息
	 */
	@Action(value = MapModel.class, url = "insert.htm")
	public void insert(MapModel model, User user) {
		userService.insert(user);
	}

	/**
	 * 修改实体处理
	 * @param model 数据模型渲染器
	 * @param user  实体信息
	 */
	@Action(value = MapModel.class, url = "update.htm")
	public void update(MapModel model, User user) {
		userService.update(user);
	}

	/**
	 * 删除实体信息处理
	 * @param model 数据模型渲染器
	 * @param id    用户ID
	 */
	@Action(value = MapModel.class, url = "delete.htm")
	public void delete(MapModel model, long id) {
		userService.deleteById(id);
	}
}
