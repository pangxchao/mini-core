/**
 * Created the sn.mini.dao.util.DaoUtil.java
 * @created 2017年11月1日 下午6:40:04
 * @version 1.0.0
 */
package sn.mini.java.jdbc.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sn.mini.java.jdbc.mapper.cell.ICell;
import sn.mini.java.jdbc.mapper.row.AbstractRow;
import sn.mini.java.jdbc.mapper.row.BeanRow;
import sn.mini.java.jdbc.mapper.row.IRow;
import sn.mini.java.jdbc.mapper.row.MapRow;
import sn.mini.java.util.json.JSONArray;
import sn.mini.java.util.json.JSONObject;

/**
 * sn.mini.dao.util.DaoUtil.java
 * @author XChao
 */
public class DaoUtil {

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @return
	 */
	public static JSONArray analysis(ResultSet resultSet) throws SQLException {
		JSONArray result = new JSONArray();
		AbstractRow<JSONObject> mapper = new MapRow();
		getMapperByResultSet(resultSet, mapper);
		while (resultSet.next()) {
			result.add(mapper.getRow(resultSet));
		}
		return result;
	}

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @param cells
	 * @return
	 * @throws SQLException
	 */
	public static JSONArray analysis(ResultSet resultSet, Map<String, ICell<?>> cells) throws SQLException {
		JSONArray result = new JSONArray();
		AbstractRow<JSONObject> mapper = new MapRow();
		getMapperByResultSet(resultSet, mapper, cells);
		while (resultSet.next()) {
			result.add(mapper.getRow(resultSet));
		}
		return result;
	}

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public static <T> List<T> analysis(ResultSet resultSet, Class<T> clazz) throws SQLException {
		List<T> result = new ArrayList<T>();
		AbstractRow<T> mapper = new BeanRow<T>(clazz);
		getMapperByResultSet(resultSet, mapper);
		while (resultSet.next()) {
			result.add(mapper.getRow(resultSet));
		}
		return result;
	}

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @param clazz
	 * @param cells
	 * @return
	 * @throws SQLException
	 */
	public static <T> List<T> analysis(ResultSet resultSet, Class<T> clazz, Map<String, ICell<?>> cells)
		throws SQLException {
		List<T> result = new ArrayList<T>();
		AbstractRow<T> mapper = new BeanRow<T>(clazz);
		getMapperByResultSet(resultSet, mapper, cells);
		while (resultSet.next()) {
			result.add(mapper.getRow(resultSet));
		}
		return result;
	}

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @param mapper
	 * @return
	 * @throws SQLException
	 */
	public static <T> List<T> analysis(ResultSet resultSet, IRow<T> mapper) throws SQLException {
		List<T> result = new ArrayList<T>();
		while (resultSet.next()) {
			result.add(mapper.getRow(resultSet));
		}
		return result;
	}

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static JSONObject analysisOne(ResultSet resultSet) throws SQLException {
		AbstractRow<JSONObject> mapper = new MapRow();
		getMapperByResultSet(resultSet, mapper);
		return resultSet.next() ? mapper.getRow(resultSet) : null;
	}

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @param cells
	 * @return
	 * @throws SQLException
	 */
	public static JSONObject analysisOne(ResultSet resultSet, Map<String, ICell<?>> cells) throws SQLException {
		AbstractRow<JSONObject> mapper = new MapRow();
		getMapperByResultSet(resultSet, mapper, cells);
		return resultSet.next() ? mapper.getRow(resultSet) : null;
	}

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public static <T> T analysisOne(ResultSet resultSet, Class<T> clazz) throws SQLException {
		AbstractRow<T> mapper = new BeanRow<T>(clazz);
		getMapperByResultSet(resultSet, mapper);
		return resultSet.next() ? mapper.getRow(resultSet) : null;
	}

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @param clazz
	 * @param cells
	 * @return
	 * @throws SQLException
	 */
	public static <T> T analysisOne(ResultSet resultSet, Class<T> clazz, Map<String, ICell<?>> cells)
		throws SQLException {
		AbstractRow<T> mapper = new BeanRow<T>(clazz);
		getMapperByResultSet(resultSet, mapper, cells);
		return resultSet.next() ? mapper.getRow(resultSet) : null;
	}

	/**
	 * 解析结果集,该方法不会主动关闭结果集，需要手动关闭
	 * @param resultSet
	 * @param mapper
	 * @return
	 * @throws SQLException
	 */
	public static <T> T analysisOne(ResultSet resultSet, IRow<T> mapper) throws SQLException {
		return resultSet.next() ? mapper.getRow(resultSet) : null;
	}

	/**
	 * 根据 ResultSet, Sql 初始化RowMapper数据
	 * @param resultSet
	 * @param mapper
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static <T> AbstractRow<T> getMapperByResultSet(ResultSet resultSet, AbstractRow<T> mapper)
		throws SQLException {
		ResultSetMetaData rsmd = resultSet.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			mapper.putCell(rsmd.getColumnLabel(i), rsmd.getColumnClassName(i));
		}
		return mapper;
	}

	/**
	 * 根据 ResultSet, Sql 初始化RowMapper数据
	 * @param resultSet
	 * @param mapper
	 * @param cells
	 * @return
	 * @throws SQLException
	 */
	public static <T> AbstractRow<T> getMapperByResultSet(ResultSet resultSet, AbstractRow<T> mapper,
		Map<String, ICell<?>> cells) throws SQLException {
		ResultSetMetaData rsmd = resultSet.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			mapper.putCell(rsmd.getColumnLabel(i), rsmd.getColumnClassName(i));
		}
		for (String hkey : cells.keySet()) {
			mapper.putCell(hkey, cells.get(hkey));
		}
		return mapper;
	}
}
