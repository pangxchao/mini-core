/**
 * Created the com.cfinal.web.central.CFParameter.java
 * @created 2017年2月24日 下午1:06:51
 * @version 1.0.0
 */
package com.cfinal.web.central;

import com.cfinal.web.editor.CFEditor;

/**
 * com.cfinal.web.central.CFParameter.java
 * @author XChao
 */
public interface CFParameter extends CFBasics {
	public static final String PARAMETER_KEY = "REQUEST_PARAMETER_KEY_";

	public int length();

	public CFParameter addParameter(String name, Class<?> type, CFEditor editor, Object value);

	public void setName(int index, String name);

	public void setType(int index, Class<?> type);

	public void setEditor(int index, CFEditor editor);

	public void setValue(int index, Object value);

	public String getName(int index);

	public Class<?> getType(int index);

	public CFEditor getEditor(int index);

	public Object getValue(int index);

	public Object getValue(String paramName);

	public Object[] getValues();
}
