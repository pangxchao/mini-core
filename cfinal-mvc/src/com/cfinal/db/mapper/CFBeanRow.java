/**
 * Created the com.cfinal.db.mapper.CFBeanRow.java
 * @created 2016年10月10日 下午6:17:37
 * @version 1.0.0
 */
package com.cfinal.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;
import com.cfinal.db.mapper.cell.CFCell;
import com.cfinal.db.model.mapping.CFDBMapping;
import com.cfinal.db.model.mapping.CFDBTables;
import com.cfinal.db.mapper.cell.CFCellMapping;
import com.cfinal.util.logger.CFLogger;

/**
 * com.cfinal.db.mapper.CFBeanRow.java
 * @author XChao
 */
public class CFBeanRow<T> extends CFAbstractRow<T> {

	private Class<T> clazz;
	private CFDBTables dbTables;

	public CFBeanRow(CFDB db, Class<T> clazz) {
		this.clazz = clazz;
		this.dbTables = CFDBMapping.getTables(db, clazz);
	}

	@Override
	public T getRow(CFDB db, ResultSet resultSet) throws SQLException {
		try {
			T instence = clazz.newInstance();
			for (String columnName : this.dbTables.allColumnKeySet()) {
				String columnLable = this.dbTables.getLable(columnName);
				CFCell<?> cellMapper = this.cells.get(columnLable);
				if(cellMapper == null) {
					cellMapper = this.cells.get((columnLable = columnName));
				}
				if(this.dbTables.getSetter(columnName) != null && cellMapper != null) {
					try {
						this.dbTables.getSetter(columnName).invoke(instence, // 换行
							this.dbTables.getTypes(columnName).cast(cellMapper // 换行
								.getCell(db, resultSet, columnLable)));
					} catch (ClassCastException e) {
						try {
							this.dbTables.getSetter(columnName).invoke(instence, // 换行
								CFCellMapping.getCellMapper(this.dbTables.getTypes(columnName) // 换行
									.getName()).getCell(db, resultSet, columnLable));
						} catch (Exception e1) {
							CFLogger.warning(e1.getMessage(), e1);
						}
					} catch (Exception e) {
						CFLogger.warning(e.getMessage(), e);
					}
				}
			}
			return instence;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
