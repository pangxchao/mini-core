package com.mini.plugin.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.mini.plugin.ui.GenerateDialog;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;
import java.util.List;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PSI_ELEMENT;
import static com.mini.plugin.util.TableUtil.getDbTableListByEvent;

@SuppressWarnings("DuplicatedCode")
public class GenerateCodeAction extends AnAction implements EventListener {


    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final PsiElement e = event.getData(PSI_ELEMENT);
        final Project project = getEventProject(event);
        if (project == null || !(e instanceof DbTable)) {
            setDisabledAndHidden(event);
            return;
        }

        List<com.mini.plugin.state.DbTable> list = getDbTableListByEvent(event);
        GenerateDialog dialog = new GenerateDialog(project, list);
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