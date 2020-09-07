package com.mini.plugin.config;

import com.mini.plugin.state.DataType;

/**
 * 数据类型映射数据模型
 *
 * @author xchao
 */
public class DataTypeModel extends AbstractTableModel<DataType> {
    public DataTypeModel() {
        // 数据对应数据库字段类型
        addColumn("Database Type");
        // 数据对应Java属性类型
        addColumn("Java Type");
    }

    @Override
    public final void addRow(DataType value) {
        final Object[] arr = new Object[2];
        arr[0] = value.getDatabaseType();
        arr[1] = value.getJavaType();
        this.addRow(arr);
    }

    @Override
    public String getRowName(int row) {
        return getValueAtString(row, 0);
    }
}