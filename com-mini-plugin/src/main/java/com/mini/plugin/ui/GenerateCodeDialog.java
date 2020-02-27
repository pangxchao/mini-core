package com.mini.plugin.ui;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiPackage;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.config.GroupDB;
import com.mini.plugin.config.Settings;
import com.mini.plugin.config.TableInfo;
import com.mini.plugin.config.Template;
import com.mini.plugin.util.ModuleUtil;
import com.mini.plugin.util.PackageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Optional;

import static com.intellij.ide.util.PackageUtil.findOrCreateDirectoryForPackage;
import static com.intellij.openapi.ui.Messages.showWarningDialog;
import static com.intellij.uiDesigner.core.GridConstraints.*;
import static com.mini.plugin.config.Settings.getInstance;
import static com.mini.plugin.util.Constants.CHOOSE_PACKAGE;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.GroovyUtil.generate;
import static com.mini.plugin.util.ModuleUtil.getModuleByName;
import static com.mini.plugin.util.ModuleUtil.getModuleNames;
import static com.mini.plugin.util.TableUtil.createTableInfo;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class GenerateCodeDialog extends JDialog implements EventListener {
	private final List<JCheckBox> checkBoxList = new ArrayList<>();
	private final TableInfo tableInfo;
	private PsiDirectory sourceDir;
	private PsiPackage psiPackage;
	private final Project project;
	private PsiDirectory packageDir;
	private Module module;

	public GenerateCodeDialog(Project project, DbTable table) {
		this.tableInfo = createTableInfo(project, table);
		this.project = project;
		// 设置当前弹出窗口布局
		this.setModal(true);
		this.setLayout(new BorderLayout(0, 0));
		this.setMinimumSize(JBUI.size(400, -1));
		// 主面板布局
		JPanel contentPanel = new JPanel(new GridLayoutManager(4, 3, JBUI.insets(10), -1, -1));
		this.add(contentPanel, BorderLayout.CENTER);
		// 创建 Module 名称标签并添加到窗口布局
		JLabel moduleLabel = new JLabel("Module");
		contentPanel.add(moduleLabel, new GridConstraints(0, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED,
				SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建Module选择器并添加到当前窗口布局
		ComboBox<String> moduleField = new ComboBox<>(getModuleNames(project));
		contentPanel.add(moduleField, new GridConstraints(0, 1, 1, 2, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_CAN_GROW,
				SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建源码目录标签并添加到当前窗口布局
		JLabel packageLabel = new JLabel("Package");
		contentPanel.add(packageLabel, new GridConstraints(1, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED,
				SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建源码输入框并添加到当前窗口布局
		JTextField packageField = new JTextField("");
		contentPanel.add(packageField, new GridConstraints(1, 1, 1, 1, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_WANT_GROW,
				SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		// 添加源码选择按钮并添加到当前窗口布局
		JButton packageButton = new JButton("Choose");
		contentPanel.add(packageButton, new GridConstraints(1, 2, 1, 1, ANCHOR_CENTER, FILL_HORIZONTAL,
				SIZEPOLICY_CAN_SHRINK | SIZEPOLICY_CAN_GROW, SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建源码目录标签并添加到当前窗口布局
		JLabel sourceLabel = new JLabel("Source Path");
		contentPanel.add(sourceLabel, new GridConstraints(2, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED,
				SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建源码输入框并添加到当前窗口布局
		JTextField sourceField = new JTextField("");
		contentPanel.add(sourceField, new GridConstraints(2, 1, 1, 2, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_WANT_GROW,
				SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		sourceField.setEnabled(false);
		// 创建源码目录标签并添加到当前窗口布局
		JLabel templateLabel = new JLabel("Template");
		contentPanel.add(templateLabel, new GridConstraints(3, 0, 1, 1, ANCHOR_NORTHWEST, FILL_NONE, SIZEPOLICY_FIXED,
				SIZEPOLICY_FIXED, null, null, null, 0, false));
		// 创建源码输入框并添加到当前窗口布局
		JPanel templatePanel = new JPanel(new GridLayout(0, 3));
		contentPanel.add(templatePanel, new GridConstraints(3, 1, 1, 2, ANCHOR_CENTER, FILL_BOTH,
				SIZEPOLICY_CAN_SHRINK | SIZEPOLICY_CAN_GROW,
				SIZEPOLICY_CAN_SHRINK | SIZEPOLICY_CAN_GROW,
				null, null, null, 0, false));
		// 按钮布局
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.add(buttonPanel, BorderLayout.SOUTH);
		// 确定按钮
		final JButton okButton = new JButton("OK");
		okButton.addActionListener(this::okButton);
		buttonPanel.add(okButton);
		// 取消按钮
		final JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this::cancelButton);
		buttonPanel.add(cancelButton);
		// 默认选中的按钮
		getRootPane().setDefaultButton(okButton);

		// 获取所有模板
		Optional.ofNullable(getInstance().getCurrentGroupDB())
				.map(GroupDB::getElements).ifPresent(elements ->
				elements.forEach((key, value) -> {
					JCheckBox box = new JCheckBox(key);
					templatePanel.add(box);
					checkBoxList.add(box);
					box.setSelected(true);
				}));

		//监听module选择事件
		moduleField.addActionListener(event ->
				ofNullable(moduleField.getSelectedItem())
						.map(o -> (String) o)
						.map(n -> getModuleByName(project, n))
						.ifPresent(m -> this.module = m));

		// 包选择按钮点击事件
		packageButton.addActionListener(event -> ofNullable(module)
				.map(PackageUtil::selectPackage).ifPresent(p -> {
					tableInfo.setPackageName(p.getQualifiedName());
					packageField.setText(p.getQualifiedName());
					ofNullable(findOrCreateDirectoryForPackage(module, p.getQualifiedName(), null, false)).ifPresent(dir -> {
						String[] packages = p.getQualifiedName().split("[.]");
						GenerateCodeDialog.this.packageDir = dir;
						GenerateCodeDialog.this.sourceDir = dir;
						for (int i = 0; sourceDir != null && i < packages.length; i++) {
							sourceDir = sourceDir.getParent();
						}
						// 显示路径
						of(dir).map(PsiDirectory::getVirtualFile).map(VirtualFile::getPath)
								.ifPresent(sourceField::setText);
						psiPackage = p;
					});
				}));

		// 默认选择 Module
		Optional.ofNullable(moduleField.getSelectedItem()).ifPresent(name ->
				module = ModuleUtil.getModuleByName(project, (String) name));

		// 设置标题
		this.setTitle("Mini Code Generate Dialog");
		this.pack();
		this.setLocationRelativeTo(null);
	}

	// 确定按钮点击事件
	protected synchronized void okButton(ActionEvent event) {
		if (sourceDir == null || psiPackage == null) {
			showWarningDialog(CHOOSE_PACKAGE, TITLE_INFO);
			return;
		}
		checkBoxList.stream().filter(AbstractButton::isSelected)
				.map(AbstractButton::getText).forEach(name -> {
			Settings settings = Settings.getInstance();
			GroupDB db = settings.getCurrentGroupDB();
			Optional.ofNullable(db).map(v -> v.get(name))
					.map(Template::getCode).ifPresent(code -> {
				tableInfo.setPackageName(psiPackage.getQualifiedName());
				generate(code, tableInfo, sourceDir.getVirtualFile());

				// 刷新并格式化代码
				packageDir.getVirtualFile().refresh(true, true);
				this.dispose();
			});
		});
	}

	// 取消按钮点击事件
	protected synchronized void cancelButton(ActionEvent event) {
		this.dispose();
	}
}
