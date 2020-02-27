package com.mini.plugin.action;

import com.intellij.database.psi.DbElement;
import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.fileChooser.FileSaverDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.intellij.database.model.ObjectKind.TABLE;
import static com.intellij.openapi.fileChooser.FileChooser.chooseFile;
import static com.mini.plugin.util.Constants.CHOOSE_DIRECTORY;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.CreationsUtil.generator;

public class Creations extends AnAction implements EventListener {
	@Override
	public void actionPerformed(@NotNull AnActionEvent event) {
		// 获取当前选中的项目并验证是否为空
		Project project = getEventProject(event);
		if (project == null) return;

		Optional.of(new FileSaverDescriptor(TITLE_INFO, CHOOSE_DIRECTORY))
				.map(des -> chooseFile(des, project, null)).ifPresent(file -> {
			List<DbTable> elements = Optional.of(event)
					.map(e -> e.getData(LangDataKeys.PSI_ELEMENT))
					.filter(element -> (element instanceof DbElement))
					.map(element -> (DbElement) element)
					.map(element -> element.getDasChildren(TABLE))
					.map(JBIterable::toList)
					.orElse(new ArrayList<>())
					.stream()
					.filter(element -> element instanceof DbTable)
					.map(element -> (DbTable) element)
					.collect(Collectors.toList());
			generator(elements, file);
		});
	}

	@Override
	public synchronized void update(@NotNull AnActionEvent event) {
		List<DbTable> elements = Optional.of(event)
				.map(e -> e.getData(LangDataKeys.PSI_ELEMENT))
				.filter(element -> (element instanceof DbElement))
				.map(element -> (DbElement) element)
				.map(element -> element.getDasChildren(TABLE))
				.map(JBIterable::toList)
				.orElse(new ArrayList<>())
				.stream()
				.filter(element -> element instanceof DbTable)
				.map(element -> (DbTable) element)
				.collect(Collectors.toList());
		Project project = Creations.getEventProject(event);
		if (project == null || elements.isEmpty()) {
			setDisabledAndHidden(event);
		}
	}

	private void setDisabledAndHidden(@NotNull AnActionEvent event) {
		event.getPresentation().setEnabledAndVisible(false);
	}
}
