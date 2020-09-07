package com.mini.plugin.config;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class AbstractTableModel<T> extends DefaultTableModel {
    private final List<BiConsumer<Integer, Integer>> changeListener = new ArrayList<>();

    public final void addChangeListener(BiConsumer<Integer, Integer> biConsumer) {
        this.changeListener.add(biConsumer);
    }

    /**
     * 删除所有表格数据
     *
     * @throws Error 错误
     */
    public final void removeAllRow() {
        while (this.getRowCount() > 0) {
            super.removeRow(0);
        }
    }

    /**
     * 添加一行数据
     *
     * @param value 添加一行数据
     */
    public abstract void addRow(T value);

    /**
     * 设置表格数据
     *
     * @param collection 表格数据
     */
    public final void setTableData(Collection<T> collection) {
        AbstractTableModel.this.removeAllRow();
        collection.forEach(this::addRow);
    }

    /**
     * 获取单元格String类型的数据
     */
    @NotNull
    public final String getValueAtString(int row, int column) {
        return Optional.ofNullable(getValueAt(row, column))
                .map(Object::toString).orElse("");
    }

    public final int getValueAtInt(int row, int column) {
        return Optional.ofNullable(getValueAt(row, column))
                .map(Object::toString)
                .map(Integer::parseInt)
                .orElse(0);
    }

    public final boolean getValueAtBool(int row, int column) {
        return Optional.ofNullable(getValueAt(row, column))
                .map(Object::toString)
                .map(Boolean::parseBoolean)
                .orElse(false);
    }

    public abstract String getRowName(int row);


    @Override
    public final void fireTableCellUpdated(int row, int column) {
        super.fireTableCellUpdated(row, column);
        changeListener.forEach(it -> { //
            it.accept(row, column);
        });
    }
}