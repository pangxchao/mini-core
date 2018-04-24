/**
 * Created the sn.mini.dao.model.DaoTable.java
 * @created 2017年11月2日 下午4:23:41
 * @version 1.0.0
 */
package sn.mini.java.jdbc.model;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Binding;
import sn.mini.java.jdbc.util.DaoUtil;
import sn.mini.java.util.json.JSONArray;
import sn.mini.java.util.lang.StringUtil;

/**
 * sn.mini.dao.model.DaoTable.java
 * @author XChao
 */
public final class DaoTable {
	private final Optional<Binding> binding;
	private final Class<?> clazz;
	private final Map<String, DaoField> fields = new HashMap<>();
	private final Map<String, String> current = new HashMap<>();
	private final Map<String, String> primary = new HashMap<>();
	private final Map<String, String> others = new HashMap<>();

	public DaoTable(Class<?> clazz) {
		try {
			this.binding = Optional.ofNullable(clazz.getAnnotation(Binding.class));
			BeanInfo beanInfo = Introspector.getBeanInfo((this.clazz = clazz));
			for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
				DaoField field = new DaoField(descriptor, Optional.ofNullable(descriptor.getWriteMethod())//
					.map(v -> v.getAnnotation(Binding.class)).orElse(null));
				if(this.fields.get(field.getName()) != null || this.fields.get(field.getDbName()) != null) {
					throw new RuntimeException("The name and the dbName cannot be repeated with other fields.");
				}
				this.fields.put(field.getName(), field);
				this.fields.put(field.getDbName(), field);
				if(field.isBinding()) {
					current.put(field.getName(), field.getDbName());
				}
				if(field.isPrimary()) {
					primary.put(field.getName(), field.getDbName());
				} else {
					others.put(field.getName(), field.getDbName());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public Class<?> getClazz() {
		return this.clazz;
	}

	public String getDbName() {
		return binding.map(v -> v.value()).orElse(StringUtil.toDBName(clazz.getSimpleName()));
	}

	public Set<Entry<String, DaoField>> fieldEntrySet() {
		return this.fields.entrySet();
	}

	public Set<Entry<String, String>> currentEntrySet() {
		return this.current.entrySet();
	}

	public Set<Entry<String, String>> primaryEntrySet() {
		return this.primary.entrySet();
	}

	public Set<Entry<String, String>> othersEntrySet() {
		return this.others.entrySet();
	}

	public String getName(String name) {
		return this.fields.get(name).getName();
	}

	public boolean isBinding(String name) {
		return this.fields.get(name).isBinding();
	}

	public boolean isPrimary(String name) {
		return this.fields.get(name).isPrimary();
	}

	public boolean isForeign(String name) {
		return this.fields.get(name).isForeign();
	}

	public String getDbName(String name) {
		return this.fields.get(name).getDbName();
	}

	public Method getReadMethod(String name) {
		return this.fields.get(name).getReadMethod();
	}

	public Method getWriteMethod(String name) {
		return this.fields.get(name).getWriteMethod();
	}

	public Class<?> getType(String name) {
		return this.fields.get(name).getType();
	}

	public Object getValue(String name, Object instance) throws Exception {
		return this.fields.get(name).getValue(instance);
	}

	public void setValue(String name, Object instance, Object value) throws Exception {
		this.fields.get(name).setValue(instance, value);
	}

	/**
	 * 获取指定表的创建表的SQL语句
	 * @param db
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static String getCreateTable(IDao dao, String tableName) throws SQLException {
		return dao.executeQuery(rs -> {
			return rs.next() ? rs.getString(2) : "";
		}, "show create table " + tableName);
	}

	/**
	 * 获取指定表所有的字段
	 * @param db
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static JSONArray getColumns(IDao dao, String tableName) throws SQLException {
		try (ResultSet resultSet = dao.getMetaData().getColumns(null, null, tableName, null)) {
			return DaoUtil.analysis(resultSet);
		}
	}

	/**
	 * 获取指定表所有的主键字段
	 * @param db
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static JSONArray getPrimaryKey(IDao dao, String tableName) throws SQLException {
		try (ResultSet resultSet = dao.getMetaData().getPrimaryKeys(null, null, tableName)) {
			return DaoUtil.analysis(resultSet);
		}
	}

	/**
	 * 获取指定表所有的外键， 其它表指向该表的所有外键
	 * @param db
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static JSONArray getExportedKeys(IDao dao, String tableName) throws SQLException {
		try (ResultSet resultSet = dao.getMetaData().getExportedKeys(null, null, tableName)) {
			return DaoUtil.analysis(resultSet);
		}
	}

	/**
	 * 获取所有表指定的外键，该表指向其它表的所有外键
	 * @param db
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static JSONArray getImportedKeys(IDao dao, String tableName) throws SQLException {
		try (ResultSet resultSet = dao.getMetaData().getImportedKeys(null, null, tableName)) {
			return DaoUtil.analysis(resultSet);
		}
	}

	/**
	 * 获取指定表所有的索引
	 * @param db
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static JSONArray getIndexInfo(IDao dao, String tableName) throws SQLException {
		try (ResultSet resultSet = dao.getMetaData().getIndexInfo(null, null, tableName, false, false)) {
			return DaoUtil.analysis(resultSet);
		}
	}
}
