@file:Suppress("RedundantExplicitType", "unused", "MemberVisibilityCanBePrivate")

package com.mini.plugin.util


import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.mini.plugin.config.Settings.instance
import com.mini.plugin.state.DbTable
import com.mini.plugin.util.PsiFileUtil.createChildDirectory
import com.mini.plugin.util.PsiFileUtil.createChildFile
import com.mini.plugin.util.PsiFileUtil.reformatFile
import com.mini.plugin.util.PsiFileUtil.writeFileContent
import groovy.lang.Binding
import groovy.lang.GroovyClassLoader
import groovy.lang.GroovyShell
import java.io.Serializable
import java.util.*

object GroovyUtil : Serializable, EventListener {
    @JvmStatic
    fun generate(template: String, tableInfo: DbTable, file: VirtualFile, project: Project) {
        val writer: BuilderWriter = BuilderWriter()
        val binding: Binding = Binding().apply {
            setVariable("author", instance.state.author)
            setVariable("builderWriter", writer)
            setVariable("tableInfo", tableInfo)
        }
        GroovyUtil::class.java.classLoader?.let {
            GroovyClassLoader(it)
        }?.let { GroovyShell(it, binding) }?.apply {
            try {
                this.evaluate(template)
                val p: String = writer.packageName.replace("[.]".toRegex(), "/")
                val f: VirtualFile = createChildDirectory(project, file, p)
                createChildFile(project, f, writer.fileName)?.let {
                    writeFileContent(project, it, writer.buffer)
                    reformatFile(project, it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}