package com.mini.plugin.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.mini.plugin.ui.GenerateCodeDialog;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public class GenerateCode extends AnAction implements EventListener {
	
	@Override
	public void actionPerformed(@NotNull AnActionEvent event) {
		PsiElement e = event.getData(CommonDataKeys.PSI_ELEMENT);
		Project project = GenerateCode.getEventProject(event);
		if (project == null || !(e instanceof DbTable)) {
			setDisabledAndHidden(event);
			return;
		}
		// 弹出代码生成配置窗口
		new GenerateCodeDialog(project, (DbTable) e)
			.setVisible(true);
	}
	
	@Override
	public synchronized void update(@NotNull AnActionEvent event) {
		PsiElement e = event.getData(CommonDataKeys.PSI_ELEMENT);
		Project project = GenerateCode.getEventProject(event);
		if (project == null || !(e instanceof DbTable)) {
			setDisabledAndHidden(event);
		}
	}
	
	private void setDisabledAndHidden(@NotNull AnActionEvent event) {
		event.getPresentation().setEnabledAndVisible(false);
	}
}