package com.mini.plugin.ui;

import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.config.DbTableModel;
import com.mini.plugin.extension.StringKt;
import com.mini.plugin.state.DbColumn;
import com.mini.plugin.state.DbTable;
import com.mini.plugin.util.TableUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.EventListener;

import static com.intellij.uiDesigner.core.GridConstraints.*;
import static com.mini.plugin.extension.StringKt.toFieldName;
import static java.util.Optional.ofNullable;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class TableConfigDialog extends JDialog implements EventListener {
    private final DbTableModel model = new DbTableModel();

    public TableConfigDialog(DbTable dbTable) {
        // 弹出层基础设置
        this.setTitle("Table Config " + dbTable.getName());
        this.setPreferredSize(JBUI.size(1440, 700));
        this.setLayout(new BorderLayout(0, 0));
        this.setModal(true);
        // 创建顶部布局并添加到当前窗口布局中
        final JPanel headPanel = new JPanel(new GridLayoutManager(2, 6, JBUI.insets(10), -1, -1));
        TableConfigDialog.this.add(headPanel, BorderLayout.NORTH);
        // 创建表名标签并添加到顶部布局
        final JLabel tableNameLabel = new JLabel("Table Name");
        headPanel.add(tableNameLabel, new GridConstraints(0, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建表格名称输入器并添加到顶部布局
        final JTextField tableNameField = new JTextField(dbTable.getName());
        headPanel.add(tableNameField, new GridConstraints(0, 1, 1, 1, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tableNameField.setEnabled(false);
        // 创建类名标签并添加到顶部布局
        final JLabel classNameLabel = new JLabel("Class Name");
        headPanel.add(classNameLabel, new GridConstraints(0, 2, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建类名输入框并添加到顶部布局
        final JTextField classNameField = new JTextField(dbTable.getEntityName());
        headPanel.add(classNameField, new GridConstraints(0, 3, 1, 1, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        // 创建表字段名前缀标签并添加到顶部布局
        final JLabel namePrefixLabel = new JLabel("Column Name Prefix");
        headPanel.add(namePrefixLabel, new GridConstraints(0, 4, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建表字段前缀输入框并添加到项部布局
        final JTextField namePrefixField = new JTextField(dbTable.getNamePrefix());
        headPanel.add(namePrefixField, new GridConstraints(0, 5, 1, 1, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        // 创建表说明标签并添加到顶部布局
        final JLabel tableCommentLabel = new JLabel("Table Comment");
        headPanel.add(tableCommentLabel, new GridConstraints(1, 0, 1, 1, ANCHOR_NORTHWEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建表说明标签到项部布局
        final JTextArea tableCommentField = new JTextArea(dbTable.getComment());
        tableCommentField.setBorder(new CustomLineBorder(1, 1, 1, 1));
        headPanel.add(tableCommentField, new GridConstraints(1, 1, 1, 5, ANCHOR_NORTHWEST, FILL_BOTH, SIZEPOLICY_WANT_GROW, SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        // 创建中心布局并添加到窗体布局中
        final JPanel centerPanel = new JPanel(new BorderLayout(0, 0));
        centerPanel.setBorder(new CustomLineBorder(1, 0, 1, 0));
        this.add(centerPanel, BorderLayout.CENTER);
        // 创建滚动面板并添加到中心布局中心;
        final JBScrollPane scrollPane = new JBScrollPane();
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBorder(new CustomLineBorder(0, 0, 0, 0));
        // 创建表格布局并添加到滚动面板
        final JBTable jbTable = new JBTable(model);
        scrollPane.setViewportView(jbTable);
        jbTable.setBorder(new CustomLineBorder(0, 0, 0, 0));
        jbTable.setSelectionMode(SINGLE_SELECTION);
        // 设置表格列宽
        ofNullable(jbTable.getColumnModel()).ifPresent(it -> {
            it.getColumn(0).setPreferredWidth(100);
            it.getColumn(1).setPreferredWidth(200);
            it.getColumn(2).setPreferredWidth(200);
            it.getColumn(3).setPreferredWidth(240);
        });

        // 创建底部面板并添加到当前窗体布局中
        final JPanel footPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        this.add(footPanel, BorderLayout.SOUTH);

        // 确定按钮
        JButton okButton = new JButton("OK");
        getRootPane().setDefaultButton(okButton);
        footPanel.add(okButton);

        // 取消按钮
        JButton cancelButton = new JButton("Cancel");
        footPanel.add(cancelButton);

        // 添加前缀名输入焦点事件
        namePrefixField.addFocusListener(new FocusListener() {
            // 得到焦点
            public void focusGained(FocusEvent e) {
            }

            // 失去焦点
            public void focusLost(FocusEvent e) {
                dbTable.setNamePrefix(namePrefixField.getText());
                dbTable.getColumnList().forEach(column -> {
                    String it = toFieldName(column.getName(), dbTable.getNamePrefix());
                    column.setFieldName(StringKt.toJavaName(it, false));
                });
                // 重新显示
                model.setTableData(dbTable.getColumnList());
            }
        });

        // 表格失去焦点时的回调事件，用于保存修改表格后的值
        model.addChangeListener((row, column) -> {
            DbColumn it = dbTable.getDbColumn(model.getRowName(row));
            if (it != null) model.resetRow(row, it);
        });

        // 确定按钮点击事件
        okButton.addActionListener(event -> {
            dbTable.setNamePrefix(namePrefixField.getText());
            dbTable.setComment(tableCommentField.getText());
            dbTable.setEntityName(classNameField.getText());
            TableUtil.saveDbTableToSetting(dbTable);
            dispose();
        });

        // 取消按钮点击事件
        cancelButton.addActionListener(event -> dispose());

        // 计算大小
        pack();
        setLocationRelativeTo(null);
        // 加载数据
        model.setTableData(dbTable.getColumnList());
    }
}