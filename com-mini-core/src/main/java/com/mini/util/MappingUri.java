package com.mini.util;

import static com.mini.util.MappingUri.PartType.P;
import static com.mini.util.MappingUri.PartType.S;
import static com.mini.util.StringUtil.*;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

public final class MappingUri<V> implements Serializable, EventListener {
	private static final long serialVersionUID = 1875965841576567853L;
	private final Map<String, V> data = new ConcurrentHashMap<>();

	public static String slash_remove(String k) {
		if (!StringUtil.endsWith(k, "/")) return k;
		return k.substring(0, k.length() - 1);
	}

	@SuppressWarnings("unused")
	public Set<String> keySet() {
		return data.keySet();
	}

	@SuppressWarnings("unused")
	public Collection<V> values() {
		return data.values();
	}

	@SuppressWarnings("unused")
	public Set<Entry<String, V>> entrySet() {
		return data.entrySet();
	}

	public V put(@Nonnull String key, @Nonnull V value) {
		return data.put(key, value);
	}

	public V putIfAbsent(@Nonnull String key, @Nonnull V value) {
		return data.putIfAbsent(key, value);
	}

	/**
	 * 获取路径匹配值
	 * @param key    路径
	 * @param suffix 是否开启后缀匹配模式
	 * @param slash  是否自动后缀路径模式匹配
	 * @param func   回调
	 * @return 匹配值
	 */
	public V get(String key, boolean suffix, boolean slash, BiConsumer<String, String> func) {
		return ObjectUtil.defIfNull(this.data.get(key), () -> {
			final var p = new MappingUri.PathInfo(key, false);
			for (Entry<String, V> entry : this.data.entrySet()) {
				var path = new PathInfo(entry.getKey(), true);
				if (equals(p, path, suffix, slash, func)) {
					return entry.getValue();
				}
			}
			return null;
		});
	}

	// 比较两个字符串是否相等或者相似，或者以规定规则可以匹配
	private static boolean equals(PathInfo aPath, PathInfo nPath, boolean suffix, boolean slash, BiConsumer<String, String> func) {
		// 未开启自动后缀路径模式匹配，最后的“/”不相同返回False
		if (!slash && aPath.isSlash() != nPath.isSlash()) {
			return false;
		}
		// 未开启后缀匹配模式并且两路径后缀不相等时，返回False;
		if (!suffix && !eq(aPath.getSuffix(), nPath.getSuffix())) {
			return false;
		}
		// 如果有一个路径的长度为0， 返回False
		PartInfo[] aParts = aPath.toArray(), nParts = nPath.toArray();
		if (aParts.length == 0 && nParts.length == 0) {
			return false;
		}
		// 比较两路径是否相等
		return Arrays.equals(aParts, nParts, (aPart, nPart) -> {
			// 两路径相等时，返回相等
			if (StringUtil.eq(aPart.getText(), nPart.getText())) {
				return 0;
			}
			// 基础路径为参数对象时，返回相等
			if (nPart.getType() == PartType.P) {
				String name = nPart.text;
				String t = aPart.text;
				func.accept(name, t);
				return 0;
			}
			return -1;
		});
	}

	// 创建 PathInfo 对象
	private static void createPathInfo(PathInfo info, String path, boolean b) {
		ObjectUtil.require(!isBlank(path), "Path can not be blank.");
		int lastSlashIndex = StringUtil.lastIndexOf(path, '/');
		int lastDotIndex = StringUtil.lastIndexOf(path, '.');
		// 后缀处理
		if (lastDotIndex > 0 && lastDotIndex > lastSlashIndex) {
			info.setSuffix(substring(path, lastDotIndex + 1));
			path = substring(path, 0, lastDotIndex);
		}
		// “/”结尾
		else if (lastSlashIndex == length(path) - 1) {
			path = substring(path, 0, lastSlashIndex);
			info.setSlash(true);
		}
		// 循环处理每一级路径
		var tokenizer = new StringTokenizer(path, "/");
		for (int count = 0; tokenizer.hasMoreElements();) {
			final var builder = new StringBuilder();
			final String t = tokenizer.nextToken();
			for (final char chars : t.toCharArray()) {
				if (b && chars == '{' && count == 0) {
					if (builder.length() > 0) {
						var p = new PartInfo(S);
						p.setBuilder(builder);
						info.partList.add(p);
					}
					count++;
					continue;
				}
				if (b && chars == '}' && count > 0) {
					if (builder.length() > 0) {
						var p = new PartInfo(P);
						p.setBuilder(builder);
						info.partList.add(p);
					}
					count--;
					continue;
				}
				builder.append(chars);
			}
			if (builder.length() > 0) {
				var p = new PartInfo(S);
				p.setBuilder(builder);
				info.partList.add(p);
			}
		}
	}

	// 整个路径信息
	private final static class PathInfo {
		private final List<PartInfo> partList = new ArrayList<>();
		private String suffix;
		private boolean slash;

		private PathInfo(String path, boolean b) {
			createPathInfo(this, path, b);
		}

		@Nonnull
		public final String getSuffix() {
			return def(suffix, "");
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		private boolean isSlash() {
			return slash;
		}

		@SuppressWarnings("SameParameterValue")
		private void setSlash(boolean slash) {
			this.slash = slash;
		}

		public final PartInfo[] toArray() {
			PartInfo[] ps = new PartInfo[0];
			return partList.toArray(ps);
		}
	}

	private final static class PartInfo {
		private PartType type;
		private String text;

		private PartInfo(PartType type) {
			this.type = type;
		}

		public void setType(PartType type) {
			this.type = type;
		}

		public void setText(String text) {
			this.text = text;
		}

		public void setBuilder(StringBuilder t) {
			this.text = t.toString();
			t.delete(0, t.length());
		}

		public PartType getType() {
			return type;
		}

		public String getText() {
			return text;
		}
	}

	protected enum PartType {
		S, P
	}
}
