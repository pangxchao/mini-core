@file:Suppress("MemberVisibilityCanBePrivate")

package com.mini.plugin.util

import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.containers.stream
import org.jetbrains.jps.model.java.JavaSourceRootType.SOURCE
import java.util.*

object ModuleUtil : EventListener {

    /**
     * 获取模块的源代码文件夹，不存在
     * @param module 模块对象
     * @return 文件夹路径
     */
    @JvmStatic
    fun getSourcePath(module: Module): List<VirtualFile> {
        return ModuleRootManager.getInstance(module)?.getSourceRoots(SOURCE) ?: listOf()
    }

    /**
     * 判断模块是否存在源代码文件夹
     * @param module 模块对象
     * @return 是否存在
     */
    @JvmStatic
    fun existsSourcePath(module: Module): Boolean {
        return getSourcePath(module).isNotEmpty()
    }

    /**
     * 获取当前项目下所有有 Module 对象
     * @param project 项目对象
     * @return 项目下所有的 Module
     */
    @JvmStatic
    fun getModules(project: Project): Array<Module> {
        return ModuleManager.getInstance(project).modules
    }

    @JvmStatic
    fun getModuleNames(project: Project): Array<String> {
        return getModules(project).stream().sorted { m, v ->
            if (existsSourcePath(v)) {
                return@sorted 1
            }
            if (existsSourcePath(m)) {
                return@sorted -1
            }
            return@sorted 0
        }.map { it.name }.toArray { arrayOfNulls(it) }
    }

    @JvmStatic
    fun getModuleByName(project: Project, name: String): Module? {
        return ModuleManager.getInstance(project).findModuleByName(name)
    }

}