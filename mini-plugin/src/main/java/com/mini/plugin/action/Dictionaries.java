package com.mini.plugin.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileSaverDescriptor;
import com.intellij.openapi.project.Project;
import com.mini.plugin.util.TableUtil;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;
import java.util.List;
import java.util.Optional;

import static com.intellij.openapi.fileChooser.FileChooser.chooseFile;
import static com.mini.plugin.util.Constants.CHOOSE_DIRECTORY;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.DictionariesUtil.generator;

public class Dictionaries extends AnAction implements EventListener {
    @Override
    @SuppressWarnings("DuplicatedCode")
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = getEventProject(event);
        if (project == null) return;

        Optional.of(new FileSaverDescriptor(TITLE_INFO, CHOOSE_DIRECTORY))
                .map(des -> chooseFile(des, project, null)).ifPresent(file -> {
            List<DbTable> elements = TableUtil.getDbTableList(event);
            generator(elements, file);
        });
    }

    @Override
    public synchronized void update(@NotNull AnActionEvent event) {
        List<DbTable> elements = TableUtil.getDbTableList(event);
        Project project = Dictionaries.getEventProject(event);
        if (project == null || elements.isEmpty()) {
            setDisabledAndHidden(event);
        }
    }

    private void setDisabledAndHidden(@NotNull AnActionEvent event) {
        event.getPresentation().setEnabledAndVisible(false);
    }
}
