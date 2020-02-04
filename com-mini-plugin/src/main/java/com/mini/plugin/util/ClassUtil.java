package com.mini.plugin.util;

import org.jetbrains.annotations.Nullable;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public final class ClassUtil implements EventListener {
	private static final Map<String, Class<?>> MAP = //
		new HashMap<String, Class<?>>() {{
			put("long", long.class);
			put("int", int.class);
			put("short", short.class);
			put("byte", byte.class);
			put("double", double.class);
			put("float", float.class);
			put("boolean", boolean.class);
			put("char", char.class);
		}};
	
	@Nullable
	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (Exception | Error e) {
			return MAP.get(className);
		}
	}
	
}
