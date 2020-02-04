package com.mini.plugin.insert;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class BuilderInsertHandler implements CodeInsightActionHandler {
	@Override
	public boolean startInWriteAction() {
		return false;
	}
	
	//private static boolean isApplicable(final PsiFile file, final Editor editor) {
	//	final List<PsiFieldMember> targetElements = collectFields(file, editor);
	//	return targetElements != null && !targetElements.isEmpty();
	//}
	
	@Override
	public void invoke(@NotNull final Project project, @NotNull final Editor editor, @NotNull final PsiFile file) {
		//final PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
		//final Document currentDocument = psiDocumentManager.getDocument(file);
		//if (currentDocument == null) {
		//	return;
		//}
		//
		//psiDocumentManager.commitDocument(currentDocument);
		//
		//if (!CodeInsightUtilBase.prepareEditorForWrite(editor)) {
		//	return;
		//}
		//
		//if (!FileDocumentManager.getInstance().requestWriting(editor.getDocument(), project)) {
		//	return;
		//}
		//
		//final List<PsiFieldMember> existingFields = collectFields(file, editor);
		//if (existingFields != null) {
		//	final List<PsiFieldMember> selectedFields = selectFieldsAndOptions(existingFields, project);
		//
		//	if (selectedFields == null || selectedFields.isEmpty()) {
		//		return;
		//	}
		//
		//	InnerBuilderGenerator.generate(project, editor, file, selectedFields);
		//}
	}
	
}
