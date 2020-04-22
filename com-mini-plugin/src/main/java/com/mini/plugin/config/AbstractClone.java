package com.mini.plugin.config;

import org.jetbrains.annotations.NotNull;

/**
 * 数据克隆接口
 * @param <T>
 * @author xchao
 */
public abstract class AbstractClone<T extends AbstractClone<T>> implements Cloneable {
	
	@NotNull
	public abstract T clone();
}
