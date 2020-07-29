package com.mini.plugin.config;

import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * 模板信息
 * @author xchao
 */
public final class Template extends AbstractClone<Template> implements Serializable {
	private String content;
	private String name;
	
	public Template setContent(String content) {
		this.content = content;
		return this;
	}
	
	public Template setName(String name) {
		this.name = name;
		return this;
	}
	
	@NotNull
	public synchronized String getContent() {
		if (StringUtil.isEmpty(content)) {
			content = "";
		}
		return content;
	}
	
	@NotNull
	public synchronized String getName() {
		if (StringUtil.isEmpty(name)) {
			name = "";
		}
		return name;
	}
	
	@NotNull
	@Override
	public final synchronized Template clone() {
		final Template info = new Template();
		info.setContent(content);
		info.setName(name);
		return info;
	}
}
