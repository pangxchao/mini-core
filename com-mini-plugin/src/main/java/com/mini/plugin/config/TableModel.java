package com.mini.plugin.config;

import javax.swing.table.DefaultTableModel;
import java.util.EventListener;
import java.util.Optional;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;

public class TableModel extends DefaultTableModel implements EventListener {
	private static final Class<?>[] TYPES = {
			String.class,
			String.class,
			String.class,
			String.class,
			Boolean.class,
			Boolean.class,
			Boolean.class,
			Boolean.class,
			Boolean.class,
			Integer.class,
			Boolean.class
	};
	
	public TableModel() {
		addColumn("Column Name");
		addColumn("Column Type");
		addColumn("Field Name");
		addColumn("Comment");
		addColumn("Id");
		addColumn("Auto");
		addColumn("CreateAt");
		addColumn("UpdateAt");
		addColumn("Del");
		addColumn("Del Val");
		addColumn("Lock");
	}
	
	@Override
	public Class getColumnClass(int columnIndex) {
		return TYPES[columnIndex];
	}
	
	public synchronized final void addRow(ColumnInfo info) {
		Optional.ofNullable(info).ifPresent(m -> TableModel.this.addRow(new Object[]{
				defaultIfEmpty(m.getColumnName(), ""),
				defaultIfEmpty(m.getDbType(), "VARCHAR").toUpperCase(),
				defaultIfEmpty(m.getFieldName(), ""),
				defaultIfEmpty(m.getComment(), ""),
				m.isId(),
				m.isAuto(),
				m.isCreateAt(),
				m.isUpdateAt(),
				m.isDel(),
				m.getDelValue(),
				m.isLock()
		}));
	}
	
	public final void removeAllRow() {
		int length = this.getRowCount();
		for (int i = 0; i < length; i++) {
			super.removeRow(0);
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if (column <= 8 && !isExtColumn(row)) {
			return false;
		}
		return super.isCellEditable(row, column);
	}
	
	protected boolean isExtColumn(int row) {
		return false;
	}
}
