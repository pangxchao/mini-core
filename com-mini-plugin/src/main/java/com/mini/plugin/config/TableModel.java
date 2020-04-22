package com.mini.plugin.config;

import com.intellij.openapi.util.text.StringUtil;

import java.util.EventListener;
import java.util.Optional;

/**
 * 表信息配置数据模型
 * @author xchao
 */
public class TableModel extends AbstractTableModel<ColumnInfo> implements EventListener {
	private static final Class<?>[] TYPES = {
			// 0-数据库字段名称
			String.class,
			// 1-数据库字段类型
			String.class,
			// 2-java字段名称
			String.class,
			// 3-字段说明
			String.class,
			// 4-是否为主键
			Boolean.class,
			// 5-是否为自增长字段
			Boolean.class,
			// 6-是否为创建时间字段
			Boolean.class,
			// 7-是否为修改时间字段
			Boolean.class,
			// 8-是否为标识删除突
			Boolean.class,
			// 9-标识删除的值
			Integer.class,
			// 10-是否为锁字段
			Boolean.class
	};
	
	public TableModel() {
		// 0-数据库字段名称
		addColumn("Column Name");
		// 1-数据库字段类型
		addColumn("Column Type");
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
	
	/**
	 * 获取每列的数据类型
	 * @param columnIndex 列编号
	 * @return 数据类型
	 */
	@Override
	public Class getColumnClass(int columnIndex) {
		return TYPES[columnIndex];
	}
	
	public boolean isCellEditable(int row, int column) {
		return column > 1;
	}
	
	protected boolean isExtColumn(int row) {
		return row >= 0;
	}
	
	public synchronized final void addRow(ColumnInfo info) {
		Optional.ofNullable(info).ifPresent(m -> {
			Object[] array = new Object[]{"", "VARCHAR", "", "", false,
					false, false, false, false, 0, false};
			// 0-数据库字段名称
			if (StringUtil.isNotEmpty(m.getColumnName())) {
				array[0] = m.getColumnName();
			}
			// 1-数据库字段类型
			if (StringUtil.isNotEmpty(m.getDatabaseType())) {
				array[1] = m.getDatabaseType().toUpperCase();
			}
			// 2-java字段名称
			if (StringUtil.isNotEmpty(m.getFieldName())) {
				array[2] = m.getFieldName();
			}
			// 3-字段说明
			if (StringUtil.isNotEmpty(m.getComment())) {
				array[3] = m.getComment();
			}
			// 4-是否为主键
			array[4] = m.isId();
			// 5-是否为自增长字段
			array[5] = m.isAuto();
			// 6-是否为创建时间字段
			array[6] = m.isCreateAt();
			// 7-是否为修改时间字段
			array[7] = m.isUpdateAt();
			// 8-是否为标识删除突
			array[8] = m.isDel();
			// 9-标识删除的值
			array[9] = m.getDelValue();
			// 10-是否为锁字段
			array[10] = m.isLock();
			// 添加数据到新行
			TableModel.this.addRow(array);
		});
	}
	
	public String getColumnInfoName(int row) {
		return (String) getValueAt(row, 0);
	}
	
	public String getDatabaseType(int row) {
		return (String) getValueAt(row, 1);
	}
	
	public String getFieldName(int row) {
		return (String) getValueAt(row, 2);
	}
	
	public String getComment(int row) {
		return (String) getValueAt(row, 3);
	}
	
	public Boolean isId(int row) {
		return (Boolean) getValueAt(row, 4);
	}
	
	public Boolean isAuto(int row) {
		return (Boolean) getValueAt(row, 5);
	}
	
	public Boolean isCreateAt(int row) {
		return (Boolean) getValueAt(row, 6);
	}
	
	public Boolean isUpdateAt(int row) {
		return (Boolean) getValueAt(row, 7);
	}
	
	public Boolean isDel(int row) {
		return (Boolean) getValueAt(row, 8);
	}
	
	public Integer getDelValue(int row) {
		return (Integer) getValueAt(row, 9);
	}
	
	public Boolean isLock(int row) {
		return (Boolean) getValueAt(row, 10);
	}
}
