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
		editors.put((new Part[0]).getClass(), new PartArrayEditor());

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

		editors.put((new String[0]).getClass(), new ArrayEidtor<String>(String.class, get(String.class)));
		editors.put((new Long[0]).getClass(), new ArrayEidtor<Long>(Long.class, get(Long.class)));
		editors.put((new Integer[0]).getClass(), new ArrayEidtor<Integer>(Integer.class, get(Integer.class)));
		editors.put((new Short[0]).getClass(), new ArrayEidtor<Short>(Short.class, get(Short.class)));
		editors.put((new Byte[0]).getClass(), new ArrayEidtor<Byte>(Byte.class, get(Byte.class)));
		editors.put((new Double[0]).getClass(), new ArrayEidtor<Double>(Double.class, get(Double.class)));
		editors.put((new Float[0]).getClass(), new ArrayEidtor<Float>(Float.class, get(Float.class)));
		editors.put((new Boolean[0]).getClass(), new ArrayEidtor<Boolean>(Boolean.class, get(Boolean.class)));

		editors.put((new long[0]).getClass(), new ArrayEidtor<Long>(long.class, get(long.class)));
		editors.put((new int[0]).getClass(), new ArrayEidtor<Integer>(int.class, get(int.class)));
		editors.put((new short[0]).getClass(), new ArrayEidtor<Short>(short.class, get(short.class)));
		editors.put((new byte[0]).getClass(), new ArrayEidtor<Byte>(byte.class, get(byte.class)));
		editors.put((new double[0]).getClass(), new ArrayEidtor<Double>(double.class, get(double.class)));
		editors.put((new float[0]).getClass(), new ArrayEidtor<Float>(float.class, get(float.class)));
		editors.put((new boolean[0]).getClass(), new ArrayEidtor<Boolean>(boolean.class, get(boolean.class)));

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
