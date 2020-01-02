package com.mini.core.logger;

import com.mini.core.logger.implement.MiniLogger;

public abstract class LoggerFactory {

	private static LoggerFactory factory;

	/**
	 * 获取日志实现实例
	 * @param name 名称
	 * @return 日志实例
	 */
	protected abstract Logger getInstance(String name);

	/**
	 * 获取日志实现实例
	 * @param clazz 所属类
	 * @return 日志实例
	 */
	protected abstract Logger getInstance(Class<?> clazz);

	/**
	 * 设置工厂实例
	 * @param factory 工厂实例
	 */
	public static void setFactory(LoggerFactory factory) {
		LoggerFactory.factory = factory;
	}

	/**
	 * 获取工厂实例，若未设置，返回默认实例
	 * @return 工厂实例
	 */
	public static synchronized LoggerFactory getFactory() {
		if (factory == null) factory = new DefaultFactory();
		return factory;
	}

	/**
	 * 获取日志实现实例
	 * @param name 名称
	 * @return 日志实例
	 */
	public static Logger getLogger(String name) {
		return getFactory().getInstance(name);
	}

	/**
	 * 获取日志实现实例
	 * @param clazz 所属类
	 * @return 日志实例
	 */
	public static Logger getLogger(Class<?> clazz) {
		return getFactory().getInstance(clazz);
	}

	protected static class DefaultFactory extends LoggerFactory {
		private MiniLogger logger = new MiniLogger(Level.DEBUG);

		@Override
		protected Logger getInstance(String name) {
			return logger;
		}

		@Override
		protected Logger getInstance(Class<?> clazz) {
			return logger;
		}
	}
}
