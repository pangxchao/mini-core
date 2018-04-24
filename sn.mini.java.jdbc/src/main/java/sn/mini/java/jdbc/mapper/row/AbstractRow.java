/**
 * Created the sn.mini.dao.mapper.row.AbstractRow.java
 * @created 2016年9月24日 上午11:58:29
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.row;

import java.util.HashMap;
import java.util.Map;

import sn.mini.java.jdbc.mapper.MapperMapping;
import sn.mini.java.jdbc.mapper.cell.ICell;

/**
 * sn.mini.dao.mapper.row.AbstractRow.java
 * @author XChao
 */
public abstract class AbstractRow<T> implements IRow<T> {
	protected final Map<String, ICell<?>> cells = new HashMap<>();

	public final void putCell(String columnLabel, ICell<?> xCellMapper) {
		this.cells.put(columnLabel, xCellMapper);
	}

	public final void putCell(String columnLabel, String columnClassName) {
		this.cells.put(columnLabel, MapperMapping.getCell(columnClassName));
	}
}
