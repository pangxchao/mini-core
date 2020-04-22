package com.mini.plugin.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.mini.plugin.config.DataType;
import com.mini.plugin.config.DataTypeGroup;
import com.mini.plugin.config.DataTypeModel;
import com.mini.plugin.config.Settings;
import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

import static com.intellij.openapi.ui.MessageDialogBuilder.yesNo;
import static com.mini.plugin.config.Settings.DEFAULT_NAME;
import static com.mini.plugin.util.Constants.CONFIRM_DELETE_MAPPER;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static java.util.Optional.ofNullable;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static org.jetbrains.annotations.Nls.Capitalization.Title;

public final class DataTypeSetting extends JPanel implements Configurable {
	private final DataTypeModel mapperModel = new DataTypeModel();
	private TreeMap<String, DataTypeGroup> data;
	private final Settings settings;
	private final JBTable table;
	private String groupName;
	private boolean modified;
	
	private synchronized void resetModelData() {
		Optional.ofNullable(data.get(groupName))
				.map(DataTypeGroup::getDataTypeMap)
				.ifPresent(mapperModel::setData);
	}
	
	protected DataTypeSetting(Settings settings, Project project) {
		DataTypeSetting.this.setLayout(new BorderLayout());
		groupName = settings.getDataTypeGroupName();
		data = settings.getDataTypeGroupMap();
		this.settings = settings;
		
		// 创建分组面板并添加分组面板在主面板上边
		DataTypeSetting.this.add(new BaseGroupPanel<DataTypeGroup>(groupName) {
			protected final void renameHandler(String name, String newName) {
				ofNullable(data.get(name)).ifPresent(group -> {
					DataTypeSetting.this.data.remove(name);
					DataTypeSetting.this.modified = true;
					group.setGroupName(newName);
					data.put(newName, group);
					groupName = newName;
					resetModelData();
				});
			}
			
			protected final void copyHandler(String name, String newName) {
				ofNullable(data.get(name)).ifPresent(group -> {
					DataTypeSetting.this.modified = true;
					DataTypeGroup g = group.clone();
					g.setGroupName(newName);
					data.put(newName, g);
					groupName = newName;
					resetModelData();
				});
			}
			
			@NotNull
			protected final Map<String, DataTypeGroup> getGroupData() {
				return DataTypeSetting.this.data;
			}
			
			protected final void selectedHandler(String name) {
				if (!Objects.equals(groupName, name)) {
					groupName = name;
					modified = true;
					resetModelData();
				}
			}
			
			protected final void deleteHandler(String name) {
				if (StringUtil.equals(DEFAULT_NAME, name)) {
					return;
				}
				DataTypeSetting.this.data.remove(name);
				DataTypeSetting.this.modified = true;
				if (!DataTypeSetting.this.data.isEmpty()) {
					groupName = data.firstKey();
				} else groupName = DEFAULT_NAME;
				resetModelData();
			}
			
			protected final void addHandler(String name) {
				DataTypeGroup group = new DataTypeGroup();
				group.setGroupName(name);
				data.put(name, group);
				groupName = name;
				modified = true;
				resetModelData();
			}
		}, BorderLayout.NORTH);
		
		// 创建数据面板并添加到主面板的中心
		JPanel centerPanel = new JPanel(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		
		// 创建数据添加按钮和删除按钮，并添加到数据面板右边
		JComponent toolBar = createActionGroupToolBar();
		toolBar.setBorder(new CustomLineBorder(1, 0, 1, 1));
		centerPanel.add(toolBar, BorderLayout.EAST);
		
		// 创建滚动布局并添加滚动面板到数据面板心
		JBScrollPane scrollPane = new JBScrollPane();
		scrollPane.setBorder(new CustomLineBorder(1, 1, 1, 1));
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		
		// 创建表格布局并添加到滚动布局
		scrollPane.setViewportView((table = new JBTable(mapperModel)));
		DataTypeSetting.this.mapperModel.addCallChangeListener((row, column) -> {
			final DataTypeGroup group = new DataTypeGroup();
			for (int i = 0; i < mapperModel.getRowCount(); i++) {
				DataType dataType = mapperModel.getDataType(i);
				group.addDataType(dataType);
			}
			group.setGroupName(groupName);
			data.put(groupName, group);
		});
		// 表格只能单选
		table.setSelectionMode(SINGLE_SELECTION);
		// 重新渲染数据
		resetModelData();
	}
	
	// 创建事件按钮
	private JComponent createActionGroupToolBar() {
		DefaultActionGroup action = new DefaultActionGroup();
		// 新增事件
		action.add(new AnAction(AllIcons.General.Add) {
			public void actionPerformed(@NotNull AnActionEvent e) {
				ofNullable(data.get(groupName)).ifPresent(group -> {
					// 创建类型映射对象
					final DataType dataType = new DataType();
					dataType.setDatabaseType("DatabaseType");
					dataType.setNullJavaType("NullableType");
					dataType.setJavaType("JavaType");
					group.addDataType(dataType);
					modified = true;
					resetModelData();
				});
				// 在表格中添加一行
				//mapperModel.addRow(dataType);
			}
		});
		// 删除事件
		action.add(new AnAction(AllIcons.General.Remove) {
			public void actionPerformed(@NotNull AnActionEvent e) {
				int row = DataTypeSetting.this.table.getSelectedRow();
				if (row >= 0 && yesNo(TITLE_INFO, CONFIRM_DELETE_MAPPER).isYes()) {
					ofNullable(data.get(groupName)).ifPresent(group -> {
						String key = mapperModel.getDatabaseType(row);
						group.getDataTypeMap().remove(key);
						resetModelData();
					});
				}
			}
			
			public final void update(@NotNull AnActionEvent e) {
				Presentation pre = e.getPresentation();
				if (table.getSelectedRow() >= 0) {
					pre.setEnabled(true);
					return;
				}
				pre.setEnabled(false);
			}
		});
		String title = "Mapper Toolbar";
		ActionManager m = ActionManager.getInstance();
		return m.createActionToolbar(title, action, false).getComponent();
	}
	
	// 重置到默认状态
	public synchronized final void resetToDefault() throws Error {
		DataTypeSetting.this.reset();
		modified = true;
	}
	
	// 重置操作
	@Override
	public synchronized final void reset() throws Error {
		groupName = settings.getDataTypeGroupName();
		data = settings.getDataTypeGroupMap();
		modified = false;
		resetModelData();
	}
	
	@Override
	public synchronized final void apply() throws Error {
		settings.setDataTypeGroupName(groupName);
		settings.setDataTypeGroupMap(data);
		modified = false;
	}
	
	@Override
	public JComponent createComponent() {
		return this;
	}
	
	@Override
	@Nls(capitalization = Title)
	public String getDisplayName() {
		return "Data Type";
	}
	
	@Override
	public boolean isModified() {
		return modified;
	}
}
