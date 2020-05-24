package com.mini.test.controller;

import com.mini.core.jdbc.transaction.Transactional;
import com.mini.core.util.StringUtil;
import com.mini.core.validation.Validator;
import com.mini.core.validation.annotation.NotNull;
import com.mini.core.validation.annotation.Positive;
import com.mini.core.web.annotation.Action;
import com.mini.core.web.annotation.Controller;
import com.mini.core.web.model.JsonModel;
import com.mini.core.web.model.StreamModel;
import com.mini.core.web.util.ResponseCode;
import com.mini.test.R;
import com.mini.test.context.FileGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import static com.mini.core.util.FileUtil.getFileExt;
import static com.mini.core.validation.Validator.status;
import static org.apache.commons.lang3.StringUtils.replace;

@Slf4j
@Singleton
@Controller(url = "/", path = "/")
public class FileController implements ResponseCode {
	
	/**
	 * 上传接口
	 * @param model      数据模型
	 * @param file       上传文件内容
	 * @param fileId     文件ID
	 * @param offset     文件偏移量
	 * @param fileLength 文件总长度
	 * @param response   HttpServletResponse
	 */
	@Transactional
	@Action(value = JsonModel.class)
	public void upload(JsonModel model, @NotNull(message = "上传文件为空") Part file, String fileId,
			@Positive(message = "参数错误", zero = true) long offset, long fileLength,
			HttpServletResponse response)
			throws Exception {
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		try {
			// 第一次上传
			if (StringUtil.isBlank(fileId)) {
				// 返回已上传大小
				fileLength = file.getSize();
				// 获取文件路径并保存
				String suffix = getFileExt(file.getSubmittedFileName());
				fileId = FileGenerator.getDBPath(suffix);
				file.write(FileGenerator.getFullPath(fileId));
				return;
			}
			File fileInfo = new File(FileGenerator.getFullPath(fileId));
			Validator.status(VERIFY).is(fileInfo.exists() && fileInfo.isFile());
			if (fileInfo.length() < offset) {
				Validator.status(VERIFY).send();
			}
			try (FileOutputStream out = new FileOutputStream(fileInfo, true)) {
				try (InputStream in = file.getInputStream()) {
					long skip = fileInfo.length() - offset;
					if (in.skip(skip) < skip) {
						Validator.status(VERIFY).send();
					}
					in.transferTo(out);
				}
			}
		} finally {
			// 设置返回数据
			model.addData("url", fileId);
			model.addData("length", fileLength);
			model.addData("cloudId", R.getCloudId());
		}
	}
	
	/**
	 * 文件下载
	 * @param model  数据模型
	 * @param fileId 文件ID
	 */
	/**
	 * 文件下载
	 * @param model    数据模型渲染器
	 * @param request  HttpServletRequest 对象
	 * @param response HttpServletResponse 对象
	 */
	@SneakyThrows
	@Action(value = StreamModel.class, url = {"2020/**", "2021/**"}, suffix = "")
	public void download(StreamModel model, HttpServletRequest request, HttpServletResponse response) {
		String fileId = replace(request.getRequestURI(), request.getContextPath() + "/", "", 1);
		final File file = new File(FileGenerator.getFullPath(fileId));
		status(NOT_FOUND).is(file.exists() && file.isFile());
		// 设置下载文件的头信息
		model.setContentType("application/octet-stream");
		model.setLastModified(file.lastModified());
		model.setContentLength(file.length());
		model.setFileName(file.getName());
		// 下载文件
		model.setWriteCallback((out, start, end) -> {
			try (FileInputStream stream = new FileInputStream(file)) {
				if (stream.skip(start) < start) {
					status(INTERNAL_SERVER_ERROR).send();
				}
				long remaining = end - start + 1;
				if (remaining <= 0) {
					return;
				}
				int nr, MAX_SKIP_BUFFER_SIZE = 2048;
				int size = (int) Math.min(MAX_SKIP_BUFFER_SIZE, remaining);
				byte[] buffer = new byte[size];
				while (remaining > 0) {
					nr = stream.read(buffer, 0, (int) Math.min(size, remaining));
					if (nr < 0) {
						break;
					}
					out.write(buffer, 0, nr);
					remaining -= nr;
				}
			}
		});
	}
}
