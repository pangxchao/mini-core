package com.mini.plugin.config;

import java.io.Serializable;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;

/**
 *
 * @author xchao
 */
public final class Template implements Serializable {
	private String name, code;
	
	public synchronized final String getName() {
		return defaultIfEmpty(name, "");
	}
	
	public synchronized final String getCode() {
		return defaultIfEmpty(code, "");
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public static Builder builder() {
		return new Builder(new Template());
	}
	
	public static Builder builder(Template copy) {
		return new Builder(new Template())
			.name(copy.getName())
			.code(copy.getCode());
	}
	
	public static class Builder implements Serializable {
		private final Template template;
		
		protected Builder(Template t) {
			this.template = t;
		}
		
		public Builder name(String name) {
			template.setName(name);
			return this;
		}
		
		public Builder code(String code) {
			template.setCode(code);
			return this;
		}
		
		public Template build() {
			return template;
		}
	}
	
	
}
