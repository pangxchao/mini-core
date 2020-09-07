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
            java.lang.Boolean.class,  // 7-是否为修改时间字段
            java.lang.Boolean.class,  // 8-是否为标识删除突
            java.lang.Integer.class,  // 9-标识删除的值
            java.lang.Boolean.class   // 10-是否为锁字段
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
        addColumn("CreateAt");
        // 7-是否为修改时间字段
        addColumn("UpdateAt");
        // 8-是否为标识删除突
        addColumn("Del");
        // 9-标识删除的值
        addColumn("Del Val");
        // 10-是否为锁字段
        addColumn("Lock");
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
                value.isCreateAt(),
                value.isUpdateAt(),
                value.isDel(),
                value.getDelValue(),
                value.isLock()
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
        column.setCreateAt(getValueAtBool(row, 6));
        column.setUpdateAt(getValueAtBool(row, 7));
        column.setDel(getValueAtBool(row, 8));
        column.setDel(getValueAtBool(row, 8));
        column.setDelValue(getValueAtInt(row, 9));
        column.setLock(getValueAtBool(row, 10));
    }
}