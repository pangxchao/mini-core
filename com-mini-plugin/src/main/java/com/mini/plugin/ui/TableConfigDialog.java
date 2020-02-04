package com.mini.plugin.ui;

import com.intellij.database.psi.DbTable;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
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
import com.mini.plugin.util.TableUtil;
import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.EventListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.intellij.uiDesigner.core.GridConstraints.*;
import static com.mini.plugin.util.StringUtil.toFieldName;
import static com.mini.plugin.util.StringUtil.toJavaName;
import static java.util.Optional.ofNullable;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class TableConfigDialog extends JDialog implements EventListener {
	private final JTextArea tableCommentField;
	private final JTextField namePrefixField;
	private final JTextField classNameField;
	private final TableModel tableModel;
	private final TableInfo tableInfo;
	private final JBTable jbTable;
	private final Project project;
	
	private synchronized void resetModelData() {
		TableConfigDialog.this.tableModel.removeAllRow();
		tableInfo.getColumns().forEach((name, info) -> { //
			tableModel.addRow(info);
		});
	}
	
	private Object getValueAt(int row, int column) {
		return tableModel.getValueAt(row, column);
	}
	
	public TableConfigDialog(Project project, DbTable dbTable) {
		this.setLayout(new BorderLayout(0, 0));
		this.setModal(true);
		this.project = project;
		// 创建表配置读写服务和表配置对象
		this.tableInfo = TableUtil.createTableInfo(project, dbTable);
		// 创建顶部布局并添加到当前窗口布局中
		final JPanel headPanel = new JPanel(new GridLayoutManager(2, 6, JBUI.insets(10), -1, -1));
		TableConfigDialog.this.add(headPanel, BorderLayout.NORTH);
		// 创建表名标签并添加到顶部布局
		final JLabel tableNameLabel = new JLabel("Table Name");
		headPanel.add(tableNameLabel, new GridConstraints(0, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED,
			SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建表格名称输入器并添加到顶部布局
		JTextField tableNameField = new JTextField(tableInfo.getTableName());
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
		final JComponent actions = createActionGroupToolBar();
		centerPanel.add(actions, BorderLayout.SOUTH);
		// 创建滚动面板并添加到中心布局中心
		final JBScrollPane scrollPane1 = new JBScrollPane();
		scrollPane1.setBorder(new CustomLineBorder(0, 0, 1, 0));
		centerPanel.add(scrollPane1, BorderLayout.CENTER);
		scrollPane1.setViewportView((jbTable = new JBTable()));
		jbTable.setBorder(new CustomLineBorder(0, 0, 0, 0));
		jbTable.setSelectionMode(SINGLE_SELECTION);
		jbTable.setModel((this.tableModel = new TableModel() {
			protected boolean isExtColumn(int row) {
				return TableConfigDialog.this //
					.isExtColumn(row);
			}
		}));
		// 创建底部面板并添加到当前窗体布局中
		JPanel footPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		this.add(footPanel, BorderLayout.SOUTH);
		// 确定按钮
		JButton okButton = new JButton("OK");
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(this::okHandler);
		footPanel.add(okButton);
		// 取消按钮
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this::cancelHandler);
		footPanel.add(cancelButton);
		// 添加后缀名输入焦点事件
		namePrefixField.addFocusListener(new FocusListener() {
			// 得到焦点
			public final void focusGained(FocusEvent e) {
			}
			
			// 失去焦点
			public final void focusLost(FocusEvent e) {
				String prefix = namePrefixField.getText();
				tableInfo.setNamePrefix(prefix);
				tableInfo.getColumns().forEach((name, info) -> { //
					if (info.isExt()) return;
					String n = toFieldName(info.getColumnName(), prefix);
					info.setFieldName(toJavaName(n, false));
				});
				// 重新显示
				resetModelData();
			}
		});
		
		// 设置标题
		this.setTitle("Table Config " + tableInfo.getTableName());
		// 计算大小
		this.pack();
		setLocationRelativeTo(null);
		// 加载数据
		resetModelData();
	}
	
	// 创建事件按钮
	private JComponent createActionGroupToolBar() {
		DefaultActionGroup action = new DefaultActionGroup();
		// 新增事件
		action.add(new AnAction(AllIcons.General.Add) {
			public void actionPerformed(@NotNull AnActionEvent e) {
				ColumnInfo columnInfo = new ColumnInfo();
				columnInfo.setColumnName("ColumnName");
				columnInfo.setFieldName("FieldName");
				columnInfo.setComment("Comment");
				columnInfo.setDbType("VARCHAR");
				columnInfo.setExt(true);
				tableInfo.addColumn(columnInfo);
				resetModelData();
			}
		});
		// 删除事件
		action.add(new AnAction(AllIcons.General.Remove) {
			public void actionPerformed(@NotNull AnActionEvent e) {
				int row = jbTable.getSelectedRow();
				int size = tableModel.getRowCount();
				if (row < 0 || row >= size) {
					return;
				}
				// 获取字段信息
				String name = (String) tableModel.getValueAt(row, 0);
				if (StringUtil.isEmpty(name)) return;
				ColumnInfo column = tableInfo.getColumns().get(name);
				if (column == null || !column.isExt()) {
					return;
				}
				// 删除操作
				tableInfo.getColumns().remove(name);
				resetModelData();
			}
			
			public final void update(@NotNull AnActionEvent e) {
				Presentation pre = e.getPresentation();
				int row = jbTable.getSelectedRow();
				if (TableConfigDialog.this //
					.isExtColumn(row)) {
					pre.setEnabled(true);
					return;
				}
				pre.setEnabled(false);
			}
		});
		String title = "Mapper Toolbar";
		ActionManager m = ActionManager.getInstance();
		return m.createActionToolbar(title, action, //
			true).getComponent();
	}
	
	// 保存操作
	protected final void okHandler(ActionEvent event) {
		tableInfo.setComment(tableCommentField.getText());
		tableInfo.setEntityName(classNameField.getText());
		tableInfo.setNamePrefix(namePrefixField.getText());
		Map<String, ColumnInfo> map = new LinkedHashMap<>();
		for (int i = 0, size = tableModel.getRowCount(); i < size; i++) {
			final ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setColumnName((String) getValueAt(i, 0));
			columnInfo.setDbType((String) getValueAt(i, 1));
			columnInfo.setFieldName((String) getValueAt(i, 2));
			columnInfo.setComment((String) getValueAt(i, 3));
			columnInfo.setId((Boolean) getValueAt(i, 4));
			columnInfo.setRef((Boolean) getValueAt(i, 5));
			columnInfo.setRefTable((String) getValueAt(i, 6));
			columnInfo.setRefColumn((String) getValueAt(i, 7));
			columnInfo.setAuto((Boolean) getValueAt(i, 8));
			columnInfo.setCreateAt((Boolean) getValueAt(i, 9));
			columnInfo.setUpdateAt((Boolean) getValueAt(i, 10));
			columnInfo.setDel((Boolean) getValueAt(i, 11));
			columnInfo.setDelValue((Integer) getValueAt(i, 12));
			columnInfo.setLock((Boolean) getValueAt(i, 13));
			columnInfo.setExt(Optional.of(tableInfo.getColumns())
				.map(m -> m.get(columnInfo.getColumnName()))
				.map(ColumnInfo::isExt)
				.orElse(true));
			map.put(columnInfo.getColumnName(), columnInfo);
		}
		// 重新设置列表数据
		this.tableInfo.setColumns(map);
		TableUtil.saveTableInfo(project, tableInfo);
		this.dispose();
	}
	
	// 取消操作
	protected final void cancelHandler(ActionEvent event) {
		this.dispose();
	}
	
	// 判断指定行是否为扩展行
	protected final boolean isExtColumn(int row) {
		if (row < 0 || row >= tableModel.getRowCount()) {
			return false;
		}
		return ofNullable(tableModel.getValueAt(row, 0))
			.map(object -> (String) object)
			.filter(columnName -> !columnName.isEmpty())
			.map(c -> tableInfo.getColumns().get(c))
			.map(ColumnInfo::isExt)
			.orElse(true);
	}
}
