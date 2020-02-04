package com.mini.plugin.action;


import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.actions.BaseCodeInsightAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.mini.plugin.insert.BuilderInsertHandler;
import org.jetbrains.annotations.NotNull;

public class BuilderAction extends BaseCodeInsightAction {
	
	@NotNull
	@Override
	protected CodeInsightActionHandler getHandler() {
		return new BuilderInsertHandler();
	}
	
	@Override
	protected boolean isValidForFile(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
		return true;
	}
}
