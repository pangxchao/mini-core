package com.mini.plugin.ui;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.config.ColumnInfo;
import com.mini.plugin.config.TableInfo;
import com.mini.plugin.config.TableModel;
import com.mini.plugin.service.TableInfoService;

import javax.swing.*;
import java.awt.*;
import java.util.EventListener;

import static com.intellij.uiDesigner.core.GridConstraints.*;

public class TableConfigDialog extends JDialog implements EventListener {
	private final TableInfoService tableInfoService;
	private final JTextArea tableCommentField;
	private final JTextField namePrefixField;
	private final JTextField tableNameField;
	private final JTextField classNameField;
	private final TableModel tableModel;
	private final TableInfo tableInfo;
	private final JBTable jbTable;
	
	public TableConfigDialog(Project project, DbTable dbTable) {
		this.setLayout(new BorderLayout(0, 0));
		this.setModal(true);
		// 创建表配置读写服务和表配置对象
		tableInfoService = TableInfoService.getInstance(project);
		this.tableInfo   = tableInfoService.createTableInfo(project, dbTable);
		
		// 创建顶部布局并添加到当前窗口布局中
		final JPanel headPanel = new JPanel(new GridLayoutManager(2, 6, JBUI.insets(10), -1, -1));
		TableConfigDialog.this.add(headPanel, BorderLayout.NORTH);
		// 创建表名标签并添加到顶部布局
		final JLabel tableNameLabel = new JLabel("Table Name");
		headPanel.add(tableNameLabel, new GridConstraints(0, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED,
			SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建表格名称输入器并添加到顶部布局
		this.tableNameField = new JTextField(tableInfo.getTableName());
		headPanel.add(tableNameField, new GridConstraints(0, 1, 1, 1, ANCHOR_WEST, FILL_HORIZONTAL,
			SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		tableNameField.setEnabled(false);
		// 创建类名标签并添加到顶部布局
		final JLabel classNameLabel = new JLabel("Class Name");
		headPanel.add(classNameLabel, new GridConstraints(0, 2, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED,
			SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建类名输入框并添加到顶部布局
		this.classNameField = new JTextField(tableInfo.getEntityName());
		headPanel.add(classNameField, new GridConstraints(0, 3, 1, 1, ANCHOR_WEST, FILL_HORIZONTAL,
			SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		// 创建表字段名前缀标签并添加到顶部布局
		JLabel namePrefixLabel = new JLabel("Column Name Prefix");
		headPanel.add(namePrefixLabel, new GridConstraints(0, 4, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED,
			SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建表字段前缀输入框并添加到项部布局
		this.namePrefixField = new JTextField(tableInfo.getNamePrefix());
		headPanel.add(namePrefixField, new GridConstraints(0, 5, 1, 1, ANCHOR_WEST, FILL_HORIZONTAL,
			SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		// 创建表说明标签并添加到顶部布局
		JLabel tableCommentLabel = new JLabel("Table Comment");
		headPanel.add(tableCommentLabel, new GridConstraints(1, 0, 1, 1, ANCHOR_NORTHWEST, FILL_NONE, SIZEPOLICY_FIXED,
			SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建表说明标签到项部布局
		this.tableCommentField = new JTextArea(tableInfo.getComment());
		tableCommentField.setBorder(new CustomLineBorder(1, 1, 1, 1));
		headPanel.add(tableCommentField, new GridConstraints(1, 1, 1, 5, ANCHOR_NORTHWEST, FILL_BOTH, SIZEPOLICY_WANT_GROW,
			SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
		// 创建中心布局并添加到窗体布局中
		JPanel centerPanel = new JPanel(new BorderLayout(0, 0));
		centerPanel.setBorder(new CustomLineBorder(1, 0, 1, 0));
		this.add(centerPanel, BorderLayout.CENTER);
		// 创建添加删除按钮布局并添加到中心布局右边
		final JPanel buttonPanel = new JPanel(new BorderLayout(0, 0));
		centerPanel.add(buttonPanel, BorderLayout.EAST);
		buttonPanel.add(new JButton("+"), BorderLayout.NORTH);
		// 创建滚动面板并添加到中心布局中心
		final JBScrollPane scrollPane1 = new JBScrollPane();
		scrollPane1.setBorder(new CustomLineBorder(0, 0, 0, 0));
		centerPanel.add(scrollPane1, BorderLayout.CENTER);
		scrollPane1.setViewportView((jbTable = new JBTable()));
		jbTable.setBorder(new CustomLineBorder(0, 0, 0, 1));
		jbTable.setModel((this.tableModel = new TableModel() {
			public Class getColumnClass(int columnIndex) {
				return super.getColumnClass(columnIndex);
			}
		}));
		
		// 创建底部面板并添加到当前窗体布局中
		JPanel footPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		this.add(footPanel, BorderLayout.SOUTH);
		// 确定按钮
		JButton okButton = new JButton("OK");
		footPanel.add(okButton);
		// 取消按钮
		JButton cancelButton = new JButton("Cancel");
		footPanel.add(cancelButton);
		
		this.setTitle("Table Config " + tableInfo.getTableName());
		
		this.pack();
		setLocationRelativeTo(null);
		
		for (ColumnInfo columnInfo : tableInfo.getColumns().values()) {
			tableModel.addRow(columnInfo);
		}
	}
}
