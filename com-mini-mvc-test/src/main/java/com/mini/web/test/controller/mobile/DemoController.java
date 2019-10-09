package com.mini.web.test.controller.mobile;

import static com.mini.validate.ValidateUtil.sendError;

import java.io.*;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.Part;

import com.mini.jdbc.util.Paging;
import com.mini.security.digest.MD5;
import com.mini.util.PKGenerator;
import com.mini.validate.ValidateUtil;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.StreamModel;
import com.mini.web.test.entity.User;
import com.mini.web.test.service.UserService;
import com.mini.web.test.util.FileGenerator;

/**
 * 该组接口是Back/Demo 与 Front/Demo 的功能扩展接口和文件下载的一些示例演示
 * @author xchao
 */
@Singleton
@Controller(path = "mobile/demo", url = "mobile/demo")
public class DemoController {

	@Inject
	private UserService userService;

	/**
	 * 实体列表查询方法
	 * @param model  数据模型渲染器
	 * @param paging 数据分页工具
	 */
	@Action(value = MapModel.class, url = "list.htm")
	public void list(MapModel model, Paging paging) {
		model.addData("data", userService.queryAll(paging));
		model.addData("paging", paging);
	}

	/**
	 * 添加实体处理
	 * @param model 数据模型渲染器
	 * @param user  实体信息
	 */
	@Action(value = MapModel.class, url = "insert.htm")
	public void insert(MapModel model, User user) throws Exception {
		ValidateUtil.isNotNull(user, 600, "用户信息为空，处理失败");
		ValidateUtil.isNotBlank(user.getName(), 600, "用户名不能为空");
		ValidateUtil.isNotBlank(user.getPassword(), 600, "用户密码不能为空");
		ValidateUtil.isNotBlank(user.getPhone(), 600, "用户手机号不能为空");
		// 加密密码和生成用户ID
		user.setPassword(MD5.encode(user.getPassword()));
		user.setCreateTime(new Date());
		user.setId(PKGenerator.id());
		// 添加用户信息到数据库
		if (userService.insert(user) != 1) {
			sendError(600, "添加用户信息失败");
			return;
		}
		// 返回用户信息到客户端
		model.addData("id", String.valueOf(user.getId()));
	}

	/**
	 * 修改实体处理
	 * @param model 数据模型渲染器
	 * @param user  用户信息
	 */
	@Action(value = MapModel.class, url = "update.htm")
	public void update(MapModel model, User user) {
		ValidateUtil.isNotNull(user, 600, "用户信息为空，处理失败");
		ValidateUtil.isNotBlank(user.getName(), 600, "用户名不能为空");
		ValidateUtil.isNotBlank(user.getPhone(), 600, "用户手机号不能为空");

		User info = userService.queryById(user.getId());
		ValidateUtil.isNotNull(info, 600, "用户信息不存在");
		user.setCreateTime(info.getCreateTime());
		user.setPassword(info.getPassword());
		if (userService.update(user) != 1) {
			sendError(600, "修改用户信息失败");
		}
	}

	/**
	 * 删除用户信息
	 * @param model  数据模型渲染器
	 * @param idList 要删除的数据ID List
	 */
	@Action(value = MapModel.class, url = "delete.htm")
	public void delete(MapModel model, long[] idList) {
		ValidateUtil.is(idList != null && idList.length > 0, 600, "未选中数据");
		userService.delete(idList);
	}

	/**
	 * 上传文件
	 * @param model 数据模型渲染器
	 * @param file  文件内容
	 * @param mark  加密串
	 * @param time  参数生成时间
	 */
	@Action(value = MapModel.class, url = "upload.htm")
	public void upload(MapModel model, Part file, String mark, long time) throws IOException {
		ValidateUtil.isNotNull(file, 600, "上传文件不能为空");
		ValidateUtil.isNotBlank(mark, 600, "加密串不能为空");

		//// 设置参数过期时间为10分钟，这块用于验证参数是否有效的代码暂时不用
		//long now = System.currentTimeMillis() - 1000 * 60 * 10;
		//ValidateUtil.is(time > now, 600, "参数过期");
		//ValidateUtil.is(equalsIgnoreCase(mark, encode(R.getMark() + time)), 600, "参数无效");

		// 保存文件
		String dbPath = FileGenerator.getDBPath(file.getSubmittedFileName());
		file.write(FileGenerator.getPublicFullPath(dbPath));

		// 设置返回数据
		model.addData("contentType", file.getContentType());
		model.addData("name", file.getSubmittedFileName());
		model.addData("length", file.getSize());
		model.addData("url", dbPath);
	}

	/**
	 * 文件下载
	 * @param model 数据模型渲染器
	 * @param mark  参数
	 */
	@Action(value = StreamModel.class, url = "download.htm")
	public void download(StreamModel model, String mark) throws FileNotFoundException {
		File file = new File("D:/My Docs/图片/image/psb2e.jpeg");
		InputStream input = new FileInputStream(file);
		model.setFileName("Ling.psb2e.jpeg");
		// 告诉客户端，该接口不支持断点续传（默认：true）
		model.setAcceptRangesSupport(false);
		model.setInputStream(input);
	}

	/**
	 * 文件下载(分片下载/断点续传)
	 * @param model 数据模型渲染器
	 * @param mark  参数
	 */
	@Action(value = StreamModel.class, url = "slice.htm")
	public void slice(StreamModel model, String mark) {
		byte[] text = "0123456789\r\nabc\n\tdefghijklmnopqrstuvwxyz-=.,/".getBytes();
		ByteArrayInputStream input = new ByteArrayInputStream(text);
		model.setFileName("demo.txt");
		model.setContentLength(text.length);
		model.setWriteCallback((out, start, end) -> {
			System.out.println("-------------------------------");
			System.out.println("start:" + start + ", end: " + end);
			if (end < 0) {
				input.transferTo(out);
				return;
			}
			int length = (int) (end - start);
			out.write(text, (int) start, length);
		});
	}
}
