/**
 * Created the sn.mini.jdbc.mapper.row.BeanRow.java
 *
 * @created 2016年10月10日 下午6:17:37
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import sn.mini.java.jdbc.mapper.MapperMapping;
import sn.mini.java.jdbc.model.ModelMapping;
import sn.mini.java.util.lang.ClassUtil;

/**
 * sn.mini.jdbc.mapper.row.BeanRow.java
 *
 * @author XChao
 */
public class BeanRow<T> extends AbstractRow<T> {
	private final Class<T> clazz;

	public BeanRow(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T getRow(ResultSet resultSet) throws SQLException {
		try {
			T instance = ClassUtil.newInstance(clazz);
			for (String columnLabel : this.cells.keySet()) {
				try {
					ModelMapping.getDaoTable(clazz).setValue(columnLabel, instance, this.cells.get(columnLabel).getCell(resultSet, columnLabel));
				} catch (Exception e) {
					try {
						ModelMapping.getDaoTable(clazz).setValue(columnLabel, instance,
								MapperMapping.getCell(ModelMapping.getDaoTable(clazz).getType(columnLabel).getName()).getCell(resultSet, columnLabel));
					} catch (Exception e1) {
//						Log.warn(String.format("DBException 表： %s, 字段：%s", clazz.getName(), columnLabel));
					}
				}
			}
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
