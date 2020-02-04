package com.mini.plugin.util;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EventListener;
import java.util.List;
import java.util.Optional;

import static com.intellij.openapi.roots.ModuleRootManager.getInstance;
import static java.util.Arrays.stream;
import static org.jetbrains.jps.model.java.JavaSourceRootType.SOURCE;

public final class ModuleUtil implements EventListener {
	private ModuleUtil() {}
	
	/**
	 * 获取当前项目下所有有 Module 对象
	 * @param project 项目对象
	 * @return 项目下所有的 Module
	 */
	@NotNull
	public static Module[] getModules(Project project) {
		return ModuleManager.getInstance(project).getModules();
	}
	
	@NotNull
	public static String[] getModuleNames(Project project) {
		return stream(getModules(project)).sorted((m, v) -> {
			if (existsSourcePath(v)) {
				return 1;
			}
			if (existsSourcePath(m)) {
				return -1;
			}
			return 0;
		}).map(Module::getName).toArray(String[]::new);
	}
	
	@Nullable
	public static Module getModuleByName(Project project, String name) {
		return ModuleManager.getInstance(project).findModuleByName(name);
	}
	
	/**
	 * 获取模块的源代码文件夹，不存在
	 * @param module 模块对象
	 * @return 文件夹路径
	 */
	public static List<VirtualFile> getSourcePath(@NotNull Module module) {
		return Optional.ofNullable(getInstance(module))
			.map(m -> m.getSourceRoots(SOURCE))
			.filter(list -> !list.isEmpty())
			.orElseThrow(NullPointerException::new);
	}
	
	/**
	 * 判断模块是否存在源代码文件夹
	 * @param module 模块对象
	 * @return 是否存在
	 */
	public static Boolean existsSourcePath(Module module) {
		return Optional.ofNullable(getInstance(module))
			.map(m -> m.getSourceRoots(SOURCE))
			.map(list -> !list.isEmpty())
			.orElse(false);
	}
}
