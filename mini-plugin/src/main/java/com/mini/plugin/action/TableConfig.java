package com.mini.plugin.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.mini.plugin.ui.TableConfigDialog;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PSI_ELEMENT;
import static com.mini.plugin.util.TableUtil.readDbTableFromSetting;

public class TableConfig extends AnAction implements EventListener {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final PsiElement e = event.getData(PSI_ELEMENT);
        final Project project = getEventProject(event);
        if (project == null || !(e instanceof DbTable)) {
            setDisabledAndHidden(event);
            return;
        }
        com.mini.plugin.state.DbTable table = readDbTableFromSetting((DbTable) e);
        TableConfigDialog dialog = new TableConfigDialog(table);
        dialog.setVisible(true);
    }

    public final void update(@NotNull AnActionEvent event) {
        final PsiElement e = event.getData(PSI_ELEMENT);
        final Project project = getEventProject(event);
        if (project == null || !(e instanceof DbTable)) {
            setDisabledAndHidden(event);
        }
    }

    private void setDisabledAndHidden(AnActionEvent event) {
        event.getPresentation().setEnabledAndVisible(false);
    }
}