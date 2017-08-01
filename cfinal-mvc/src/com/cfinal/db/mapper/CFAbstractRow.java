/**
 * Created the com.cfinal.db.mapper.CFAbstractRow.java
 * @created 2016年9月24日 上午11:58:29
 * @version 1.0.0
 */
package com.cfinal.db.mapper;

import java.util.HashMap;
import java.util.Map;

import com.cfinal.db.mapper.cell.CFCell;
import com.cfinal.db.mapper.cell.CFCellMapping;

/**
 * com.cfinal.db.mapper.CFAbstractRow.java
 * @author XChao
 */
public abstract class CFAbstractRow<T> implements CFRow<T> {
	protected final Map<String, CFCell<?>> cells = new HashMap<>();

	public final void putCell(String columnLabel, CFCell<?> xCellMapper) {
		this.cells.put(columnLabel, xCellMapper);
	}

	public final void putCell(String columnLabel, String columnClassName) {
		this.cells.put(columnLabel, CFCellMapping.getCellMapper(columnClassName));
	}
}
