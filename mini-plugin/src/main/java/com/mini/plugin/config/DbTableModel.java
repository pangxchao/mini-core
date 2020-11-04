package com.mini.plugin.config;

import com.mini.plugin.state.DbColumn;

import java.util.EventListener;

/**
 * 表信息配置数据模型
 *
 * @author xchao
 */
public class DbTableModel extends AbstractTableModel<DbColumn> implements EventListener {

    private static final Class<?>[] TYPES = new Class[]{
            java.lang.String.class,   // 0-数据库字段类型
            java.lang.String.class,   // 1-数据库字段名称
            java.lang.String.class,   // 2-java实体名称
            java.lang.String.class,   // 3-字段刘明
            java.lang.Boolean.class,  // 4-是否为主键
            java.lang.Boolean.class,  // 5-是否为自增长字段
            java.lang.Boolean.class,  // 6-是否为创建时间字段
            java.lang.Boolean.class,  // 7-是否为创建者
            java.lang.Boolean.class,   // 8-是否为修改时间
            java.lang.Boolean.class,   // 9-是否为修改者
            java.lang.Boolean.class   // 10-是否为版本字段
    };

    public DbTableModel() {
        // 0-数据库字段类型
        addColumn("Column Type");
        // 1-数据库字段名称
        addColumn("Column Name");
        // 2-java字段名称
        addColumn("Field Name");
        // 3-字段说明
        addColumn("Comment");
        // 4-是否为主键
        addColumn("Id");
        // 5-是否为自增长字段
        addColumn("Auto");
        // 6-是否为创建时间字段
        addColumn("CreatedDate");
        // 7-是否为创建时间字段
        addColumn("CreatedBy");
        // 8-是否为修改时间字段
        addColumn("ModifiedDate");
        // 9-是否为修改时间字段
        addColumn("ModifiedBy");
        // 10-是否为版本字段
        addColumn("Version");
    }

    @Override
    public void addRow(DbColumn value) {
        this.addRow(new Object[]{
                value.getDatabaseType().toUpperCase(),
                value.getColumnName(),
                value.getFieldName(),
                value.getComment(),
                value.isId(),
                value.isAuto(),
                value.isCreatedDate(),
                value.isCreatedBy(),
                value.isModifiedDate(),
                value.isModifiedBy(),
                value.isVersion()
        });
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return TYPES[columnIndex];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column > 1;
    }

    @Override
    public String getRowName(int row) {
        return getValueAtString(row, 1);
    }

    public void resetRow(int row, DbColumn column) {
        column.setFieldName(getValueAtString(row, 2));
        column.setComment(getValueAtString(row, 3));
        column.setId(getValueAtBool(row, 4));
        column.setAuto(getValueAtBool(row, 5));
        column.setCreatedDate(getValueAtBool(row, 6));
        column.setCreatedBy(getValueAtBool(row, 7));
        column.setModifiedDate(getValueAtBool(row, 8));
        column.setModifiedBy(getValueAtBool(row, 9));
        column.setVersion(getValueAtBool(row, 10));
    }
}