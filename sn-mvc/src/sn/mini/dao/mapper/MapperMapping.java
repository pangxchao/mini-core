/**
 * Created the sn.mini.dao.mapper.MapperMapping.java
 * @created 2017年11月2日 下午5:55:38
 * @version 1.0.0
 */
package sn.mini.dao.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sn.mini.dao.mapper.cell.BlobCell;
import sn.mini.dao.mapper.cell.BooleanCell;
import sn.mini.dao.mapper.cell.ByteCell;
import sn.mini.dao.mapper.cell.DateCell;
import sn.mini.dao.mapper.cell.DoubleCell;
import sn.mini.dao.mapper.cell.FloatCell;
import sn.mini.dao.mapper.cell.ICell;
import sn.mini.dao.mapper.cell.IntegerCell;
import sn.mini.dao.mapper.cell.LongCell;
import sn.mini.dao.mapper.cell.ObjectCell;
import sn.mini.dao.mapper.cell.ShortCell;
import sn.mini.dao.mapper.cell.StringCell;

/**
 * sn.mini.dao.mapper.MapperMapping.java
 * @author XChao
 */
public final class MapperMapping {
	private static final Map<String, ICell<?>> cells = new ConcurrentHashMap<String, ICell<?>>() {
		private static final long serialVersionUID = 1L;
		{
			put(Object.class.getName(), new ObjectCell());
			put(String.class.getName(), new StringCell());
			put(Long.class.getName(), new LongCell());
			put(Integer.class.getName(), new IntegerCell());
			put(Short.class.getName(), new ShortCell());
			put(Byte.class.getName(), new ObjectCell());
			put(Double.class.getName(), new DoubleCell());
			put(Float.class.getName(), new FloatCell());
			put(Boolean.class.getName(), new BooleanCell());
			put(BigDecimal.class.getName(), new DoubleCell());
			put(BigInteger.class.getName(), new LongCell());
			put(Date.class.getName(), new DateCell());
			put(java.sql.Date.class.getName(), new DateCell());
			put(java.sql.Time.class.getName(), new DateCell());
			put(java.sql.Timestamp.class.getName(), new DateCell());
			put(Blob.class.getName(), new BlobCell());
			put(long.class.getName(), new LongCell());
			put(int.class.getName(), new IntegerCell());
			put(short.class.getName(), new ShortCell());
			put(byte.class.getName(), new ByteCell());
			put(double.class.getName(), new DoubleCell());
			put(float.class.getName(), new FloatCell());
			put(boolean.class.getName(), new BooleanCell());
		}
	};

	/**
	 * 根据指定Class 获取cell 实现
	 * @param clazz
	 * @return
	 */
	public static ICell<?> getCell(String clazz) {
		return cells.get(clazz) == null ? cells.get(Object.class.getName()) : cells.get(clazz);
	}
}
