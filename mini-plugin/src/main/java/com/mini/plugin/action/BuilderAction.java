package com.mini.plugin.action;

import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.mini.plugin.handler.BuilderHandler;

/**
 * Builder Action
 *
 * @author xchao
 */
public final class BuilderAction extends BaseGenerateAction {
    public BuilderAction() {
        super(new BuilderHandler());
    }

//    private final BuilderHandler handler = new BuilderHandler();
//
//    @Override
//    protected boolean isValidForFile(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
//        return handler.isValidFor(editor, file);
//    }
//
//    @NotNull
//    @Override
//    protected CodeInsightActionHandler getHandler() {
//        return handler;
//    }

}
