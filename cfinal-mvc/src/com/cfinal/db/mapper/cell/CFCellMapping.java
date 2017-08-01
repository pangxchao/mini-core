/**
 * Created the com.cfinal.db.mapper.cell.CFCellMapping.java
 * @created 2016年9月24日 下午12:03:31
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * com.cfinal.db.mapper.cell.CFCellMapping.java
 * @author XChao
 */
public class CFCellMapping {
	private static final Map<String, CFCell<?>> cells = new HashMap<>();

	static {
		cells.put(Object.class.getName(), new CFObjectCell());
		cells.put(String.class.getName(), new CFStringCell());
		cells.put(Long.class.getName(), new CFLongCell());
		cells.put(Integer.class.getName(), new CFIntegerCell());
		cells.put(Short.class.getName(), new CFShortCell());
		cells.put(Byte.class.getName(), new CFByteCell());
		cells.put(Double.class.getName(), new CFDoubleCell());
		cells.put(Float.class.getName(), new CFFloatCell());
		cells.put(Boolean.class.getName(), new CFBooleanCell());
		cells.put(BigDecimal.class.getName(), new CFDoubleCell());
		cells.put(BigInteger.class.getName(), new CFLongCell());
		cells.put(Date.class.getName(), new CFDateCell());
		cells.put(java.sql.Date.class.getName(), new CFDateCell());
		cells.put(java.sql.Time.class.getName(), new CFDateCell());
		cells.put(java.sql.Timestamp.class.getName(), new CFDateCell());
		cells.put(Blob.class.getName(), new CFBlobCell());
		cells.put(long.class.getName(), new CFLongCell());
		cells.put(int.class.getName(), new CFIntegerCell());
		cells.put(short.class.getName(), new CFShortCell());
		cells.put(byte.class.getName(), new CFByteCell());
		cells.put(double.class.getName(), new CFDoubleCell());
		cells.put(float.class.getName(),  new CFFloatCell());
		cells.put(boolean.class.getName(),  new CFBooleanCell());
	}

	public static CFCell<?> getCellMapper(String columnClassName) {
		CFCell<?> xCellMapper = cells.get(columnClassName);
		if (xCellMapper == null) {
			xCellMapper = cells.get(Object.class.getName());
		}
		return xCellMapper;
	}
}
