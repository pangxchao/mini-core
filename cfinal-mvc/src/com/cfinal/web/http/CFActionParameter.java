/**
 * Created the com.cfinal.web.control.CFActionParameter.java
 * @created 2017年2月24日 下午1:06:51
 * @version 1.0.0
 */
package com.cfinal.web.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cfinal.web.editor.CFEditor;

/**
 * com.cfinal.web.control.CFActionParameter.java
 * @author XChao
 */
public class CFActionParameter implements CFParameter {
	private final List<ParameterItem> param = new ArrayList<>();

	protected static class ParameterItem {
		private String name;
		private Class<?> type;
		private CFEditor editor;
		private Object value;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the type
		 */
		public Class<?> getType() {
			return type;
		}

		/**
		 * @param type the type to set
		 */
		public void setType(Class<?> type) {
			this.type = type;
		}

		/**
		 * @return the edtor
		 */
		public CFEditor getEdtor() {
			return editor;
		}

		/**
		 * @param edtor the edtor to set
		 */
		public void setEdtor(CFEditor edtor) {
			this.editor = edtor;
		}

		/**
		 * @return the value
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(Object value) {
			this.value = value;
		}

	}

	public int length() {
		return this.param.size();
	}

	public CFActionParameter addParameter(String name, Class<?> type, CFEditor editor, Object value) {
		ParameterItem item = new ParameterItem();
		item.setName(name);
		item.setType(type);
		item.setEdtor(editor);
		item.setValue(value);
		this.param.add(item);
		return this;
	}

	public void setName(int index, String name) {
		this.param.get(index).setName(name);
	}

	public void setType(int index, Class<?> type) {
		this.param.get(index).setType(type);
	}

	public void setEditor(int index, CFEditor editor) {
		this.param.get(index).setEdtor(editor);
	}

	public void setValue(int index, Object value) {
		this.param.get(index).setValue(value);
	}

	public String getName(int index) {
		return this.param.get(index).getName();
	}

	public Class<?> getType(int index) {
		return this.param.get(index).getType();
	}

	public CFEditor getEditor(int index) {
		return this.param.get(index).getEdtor();
	}

	public Object getValue(int index) {
		return this.param.get(index).getValue();
	}

	public Object getValue(String paramName) {
		for (ParameterItem item : this.param) {
			if(StringUtils.equals(item.getName(), paramName)) {
				return item.getValue();
			}
		}
		return null;
	}

	public Object[] getValues() {
		List<Object> values = new ArrayList<>();
		for (ParameterItem item : this.param) {
			values.add(item.getValue());
		}
		return values.toArray();
	}
}
