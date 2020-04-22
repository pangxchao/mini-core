package com.mini.plugin.config;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTableModel<T> extends DefaultTableModel {
	private List<CellUpdatedListener> callChangeListener = new ArrayList<>();
	
	public void addCallChangeListener(CellUpdatedListener listener) {
		this.callChangeListener.add(listener);
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		return super.getValueAt(row, column);
	}
	
	/**
	 * 添加一行数据
	 * @param value 添加一行数据
	 */
	public abstract void addRow(T value);
	
	/**
	 * 设置表格数据
	 * @param data 表格数据
	 */
	public final void setTableData(List<T> data) {
		AbstractTableModel.this.removeAllRow();
		data.forEach(this::addRow);
	}
	
	/**
	 * 删除所有表格数据
	 * @throws Error 错误
	 */
	public final void removeAllRow() throws Error {
		final int length = this.getRowCount();
		for (int i = 0; i < length; i++) {
			super.removeRow(0);
		}
	}
	
	@Override
	public void fireTableCellUpdated(int row, int column) {
		super.fireTableCellUpdated(row, column);
		callChangeListener.forEach(l -> { //
			l.change(row, column);
		});
	}
	
	public interface CellUpdatedListener {
		void change(int row, int column);
	}
}
