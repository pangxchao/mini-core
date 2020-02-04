package com.mini.plugin.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileSaverDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.EventListener;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.intellij.openapi.actionSystem.LangDataKeys.PSI_ELEMENT_ARRAY;
import static com.intellij.openapi.fileChooser.FileChooser.chooseFile;
import static com.mini.plugin.util.Constants.CHOOSE_DIRECTORY;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.DictionariesUtil.generator;
import static com.mini.plugin.util.TableUtil.createTableInfo;
import static java.util.Optional.ofNullable;

public class Dictionaries extends AnAction implements EventListener {
	@Override
	public void actionPerformed(@NotNull AnActionEvent event) {
		// 获取当前选中的项目并验证是否为空
		Project project = getEventProject(event);
		if (project == null) return;
		
		Optional.of(new FileSaverDescriptor(TITLE_INFO, CHOOSE_DIRECTORY))
			.map(des -> chooseFile(des, project, null)).ifPresent(file -> {
			PsiElement[] elements = event.getData(PSI_ELEMENT_ARRAY);
			ofNullable(elements).map(Arrays::stream).map(array ->
				array.filter(ele -> (ele instanceof DbTable))
					.map(ele -> (DbTable) ele)
					.map(t -> createTableInfo(project, t))
					.collect(Collectors.toList()))
				.filter(list -> !list.isEmpty())
				.ifPresent(list -> generator(list, file));
		});
	}
}
