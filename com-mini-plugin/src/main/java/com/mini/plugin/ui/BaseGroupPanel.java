package com.mini.plugin.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.config.AbstractGroup;
import com.mini.plugin.config.Settings;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.Serializable;
import java.util.List;
import java.util.*;

import static com.intellij.openapi.ui.MessageDialogBuilder.yesNo;
import static com.intellij.openapi.ui.Messages.showInputDialog;
import static com.mini.plugin.util.Constants.CONFIRM_DELETE_GROUP;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static java.awt.event.ItemEvent.SELECTED;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;


/**
 * 分组面板-负责抽象出创建分组、删除分组、复制分组
 * @author xchao
 */
public abstract class BaseGroupPanel<E, T extends AbstractGroup<E, T>> extends JPanel implements Serializable, EventListener {
	private final CollectionComboBoxModel<String> comboBoxModel;
	private final ComboBox<String> comboBox = new ComboBox<>();
	
	@NotNull
	private synchronized List<String> getNameList() {
		return Optional.of(getGroupData())
			.map(Map::keySet)
			.map(ArrayList::new)
			.orElse(new ArrayList<>());
	}
	
	// 输入元素名称
	private synchronized String inputGroupName(String value) {
		InputValidator inputValidator = new InputValidator() {
			public final boolean checkInput(String text) {
				return StringUtil.isNotEmpty(text) && //
					!getNameList().contains(text);
			}
			
			@Override
			public boolean canClose(String inputString) {
				return this.checkInput(inputString);
			}
		};
		return showInputDialog("Group Name", TITLE_INFO, //
			null, value, inputValidator);
	}
	
	private synchronized T getGroupItem(String name) {
		return Optional.of(getGroupData())
			.map(data -> data.get(name))
			.orElse(null);
	}
	
	private synchronized void resetModelData(String name) {
		BaseGroupPanel.this.comboBoxModel.removeAll();
		this.comboBoxModel.add(getNameList());
		this.comboBoxModel.update();
		// 设置选中项
		if (StringUtil.isNotEmpty(name)) {
			comboBox.setSelectedItem(name);
		} else comboBox.setSelectedIndex(0);
	}
	
	protected BaseGroupPanel(String groupName) {
		this.setLayout(new BorderLayout());
		// 创建一个内容面板
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// 添加分组标签说明
		panel.add(new Label("Group Name"));
		// 添加分组下拉框
		panel.add(this.comboBox);
		// 添加分组选中事件
		this.comboBox.addItemListener(event -> //
			Optional.ofNullable(event)
				.filter(e -> e.getStateChange() == SELECTED)
				.map(ItemEvent::getItem)
				.map(o -> (String) o)
				.ifPresent(this::selectedHandler));
		// 创建事件按钮并在面板上添加按钮工具
		panel.add(createActionGroupToolBar());
		// 设置面板默认大小
		panel.setPreferredSize(JBUI.size(600, 40));
		// 将内容面板添加至主面板左边(西边)
		this.add(panel, BorderLayout.WEST);
		// 添加分组数据到下拉列表
		comboBoxModel = new CollectionComboBoxModel<>();
		this.comboBox.setModel(this.comboBoxModel);
		this.resetModelData(groupName);
	}
	
	// 创建事件按钮
	private JComponent createActionGroupToolBar() {
		DefaultActionGroup action = new DefaultActionGroup();
		action.add(new AnAction(AllIcons.Actions.Copy) { // 复制事件
			public final void actionPerformed(@NotNull AnActionEvent e) {
				String name = (String) comboBox.getSelectedItem();
				String newName = inputGroupName(name + "Copy");
				if (StringUtil.isEmpty(newName)) {
					return;
				}
				ofNullable(getGroupItem(name)).ifPresent(group -> {
					BaseGroupPanel.this.copyHandler(name, newName);
					resetModelData(newName);
				});
			}
			
			public final void update(@NotNull AnActionEvent e) {
				Presentation pre = e.getPresentation();
				if (comboBox.getSelectedIndex() >= 0) {
					pre.setEnabled(true);
					return;
				}
				pre.setEnabled(false);
			}
		});
		action.add(new AnAction(AllIcons.Actions.Edit) { // 修改名称
			public void actionPerformed(@NotNull AnActionEvent e) {
				String name = (String) comboBox.getSelectedItem();
				if (Objects.equals(name, Settings.DEFAULT_NAME)) {
					return;
				}
				String newName = inputGroupName(name);
				if (StringUtil.isEmpty(newName)) {
					return;
				}
				BaseGroupPanel.this.renameHandler(name, newName);
				resetModelData(newName);
			}
			
			public final void update(@NotNull AnActionEvent e) {
				String name = (String) comboBox.getSelectedItem();
				if (Objects.equals(name, Settings.DEFAULT_NAME)) {
					e.getPresentation().setEnabled(false);
					return;
				}
				e.getPresentation().setEnabled(true);
			}
		});
		action.add(new AnAction(AllIcons.General.Add) { // 新增事件
			public void actionPerformed(@NotNull AnActionEvent e) {
				String name = inputGroupName("");
				if (StringUtil.isEmpty(name)) {
					return;
				}
				addHandler(name);
				resetModelData(name);
			}
		});
		action.add(new AnAction(AllIcons.General.Remove) { // 删除事件
			public void actionPerformed(@NotNull AnActionEvent e) {
				String name = (String) comboBox.getSelectedItem();
				if (Objects.equals(name, Settings.DEFAULT_NAME)) {
					return;
				}
				if (yesNo(TITLE_INFO, format(CONFIRM_DELETE_GROUP, name)).isYes()) {
					BaseGroupPanel.this.deleteHandler(name);
					resetModelData(null);
				}
			}
			
			public final void update(@NotNull AnActionEvent e) {
				String name = (String) comboBox.getSelectedItem();
				if (Objects.equals(name, Settings.DEFAULT_NAME)) {
					e.getPresentation().setEnabled(false);
					return;
				}
				e.getPresentation().setEnabled(true);
			}
		});
		String title = "Group Toolbar";
		ActionManager m = ActionManager.getInstance();
		return m.createActionToolbar(title, action, //
			true).getComponent();
	}
	
	
	// 修改名称操作
	protected abstract void renameHandler(String name, String newName);
	
	// 复制操作
	protected abstract void copyHandler(String name, String newName);
	
	// 分组数据改变之前的回调
	protected abstract void selectedHandler(String name);
	
	// 新增分组操作
	protected abstract void deleteHandler(String name);
	
	// 获取分组数据
	@NotNull
	protected abstract Map<String, T> getGroupData();
	
	// 新增分组操作
	protected abstract void addHandler(String name);
}
