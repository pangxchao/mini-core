@file:Suppress("unused")

package com.mini.plugin.util

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project

object EditorUtil {
    @JvmStatic
    @JvmOverloads
    fun createEditor(project: Project, content: String = ""): Editor {
        return FileTypeManager.getInstance()?.getFileTypeByExtension("groovy")?.let EditorLet@{ fileType: FileType ->
            return@EditorLet EditorFactory.getInstance()?.createDocument(content)?.let { doc: Document ->
                EditorFactory.getInstance()?.createEditor(doc, project, fileType, false)?.apply {
                    // 关闭标记位置（断点位置）
                    this.settings.isLineMarkerAreaShown = false
                    // 支持代码折叠
                    this.settings.isFoldingOutlineShown = true
                    // 附加行，附加列（提高视野）
                    this.settings.additionalColumnsCount = 3
                    this.settings.additionalLinesCount = 3
                    // 关闭缩减指南
                    this.settings.isIndentGuidesShown = false
                    // 显示行号
                    this.settings.isLineNumbersShown = true
                    // 不显示换行符号
                    this.settings.isCaretRowShown = false
                    // 关闭虚拟空间
                    this.settings.isVirtualSpace = false
                    // 自动换行
                    this.settings.isUseSoftWraps = true
                }
            }
        } ?: throw RuntimeException("Editor create fail")
    }

}