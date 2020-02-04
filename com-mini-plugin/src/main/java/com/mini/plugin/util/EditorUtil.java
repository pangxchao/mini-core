package com.mini.plugin.util;

import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.testFramework.LightVirtualFile;

import static java.util.Objects.requireNonNull;

public class EditorUtil {
	private static final String TEMPLATE = "Mini-Code.groovy";
	
	public static Editor createEditor(Project project) {
		// 创建文件类型对象
		FileType fileType = FileTypeManager.getInstance().getFileTypeByExtension("groovy");
		// 获取编辑器工厂
		EditorFactory editorFactory = EditorFactory.getInstance();
		// 创建文件工厂对象
		PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
		// 创建文件对象
		PsiFile psiFile = psiFileFactory.createFileFromText(TEMPLATE, fileType, "", 0, true);
		// 标识为模板，让 Velocity 跳过语法校验
		psiFile.getViewProvider().putUserData(FileTemplateManager.DEFAULT_TEMPLATE_PROPERTIES,
			FileTemplateManager.getInstance(project).getDefaultProperties());
		// 创建文档对象
		Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);
		// 创建编辑框
		Editor editor = editorFactory.createEditor(requireNonNull(document), //
			project, psiFile.getVirtualFile(), false);
		// 编辑器设置
		EditorSettings editorSettings = editor.getSettings();
		// 关闭标记位置（断点位置）
		editorSettings.setLineMarkerAreaShown(false);
		// 支持代码折叠
		editorSettings.setFoldingOutlineShown(true);
		// 附加行，附加列（提高视野）
		editorSettings.setAdditionalColumnsCount(3);
		editorSettings.setAdditionalLinesCount(3);
		// 关闭缩减指南
		editorSettings.setIndentGuidesShown(false);
		// 显示行号
		editorSettings.setLineNumbersShown(true);
		// 不显示换行符号
		editorSettings.setCaretRowShown(false);
		// 关闭虚拟空间
		editorSettings.setVirtualSpace(false);
		// 自动换行
		editorSettings.setUseSoftWraps(true);
		// 默认只读
		document.setReadOnly(true);
		return editor;
	}
}
