package com.mini.plugin.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.mini.plugin.config.GroupMapper;
import com.mini.plugin.config.Settings;
import com.mini.plugin.config.TypeMapper;
import com.mini.plugin.config.TypeMapperModel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.intellij.openapi.ui.MessageDialogBuilder.yesNo;
import static com.mini.plugin.util.Constants.CONFIRM_DELETE_MAPPER;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static java.util.Optional.ofNullable;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static org.jetbrains.annotations.Nls.Capitalization.Title;

public final class MapperSetting extends JPanel implements Configurable {
	private final Map<String, GroupMapper> data = new LinkedHashMap<>();
	private final TypeMapperModel mapperModel = new TypeMapperModel();
	private final Settings settings;
	private final JBTable table;
	private String groupName;
	private boolean modified;
	
	@NotNull
	private synchronized LinkedHashMap<String, TypeMapper> getTypeMapperData() {
		return ofNullable(data.get(groupName)).map(GroupMapper::getElements)
			.orElse(new LinkedHashMap<>());
	}
	
	private synchronized void resetModelData() {
		MapperSetting.this.mapperModel.removeAllRow();
		getTypeMapperData().forEach((key, value) -> //
			mapperModel.addRow(value));
	}
	
	private synchronized String getValueAt(int row, int column) {
		return (String) mapperModel.getValueAt(row, column);
	}
	
	protected MapperSetting(Settings settings, Project project) {
		MapperSetting.this.setLayout(new BorderLayout());
		this.groupName = settings.getMapperGroupName();
		this.data.putAll(settings.getMapperGroup());
		this.settings = settings;
		// 创建分组面板并添加分组面板在主面板上边
		add(new BaseGroupPanel<TypeMapper, GroupMapper>(settings.getMapperGroupName()) {
			protected synchronized final void renameHandler(String name, String newName) {
				ofNullable(data.get(name)).ifPresent(mapper -> {
					MapperSetting.this.data.remove(name);
					MapperSetting.this.modified = true;
					data.put(newName, mapper);
				});
			}
			
			protected synchronized final void copyHandler(String name, String newName) {
				ofNullable(data.get(name)).ifPresent(mapper -> {
					MapperSetting.this.modified = true;
					data.put(newName, mapper.copy());
				});
			}
			
			@NotNull
			protected synchronized final Map<String, GroupMapper> getGroupData() {
				return MapperSetting.this.data;
			}
			
			protected synchronized final void selectedHandler(String name) {
				if (!Objects.equals(MapperSetting.this.groupName, name)) {
					MapperSetting.this.groupName = name;
					MapperSetting.this.modified  = true;
				}
				resetModelData();
			}
			
			protected synchronized final void deleteHandler(String name) {
				MapperSetting.this.data.remove(name);
				MapperSetting.this.modified = true;
			}
			
			protected synchronized final void addHandler(String name) {
				MapperSetting.this.modified = true;
				data.put(name, GroupMapper.builder()
					.name(name).build());
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
		MapperSetting.this.mapperModel.addTableModelListener(event -> {
			GroupMapper.Builder builder = GroupMapper.builder()
				.name(groupName);
			for (int i = 0; i < mapperModel.getRowCount(); i++) {
				builder.element(TypeMapper.builder()
					.databaseType(getValueAt(i, 0))
					.nullJavaType(getValueAt(i, 2))
					.javaType(getValueAt(i, 1))
					.build());
			}
			data.put(groupName, builder.build());
		});
		// 表格只能单选
		table.setSelectionMode(SINGLE_SELECTION);
	}
	
	// 创建事件按钮
	private JComponent createActionGroupToolBar() {
		DefaultActionGroup action = new DefaultActionGroup();
		// 新增事件
		action.add(new AnAction(AllIcons.General.Add) {
			public void actionPerformed(@NotNull AnActionEvent e) {
				ofNullable(data.get(groupName)).ifPresent(group -> {
					group.addElement(TypeMapper.builder()
						.nullJavaType("NullableType")
						.databaseType("DBType")
						.javaType("JavaType")
						.build());
					resetModelData();
					modified = true;
				});
			}
		});
		// 删除事件
		action.add(new AnAction(AllIcons.General.Remove) {
			public void actionPerformed(@NotNull AnActionEvent e) {
				int row = MapperSetting.this.table.getSelectedRow();
				if (row >= 0 && yesNo(TITLE_INFO, CONFIRM_DELETE_MAPPER).isYes()) {
					mapperModel.removeRow(row);
					modified = true;
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
		return m.createActionToolbar(title, action, //
			false).getComponent();
	}
	
	// 重置到默认状态
	public synchronized final void reset(Settings settings) {
		MapperSetting.this.data.clear();
		data.putAll(settings.getMapperGroup());
		groupName = settings.getMapperGroupName();
		modified  = true;
	}
	
	// 重置操作
	@Override
	public synchronized final void reset() {
		this.reset(settings);
		modified = false;
	}
	
	@Override
	public synchronized final void apply() {
		settings.setMapperGroupName(groupName);
		settings.setMapperGroup(data);
		modified = false;
	}
	
	@Override
	public JComponent createComponent() {
		return this;
	}
	
	@Override
	@Nls(capitalization = Title)
	public String getDisplayName() {
		return "Type Mapper";
	}
	
	@Override
	public boolean isModified() {
		return modified;
	}
}
