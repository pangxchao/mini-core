package com.mini.plugin.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.mini.plugin.config.GroupDB;
import com.mini.plugin.config.Settings;
import org.jetbrains.annotations.Nls;

import java.util.Map;

import static com.mini.plugin.config.GroupDB.builder;
import static org.jetbrains.annotations.Nls.Capitalization.Title;

public final class DBSetting extends BasicsSetting<GroupDB> implements Configurable {
	
	public DBSetting(Settings settings, Project project) {
		super(settings, project);
	}
	
	@Override
	protected void setData(Settings settings, Map<String, GroupDB> data) {
		settings.setDbGroup(data);
	}
	
	@Override
	protected Map<String, GroupDB> getData(Settings settings) {
		return settings.getDbGroup();
	}
	
	
	@Override
	protected void setName(Settings settings, String name) {
		settings.setDbGroupName(name);
	}
	
	
	@Override
	protected GroupDB createInstance(String name) {
		return builder().name(name).build();
	}
	
	@Override
	protected String getName(Settings settings) {
		return settings.getDbGroupName();
	}
	
	
	@Override
	@Nls(capitalization = Title)
	public String getDisplayName() {
		return "DB Template";
	}
}
