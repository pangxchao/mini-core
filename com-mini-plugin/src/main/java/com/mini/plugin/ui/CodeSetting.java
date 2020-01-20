package com.mini.plugin.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.mini.plugin.config.GroupCode;
import com.mini.plugin.config.Settings;
import org.jetbrains.annotations.Nls;

import java.util.Map;

import static org.jetbrains.annotations.Nls.Capitalization.Title;

public final class CodeSetting extends BasicsSetting<GroupCode> implements Configurable {
	
	public CodeSetting(Settings settings, Project project) {
		super(settings, project);
	}
	
	@Override
	protected void setData(Settings settings, Map<String, GroupCode> data) {
		settings.setCodeGroup(data);
	}
	
	@Override
	protected Map<String, GroupCode> getData(Settings settings) {
		return settings.getCodeGroup();
	}
	
	@Override
	protected void setName(Settings settings, String name) {
		settings.setCodeGroupName(name);
	}
	
	@Override
	protected String getName(Settings settings) {
		return settings.getCodeGroupName();
	}
	
	@Override
	protected GroupCode createInstance() {
		return new GroupCode();
	}
	
	@Override
	@Nls(capitalization = Title)
	public String getDisplayName() {
		return "Code Template";
	}
	
	
}
