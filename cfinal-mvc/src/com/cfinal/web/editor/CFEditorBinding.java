/**
 * Created the com.cfinal.web.editor.CFEditorBinding.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.cfinal.db.CFDB;
import com.cfinal.web.editor.array.CFArrayEditor;
import com.cfinal.web.editor.array.CFPartArrayEditor;
import com.cfinal.web.editor.atomic.CFAtomicBooleanEditor;
import com.cfinal.web.editor.atomic.CFAtomicByteEditor;
import com.cfinal.web.editor.atomic.CFAtomicDoubleEditor;
import com.cfinal.web.editor.atomic.CFAtomicFloatEditor;
import com.cfinal.web.editor.atomic.CFAtomicIntegerEditor;
import com.cfinal.web.editor.atomic.CFAtomicLongEditor;
import com.cfinal.web.editor.atomic.CFAtomicShortEditor;
import com.cfinal.web.editor.basics.CFBooleanEditor;
import com.cfinal.web.editor.basics.CFByteEditor;
import com.cfinal.web.editor.basics.CFCharEditor;
import com.cfinal.web.editor.basics.CFDoubleEditor;
import com.cfinal.web.editor.basics.CFFloatEditor;
import com.cfinal.web.editor.basics.CFIntegerEditor;
import com.cfinal.web.editor.basics.CFLongEditor;
import com.cfinal.web.editor.basics.CFShortEditor;
import com.cfinal.web.editor.basics.CFStringEditor;
import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;
import com.cfinal.web.http.CFPart;
import com.cfinal.web.model.CFModel;
import com.cfinal.web.model.CFUser;

/**
 * com.cfinal.web.editor.CFEditorBinding.java
 * @author XChao
 */
public class CFEditorBinding {
	private static CFEditorBinding instence;
	private Map<Class<?>, CFEditor> editors = new HashMap<Class<?>, CFEditor>();
	private CFBeanEditor beanEditor = new CFBeanEditor();

	public synchronized static CFEditorBinding getInstence() {
		if(instence == null) {
			instence = new CFEditorBinding();
		}
		return instence;
	}

	private CFEditorBinding() {
		editors.put(String.class, new CFStringEditor());
		editors.put(CFUser.class, new CFUserEditor());
		editors.put(java.util.Date.class, new CFDateEditor());

		editors.put(HttpSession.class, new CFSessionEditor());
		editors.put(CFHttpServletRequest.class, new CFRequestEditor());
		editors.put(CFHttpServletResponse.class, new CFResponseEditor());
		editors.put(HttpServletRequest.class, new CFRequestEditor());
		editors.put(HttpServletResponse.class, new CFResponseEditor());

		editors.put(Part.class, new CFPartEditor());
		editors.put(CFPart.class, new CFPartEditor());
		editors.put((new Part[0]).getClass(), new CFPartArrayEditor());
		editors.put((new CFPart[0]).getClass(), new CFPartArrayEditor());

		editors.put(byte.class, new CFAtomicByteEditor());
		editors.put(long.class, new CFAtomicLongEditor());
		editors.put(double.class, new CFAtomicDoubleEditor());
		editors.put(float.class, new CFAtomicFloatEditor());
		editors.put(short.class, new CFAtomicShortEditor());
		editors.put(int.class, new CFAtomicIntegerEditor());
		editors.put(boolean.class, new CFAtomicBooleanEditor());
		editors.put(char.class, new CFCharEditor());

		editors.put(Byte.class, new CFByteEditor());
		editors.put(Long.class, new CFLongEditor());
		editors.put(Double.class, new CFDoubleEditor());
		editors.put(Float.class, new CFFloatEditor());
		editors.put(Short.class, new CFShortEditor());
		editors.put(Integer.class, new CFIntegerEditor());
		editors.put(Boolean.class, new CFBooleanEditor());

		editors.put((new String[0]).getClass(), new CFArrayEditor<String>(String.class, getEditor(String.class)));
		editors.put((new Integer[0]).getClass(), new CFArrayEditor<Integer>(Integer.class, getEditor(Integer.class)));
		editors.put((new Long[0]).getClass(), new CFArrayEditor<Long>(Long.class, getEditor(Long.class)));
		editors.put((new Double[0]).getClass(), new CFArrayEditor<Double>(Double.class, getEditor(Double.class)));
		editors.put((new Float[0]).getClass(), new CFArrayEditor<Float>(Float.class, getEditor(Float.class)));
		editors.put((new Short[0]).getClass(), new CFArrayEditor<Short>(Short.class, getEditor(Short.class)));
		editors.put((new Byte[0]).getClass(), new CFArrayEditor<Byte>(Byte.class, getEditor(Byte.class)));
		editors.put((new Boolean[0]).getClass(), new CFArrayEditor<Boolean>(Boolean.class, getEditor(Boolean.class)));

		editors.put((new int[0]).getClass(), new CFArrayEditor<Integer>(int.class, getEditor(int.class)));
		editors.put((new long[0]).getClass(), new CFArrayEditor<Long>(long.class, getEditor(long.class)));
		editors.put((new double[0]).getClass(), new CFArrayEditor<Double>(double.class, getEditor(double.class)));
		editors.put((new float[0]).getClass(), new CFArrayEditor<Float>(float.class, getEditor(float.class)));
		editors.put((new short[0]).getClass(), new CFArrayEditor<Short>(short.class, getEditor(short.class)));
		editors.put((new byte[0]).getClass(), new CFArrayEditor<Byte>(byte.class, getEditor(byte.class)));
		editors.put((new boolean[0]).getClass(), new CFArrayEditor<Boolean>(boolean.class, getEditor(boolean.class)));
		editors.put((new char[0]).getClass(), new CFArrayEditor<Character>(char.class, getEditor(char.class)));

		editors.put(CFDB.class, new CFDBEditor());
		editors.put(CFModel.class, new CFModelEditor());
	}

	public CFEditor getEditor(Class<?> type) {
		CFEditor result = editors.get(type);
		if(result == null) {
			result = beanEditor;
		}
		return result;
	}

	public void bind(Class<?> clazz, CFEditor editor) {
		editors.put(clazz, editor);
	}

}
