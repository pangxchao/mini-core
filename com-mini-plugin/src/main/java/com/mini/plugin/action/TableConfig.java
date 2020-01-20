package com.mini.plugin.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.mini.plugin.ui.TableConfigDialog;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;
import java.util.Optional;

public class TableConfig extends AnAction implements EventListener {
	
	@Override
	public void actionPerformed(@NotNull AnActionEvent event) {
		// 获取当前选中的项目并验证是否为空
		Project project = getEventProject(event);
		if (project == null) return;
		
		Optional.ofNullable(event.getData(LangDataKeys.PSI_ELEMENT))
			.filter(element -> (element instanceof DbTable))
			.map(element -> (DbTable) element)
			.ifPresent(table -> new TableConfigDialog(project, table)
				.setVisible(true));
	}
}