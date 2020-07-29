package com.mini.plugin.config;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

import static com.intellij.openapi.util.text.StringUtil.isNotEmpty;

/**
 * 数据类型映射数据模型
 * @author xchao
 */
public final class DataTypeModel extends AbstractTableModel<DataType> {
	
	public DataTypeModel() throws Error {
		// 数据对应数据库的字段类型
		addColumn("Database Type");
		// 不能为空数据的Java类型
		addColumn("Java Type");
		// 可为空数据的Java类型
		addColumn("Nullable Type");
	}
	
	public synchronized final void addRow(DataType mapping) {
		Optional.ofNullable(mapping).ifPresent(m -> {
			Object[] array = new Object[]{"", "", ""};
			// 数据对应数据库的字段类型
			if (isNotEmpty(m.getDatabaseType())) {
				array[0] = m.getDatabaseType();
			}
			// 不能为空数据的Java类型
			if (isNotEmpty(m.getJavaType())) {
				array[1] = m.getJavaType();
			}
			// 可为空数据的Java类型
			if (isNotEmpty(m.getNullJavaType())) {
				array[2] = m.getNullJavaType();
			}
			// 添加一行数据
			DataTypeModel.this.addRow(array);
		});
	}
	
	public void setData(Map<String, DataType> data) {
		DataTypeModel.this.removeAllRow();
		data.forEach((key, value) -> {//
			this.addRow(value);
		});
	}
	
	public String getDatabaseType(int row) {
		return (String) getValueAt(row, 0);
	}
	
	public String getJavaType(int row) {
		return (String) getValueAt(row, 1);
	}
	
	public String getNullJavaType(int row) {
		return (String) getValueAt(row, 2);
	}
	
	@NotNull
	public final DataType getDataType(int row) {
		final DataType dataType = new DataType();
		dataType.setDatabaseType(getDatabaseType(row));
		dataType.setNullJavaType(getNullJavaType(row));
		dataType.setJavaType(getJavaType(row));
		return dataType;
	}
}
