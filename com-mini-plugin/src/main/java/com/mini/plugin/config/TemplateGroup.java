package com.mini.plugin.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.TreeMap;

import static com.mini.plugin.config.Settings.DEFAULT_NAME;

/**
 * 模板分组信息
 * @author xchao
 */
public final class TemplateGroup extends AbstractClone<TemplateGroup> implements Serializable {
	public static final TypeReference<TreeMap<String, TemplateGroup>> TYPE = new TypeReference<TreeMap<String, TemplateGroup>>() {};
	private TreeMap<String, Template> templateMap;
	private String groupName;
	
	public TemplateGroup setTemplateMap(TreeMap<String, Template> templateMap) {
		this.templateMap = templateMap;
		return this;
	}
	
	public TemplateGroup setGroupName(String groupName) {
		this.groupName = groupName;
		return this;
	}
	
	public TreeMap<String, Template> getTemplateMap() {
		if (TemplateGroup.this.templateMap == null) {
			templateMap = new TreeMap<>();
		}
		return templateMap;
	}
	
	@NotNull
	public synchronized String getGroupName() {
		if (StringUtil.isEmpty(groupName)) {
			groupName = DEFAULT_NAME;
		}
		return groupName;
	}
	
	public TemplateGroup addTemplate(@NotNull Template template) {
		getTemplateMap().put(template.getName(), template);
		return this;
	}
	
	@Nullable
	public final Template getTemplate(String templateName) {
		if (this.templateMap == null) return null;
		return templateMap.get(templateName);
	}
	
	@NotNull
	@Override
	public final synchronized TemplateGroup clone() {
		final TemplateGroup info = new TemplateGroup();
		info.setTemplateMap(new TreeMap<>(getTemplateMap()));
		info.setGroupName(groupName);
		return info;
	}
}
