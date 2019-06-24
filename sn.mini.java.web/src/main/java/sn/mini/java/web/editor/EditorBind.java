/**
 * Created the sn.mini.java.web.editor.EditorBind.java
 * @created 2017年10月30日 下午12:38:38
 * @version 1.0.0
 */
package sn.mini.java.web.editor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import sn.mini.java.jdbc.IDao;
import sn.mini.java.web.editor.basics.StringEditor;
import sn.mini.java.web.editor.date.DateEditor;
import sn.mini.java.web.editor.http.PartArrayEditor;
import sn.mini.java.web.editor.http.PartEditor;
import sn.mini.java.web.editor.http.RequestEditor;
import sn.mini.java.web.editor.http.ResponseEditor;
import sn.mini.java.web.editor.http.SessionEditor;
import sn.mini.java.web.http.SNHttpServletRequest;
import sn.mini.java.web.model.IModel;
import sn.mini.java.web.util.IUser;

/**
 * sn.mini.java.web.editor.EditorBind.java
 * @author XChao
 */
public class EditorBind {

	public static final EditorBind instence = new EditorBind();

	private final IEditor bean_editor = new BeanEditor();
	private final Map<Class<?>, IEditor> editors = new ConcurrentHashMap<Class<?>, IEditor>();

	private EditorBind() {
		editors.put(String.class, new StringEditor());
		editors.put(IUser.class, new IUserEditor());
		editors.put(java.util.Date.class, new DateEditor());
		editors.put(java.sql.Time.class, new DateEditor());
		editors.put(java.sql.Date.class, new DateEditor());
		editors.put(java.sql.Timestamp.class, new DateEditor());

		editors.put(HttpSession.class, new SessionEditor());
		editors.put(SNHttpServletRequest.class, new RequestEditor());
		editors.put(HttpServletRequest.class, new RequestEditor());
		editors.put(HttpServletResponse.class, new ResponseEditor());

		editors.put(Part.class, new PartEditor());
		editors.put(Part[].class, new PartArrayEditor());

		editors.put(byte.class, new sn.mini.java.web.editor.atomic.ByteEditor());
		editors.put(long.class, new sn.mini.java.web.editor.atomic.LongEditor());
		editors.put(double.class, new sn.mini.java.web.editor.atomic.DoubleEditor());
		editors.put(float.class, new sn.mini.java.web.editor.atomic.FloatEditor());
		editors.put(short.class, new sn.mini.java.web.editor.atomic.ShortEditor());
		editors.put(int.class, new sn.mini.java.web.editor.atomic.IntEditor());
		editors.put(boolean.class, new sn.mini.java.web.editor.atomic.BooleanEditor());

		editors.put(Byte.class, new sn.mini.java.web.editor.basics.ByteEditor());
		editors.put(Long.class, new sn.mini.java.web.editor.basics.LongEditor());
		editors.put(Double.class, new sn.mini.java.web.editor.basics.DoubleEditor());
		editors.put(Float.class, new sn.mini.java.web.editor.basics.FloatEditor());
		editors.put(Short.class, new sn.mini.java.web.editor.basics.ShortEditor());
		editors.put(Integer.class, new sn.mini.java.web.editor.basics.IntegerEditor());
		editors.put(Boolean.class, new sn.mini.java.web.editor.basics.BooleanEditor());

		editors.put(String[].class, new ArrayEidtor<>(String.class, get(String.class)));
		editors.put(Long[].class, new ArrayEidtor<>(Long.class, get(Long.class)));
		editors.put(Integer[].class, new ArrayEidtor<>(Integer.class, get(Integer.class)));
		editors.put(Short[].class, new ArrayEidtor<>(Short.class, get(Short.class)));
		editors.put(Byte[].class, new ArrayEidtor<>(Byte.class, get(Byte.class)));
		editors.put(Double[].class, new ArrayEidtor<>(Double.class, get(Double.class)));
		editors.put(Float[].class, new ArrayEidtor<>(Float.class, get(Float.class)));
		editors.put(Boolean[].class, new ArrayEidtor<>(Boolean.class, get(Boolean.class)));

		editors.put(long[].class, new ArrayEidtor<>(long.class, get(long.class)));
		editors.put(int[].class, new ArrayEidtor<>(int.class, get(int.class)));
		editors.put(short[].class, new ArrayEidtor<>(short.class, get(short.class)));
		editors.put(byte[].class, new ArrayEidtor<>(byte.class, get(byte.class)));
		editors.put(double[].class, new ArrayEidtor<>(double.class, get(double.class)));
		editors.put(float[].class, new ArrayEidtor<>(float.class, get(float.class)));
		editors.put(boolean[].class, new ArrayEidtor<>(boolean.class, get(boolean.class)));

		editors.put(IDao.class, new IDaoEditor());
		editors.put(IModel.class, new IModelEditor());
	}

	private IEditor get(Class<?> clazz) {
		return Optional.ofNullable(editors.get(clazz)).orElse(bean_editor);
	}

	private void _bind(Class<?> clazz, IEditor editor) {
		editors.put(clazz, editor);
	}

	public static IEditor getEditor(Class<?> clazz) {
		return instence.get(clazz);
	}

	public static void bind(Class<?> clazz, IEditor editor) {
		instence._bind(clazz, editor);
	}

}
