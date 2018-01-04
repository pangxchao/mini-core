/**
 * Created the sn.mini.dao.mapper.row.BeanRow.java
 * @created 2016年10月10日 下午6:17:37
 * @version 1.0.0
 */
package sn.mini.dao.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import sn.mini.dao.mapper.MapperMapping;
import sn.mini.dao.model.ModelMapping;
import sn.mini.util.logger.Log;

/**
 * sn.mini.dao.mapper.row.BeanRow.java
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
			T instence = clazz.newInstance();
			for (String columnLable : this.cells.keySet()) {
				try {
					ModelMapping.getDaoTable(clazz).setValue(columnLable, instence,
						this.cells.get(columnLable).getCell(resultSet, columnLable));
				} catch (Exception e) {
					try {
						ModelMapping.getDaoTable(clazz).setValue(columnLable, instence, MapperMapping.getCell(//
							ModelMapping.getDaoTable(clazz).getType(columnLable).getName()) //
							.getCell(resultSet, columnLable));
					} catch (Exception e1) {
						Log.warn("DBException: " + e.getMessage());
					}
				}
			}
			return instence;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
