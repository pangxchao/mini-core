package com.mini.plugin.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.annotations.Transient;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.components.ServiceManager.getService;

@State(name = "MiniCode", storages = @Storage("Mini-Code.xml"))
public final class Settings implements PersistentStateComponent<Settings> {
	// 默认名称
	@Transient
	public static final String DEFAULT_NAME = "Default";

	// 默认编码
	private String encoding = "UTF-8";

	/**
	 * 获取单例实例对象
	 * @return 实例对象
	 */
	public synchronized static Settings getInstance() {
		return getService(Settings.class);
	}

	@Override
	public void loadState(@NotNull Settings settings) {
		this.encoding = settings.encoding;
	}

	@Override
	public final Settings getState() {
		return this;
	}


}
