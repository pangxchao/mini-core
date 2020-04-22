package com.mini.plugin.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.mini.plugin.config.Settings;
import com.mini.plugin.config.Template;
import com.mini.plugin.config.TemplateGroup;
import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;
import static com.mini.plugin.config.Settings.DEFAULT_NAME;
import static java.util.Optional.ofNullable;
import static org.jetbrains.annotations.Nls.Capitalization.Title;

public final class TemplateSetting extends JPanel implements Configurable {
	private TreeMap<String, TemplateGroup> data;
	private final TemplatePanel templatePanel;
	private Settings settings;
	private boolean modified;
	private String groupName;
	
	private synchronized void resetModelData() throws Error {
		templatePanel.resetModelData(groupName);
	}
	
	public TemplateSetting(Settings settings, Project project) {
		this.setLayout(new BorderLayout(0, 0));
		groupName = settings.getTemplateGroupName();
		data = settings.getTemplateGroupMap();
		this.settings = settings;
		// 创建模板编辑面板并将主面板添加到当前面板的中心
		TemplateSetting.this.add((templatePanel = new TemplatePanel(project) {
			protected synchronized void renameHandler(String name, String newName) {
				ofNullable(data.get(groupName)).map(TemplateGroup::getTemplateMap).ifPresent(map -> {
					Template template = map.get(name);
					if (template == null) return;
					// 设置新模板名称并添加到列表
					template.setName(newName);
					map.put(newName, template);
					// 从原来的列表删除旧模板
					map.remove(name);
					modified = true;
				});
			}
			
			protected synchronized void modifiedHandler(String name, String text) {
				ofNullable(data.get(groupName)).map(TemplateGroup::getTemplateMap).ifPresent(map -> {
					Template template = map.get(defaultIfEmpty(name, ""));
					if (template == null || StringUtil.isEmpty(name)) {
						return;
					}
					if (template.getContent().equals(text)) {
						return;
					}
					template.setContent(text);
					map.put(name, template);
					modified = true;
				});
			}
			
			protected synchronized void copyHandler(String name, String newName) {
				ofNullable(data.get(groupName)).map(TemplateGroup::getTemplateMap).ifPresent(map -> { //
					Template template = map.get(name);
					if (template == null) return;
					// 复制新模板并添加到列表中
					Template t = template.clone();
					t.setName(newName);
					map.put(newName, t);
					modified = true;
				});
			}
			
			@Nullable
			protected synchronized Template getTemplate(String name) {
				return Optional.ofNullable(data.get(groupName))
						.map(TemplateGroup::getTemplateMap)
						.map(map -> map.get(name))
						.orElse(null);
			}
			
			protected synchronized void deleteHandler(String name) {
				ofNullable(data.get(groupName)).map(TemplateGroup::getTemplateMap).ifPresent(map -> {
					map.remove(name);
					modified = true;
				});
			}
			
			@NotNull
			protected final synchronized java.util.List<String> getTemplateNames() {
				return Optional.ofNullable(data.get(groupName))
						.map(TemplateGroup::getTemplateMap)
						.map(TreeMap::keySet)
						.map(ArrayList::new)
						.orElse(new ArrayList<>());
			}
			
			protected synchronized void addHandler(String name) {
				ofNullable(data.get(groupName)).map(TemplateGroup::getTemplateMap).ifPresent(map -> {
					Template template = new Template();
					template.setName(name);
					template.setContent("");
					map.put(name, template);
					modified = true;
				});
			}
		}), BorderLayout.CENTER);
		
		// 创建分组面板并将分组面板添加到当前面板的上面
		TemplateSetting.this.add(new BaseGroupPanel<TemplateGroup>(groupName) {
			protected void renameHandler(String name, String newName) {
				ofNullable(data.get(name)).ifPresent(group -> {
					TemplateSetting.this.data.remove(name);
					TemplateSetting.this.modified = true;
					group.setGroupName(newName);
					data.put(newName, group);
					groupName = newName;
					resetModelData();
				});
			}
			
			protected void copyHandler(String name, String newName) {
				ofNullable(data.get(name)).ifPresent(group -> {
					TemplateSetting.this.modified = true;
					TemplateGroup g = group.clone();
					g.setGroupName(newName);
					data.put(newName, g);
					groupName = newName;
					resetModelData();
				});
			}
			
			@NotNull
			protected final Map<String, TemplateGroup> getGroupData() {
				return TemplateSetting.this.data;
			}
			
			protected void selectedHandler(String name) {
				if (!Objects.equals(groupName, name)) {
					groupName = name;
					modified = true;
					resetModelData();
				}
			}
			
			protected void deleteHandler(String name) {
				if (StringUtil.equals(DEFAULT_NAME, name)) {
					return;
				}
				TemplateSetting.this.data.remove(name);
				TemplateSetting.this.modified = true;
				if (!TemplateSetting.this.data.isEmpty()) {
					groupName = data.firstKey();
				} else groupName = DEFAULT_NAME;
				resetModelData();
			}
			
			protected void addHandler(String name) {
				TemplateGroup group = new TemplateGroup();
				group.setTemplateMap(new TreeMap<>());
				group.setGroupName(name);
				data.put(name, group);
				groupName = name;
				modified = true;
				resetModelData();
			}
		}, BorderLayout.NORTH);
		resetModelData();
	}
	
	// 重置到默认状态
	public synchronized final void resetToDefault() throws Error {
		TemplateSetting.this.reset();
		modified = true;
	}
	
	// 重置操作
	@Override
	public synchronized final void reset() {
		groupName = settings.getTemplateGroupName();
		data = settings.getTemplateGroupMap();
		modified = false;
		resetModelData();
	}
	
	// 保存操作
	@Override
	public synchronized final void apply() throws Error {
		settings.setTemplateGroupName(groupName);
		settings.setTemplateGroupMap(data);
		modified = false;
	}
	
	@Override
	public JComponent createComponent() {
		return this;
	}
	
	@Override
	@Nls(capitalization = Title)
	public String getDisplayName() {
		return "Template";
	}
	
	@Override
	public boolean isModified() {
		return modified;
	}
	
}
