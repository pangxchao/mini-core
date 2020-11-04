@file:Suppress("unused")

package com.mini.plugin.util

import com.intellij.codeInsight.actions.OptimizeImportsProcessor
import com.intellij.codeInsight.actions.ReformatCodeProcessor
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Computable
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager

object PsiFileUtil {

    /**
     * 创建子目录
     * @param project 文件对象
     * @param parent  父级目录
     * @param dirName 子目录
     * @return 目录对象
     */
    @JvmStatic
    fun createChildDirectory(project: Project, parent: VirtualFile, dirName: Array<String>): VirtualFile {
        var file: VirtualFile = parent
        for (item in dirName) {
            file = createChildDirectory(project, file, item)
        }
        return file
    }

    /**
     * 创建子目录
     * @param project 文件对象
     * @param parent  父级目录
     * @param dirName 子目录
     * @return 目录对象
     */
    @JvmStatic
    fun createChildDirectory(project: Project, parent: VirtualFile, dirName: String): VirtualFile {
        return WriteCommandAction.runWriteCommandAction(project, Computable computable@{
            return@computable VfsUtil.createDirectoryIfMissing(parent, dirName)
        })
    }

    /**
     * 创建子文件
     * @param project  项目对象
     * @param parent   父级目录
     * @param fileName 子文件名
     * @return 文件对象
     */
    @JvmStatic
    fun createChildFile(project: Project, parent: VirtualFile, fileName: String): VirtualFile? {
        return WriteCommandAction.runWriteCommandAction(project, Computable<VirtualFile> computable@{
            return@computable PsiManager.getInstance(project).findDirectory(parent)?.let {
                return@let it.findFile(fileName) ?: it.createFile(fileName)
            }?.virtualFile
        })
    }

    /**
     * 设置文件内容
     * @param project 项目对象
     * @param file    文件
     * @param text    文件内容
     * @return 覆盖后的文档对象
     */
    @JvmStatic
    fun writeFileContent(project: Project, file: VirtualFile, text: String): Document? {
        return FileDocumentManager.getInstance().getDocument(file)?.apply {
            WriteCommandAction.runWriteCommandAction(project) { setText(text) }
            PsiDocumentManager.getInstance(project).commitDocument(this)
        }
    }

    /**
     * 格式化虚拟文件
     * @param project     项目对象
     * @param file 虚拟文件
     */
    @JvmStatic
    fun reformatFile(project: Project, file: VirtualFile) {
        PsiManager.getInstance(project).findFile(file)?.let {
            reformatFile(project, listOf(it))
        }
    }

    /**
     * 执行格式化
     * @param project     项目对象
     * @param fileList 文件列表
     */
    @JvmStatic
    fun reformatFile(project: Project, fileList: List<PsiFile>) {
        fileList.takeIf { it.isNotEmpty() }?.toTypedArray().let { files ->
            ReformatCodeProcessor(project, files, null, false).let {
                OptimizeImportsProcessor(it) // 优化导入
                it.run() // 执行该操作
            }
        }
    }
}