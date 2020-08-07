package com.mini.plugin.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.mini.plugin.ui.GenerateCodeDialog;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.intellij.openapi.actionSystem.LangDataKeys.PSI_ELEMENT_ARRAY;
import static java.util.Arrays.stream;

public class GenerateCode extends AnAction implements EventListener {
	
	@Override
	@SuppressWarnings("DuplicatedCode")
	public void actionPerformed(@NotNull AnActionEvent event) {
		PsiElement[] elements = event.getData(PSI_ELEMENT_ARRAY);
		final Project project = GenerateCode.getEventProject(event);
		if (project == null || elements == null || elements.length <= 0) {
			setDisabledAndHidden(event);
			return;
		}
		List<DbTable> tableList = stream(elements).filter(Objects::nonNull)
				.filter(element -> element instanceof DbTable)
				.map(element -> (DbTable) element)
				.collect(Collectors.toList());
		if (tableList.isEmpty()) {
			setDisabledAndHidden(event);
			return;
		}
		// 弹出代码生成配置窗口
		new GenerateCodeDialog(project, tableList).setVisible(true);
	}
	
	@Override
	@SuppressWarnings("DuplicatedCode")
	public synchronized void update(@NotNull AnActionEvent event) {
		PsiElement[] elements = event.getData(PSI_ELEMENT_ARRAY);
		final Project project = GenerateCode.getEventProject(event);
		if (project == null || elements == null || elements.length <= 0) {
			setDisabledAndHidden(event);
			return;
		}
		List<DbTable> tableList = stream(elements).filter(Objects::nonNull)
				.filter(element -> element instanceof DbTable)
				.map(element -> (DbTable) element)
				.collect(Collectors.toList());
		if (tableList.isEmpty()) {
			setDisabledAndHidden(event);
		}
	}
	
	private void setDisabledAndHidden(@NotNull AnActionEvent event) {
		event.getPresentation().setEnabledAndVisible(false);
	}
}