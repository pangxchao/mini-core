package com.mini.plugin.ui;

import com.intellij.openapi.project.Project;
import com.mini.plugin.config.AbstractGroup;
import com.mini.plugin.config.Settings;
import com.mini.plugin.config.Template;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BasicsSetting<T extends AbstractGroup<Template, T>> extends JPanel {
	private final Map<String, T> data = new LinkedHashMap<>();
	private final BaseTemplatePanel templatePanel;
	private Settings settings;
	private boolean modified;
	private String groupName;
	
	public BasicsSetting(Settings settings, Project project) {
		this.setLayout(new BorderLayout(0, 0));
		this.groupName = getName(settings);
		this.settings  = settings;
		Optional.ofNullable(getData(settings)).ifPresent(g ->
			g.forEach((k, v) -> data.put(k, v.copy())));
		// 创建模板编辑面板
		this.templatePanel = new BaseTemplatePanel(project) {
			protected synchronized void renameHandler(String name, String newName) {
				Optional.ofNullable(getTemplate(name)).ifPresent(t -> {
					BasicsSetting.this.modified = true;
					t.setName(newName);
				});
			}
			
			protected synchronized void modifiedHandler(String name, String text) {
				Optional.ofNullable(getTemplate(name)).ifPresent(t -> {
					modified = modified || !Objects.equals(text, t.getCode());
					t.setCode(text);
				});
			}
			
			protected synchronized void copyHandler(String name, String newName) {
				Optional.ofNullable(data.get(groupName)).map(AbstractGroup //
					::getElements).ifPresent(templates -> { //
					Optional.ofNullable(getTemplate(name)).ifPresent(t -> {
						templates.add(Template.builder().name(newName)
							.code(t.getCode()).build());
						BasicsSetting.this.modified = true;
					});
				});
			}
			
			protected synchronized void moveDownHandler(String name, int index) {
				Optional.ofNullable(data.get(groupName)).map(AbstractGroup //
					::getElements).ifPresent(templates -> {
					if (index < 0 || index >= templates.size() - 2) {
						return;
					}
					Collections.swap(templates, index, index + 1);
					BasicsSetting.this.modified = true;
				});
			}
			
			protected synchronized void moveUpHandler(String name, int index) {
				Optional.ofNullable(data.get(groupName)).map(AbstractGroup //
					::getElements).ifPresent(templates -> {
					if (index <= 0 || index >= templates.size() - 1) {
						return;
					}
					Collections.swap(templates, index - 1, index);
					BasicsSetting.this.modified = true;
				});
			}
			
			@Nullable
			protected synchronized Template getTemplate(String name) {
				return BasicsSetting.this.getTemplate(name);
			}
			
			protected synchronized void deleteHandler(String name) {
				Optional.ofNullable(data.get(groupName)).map(AbstractGroup //
					::getElements).ifPresent(templates -> {
					templates.remove(getTemplate(name));
					BasicsSetting.this.modified = true;
				});
			}
			
			@NotNull
			protected synchronized List<String> getTemplateData() {
				return getTemplateNames();
			}
			
			protected synchronized void addHandler(String name) {
				Optional.ofNullable(data.get(groupName)).map(AbstractGroup //
					::getElements).ifPresent(templates -> {
					BasicsSetting.this.modified = true;
					templates.add(Template.builder()
						.name(name).code("")
						.build());
				});
			}
		};
		// 将主面板添加到当前面板的中心
		this.add(templatePanel, BorderLayout.CENTER);
		
		// 创建分组面板并将分组面板添加到当前面板的上面
		add(new BaseGroupPanel<Template, T>(getName(settings)) {
			protected synchronized void renameHandler(String name, String newName) {
				Optional.ofNullable(data.get(name)).ifPresent(mapper -> {
					BasicsSetting.this.data.remove(name);
					BasicsSetting.this.modified = true;
					data.put(newName, mapper);
				});
			}
			
			protected synchronized void copyHandler(String name, String newName) {
				Optional.ofNullable(data.get(name)).ifPresent(mapper -> {
					BasicsSetting.this.modified = true;
					data.put(newName, mapper.copy());
				});
			}
			
			@NotNull
			protected synchronized final Map<String, T> getGroupData() {
				return BasicsSetting.this.data;
			}
			
			protected synchronized void selectedHandler(String name) {
				Optional.of(templatePanel).ifPresent(panel -> {
					if (!Objects.equals(groupName, name)) {
						groupName = name;
						modified  = true;
					}
					panel.resetModelData(null);
				});
			}
			
			protected synchronized void deleteHandler(String name) {
				BasicsSetting.this.data.remove(name);
				BasicsSetting.this.modified = true;
			}
			
			protected synchronized void addHandler(String name) {
				BasicsSetting.this.modified = true;
				data.put(name, createInstance());
			}
		}, BorderLayout.NORTH);
	}
	
	@NotNull
	private synchronized List<String> getTemplateNames() {
		return Optional.ofNullable(data.get(groupName))
			.map(AbstractGroup::getElements)
			.map(list -> list.stream().map(Template::getName)
				.collect(Collectors.toList()))
			.orElse(new ArrayList<>());
	}
	
	private synchronized Template getTemplate(String name) {
		return Optional.ofNullable(data.get(groupName))
			.map(AbstractGroup::getElements)
			.flatMap(list -> list.stream().filter(t -> //
				Objects.equals(name, t.getName())).findAny())
			.orElse(null);
	}
	
	// 重置到默认状态
	public synchronized final void reset(Settings settings) {
		BasicsSetting.this.data.clear();
		Optional.ofNullable(getData(settings)).ifPresent(g ->
			g.forEach((k, v) -> data.put(k, v.copy())));
		modified = true;
	}
	
	// 重置操作
	public synchronized final void reset() {
		this.reset(settings);
		modified = false;
	}
	
	// 保存操作
	public synchronized final void apply() {
		setName(settings, groupName);
		setData(settings, data);
		modified = false;
	}
	
	public final JComponent createComponent() {
		return this;
	}
	
	public final boolean isModified() {
		return modified;
	}
	
	protected abstract void setData(Settings settings, Map<String, T> data);
	
	protected abstract void setName(Settings settings, String name);
	
	protected abstract Map<String, T> getData(Settings settings);
	
	protected abstract String getName(Settings settings);
	
	protected abstract T createInstance();
	
	
}
