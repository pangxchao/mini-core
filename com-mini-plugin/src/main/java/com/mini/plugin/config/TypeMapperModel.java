package com.mini.plugin.config;

import javax.swing.table.DefaultTableModel;
import java.util.Optional;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;

public final class TypeMapperModel extends DefaultTableModel {
	
	public TypeMapperModel() {
		addColumn("Database Type");
		addColumn("Java Type");
		addColumn("Nullable Type");
	}
	
	public synchronized final void addRow(TypeMapper mapper) {
		Optional.ofNullable(mapper).ifPresent(m -> { //
			TypeMapperModel.this.addRow(new Object[]{
				defaultIfEmpty(m.getDatabaseType(), ""),
				defaultIfEmpty(m.getJavaType(), ""),
				defaultIfEmpty(m.getNullJavaType(), "")
			});
		});
	}
	
	public final void removeAllRow() {
		int length = this.getRowCount();
		for (int i = 0; i < length; i++) {
			super.removeRow(0);
		}
	}
}
