package com.mini.plugin.ui;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.state.DbTable;
import com.mini.plugin.state.TemplateGroup;
import com.mini.plugin.ui.dialog.MiniPackageChooserDialog;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

import static com.intellij.uiDesigner.core.GridConstraints.*;
import static com.mini.plugin.config.Settings.instance;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.GroovyUtil.generate;
import static com.mini.plugin.util.ModuleUtil.getModuleByName;
import static com.mini.plugin.util.ModuleUtil.getModuleNames;
import static java.util.Optional.ofNullable;

public class GenerateDialog extends JDialog implements EventListener {
    private final List<JCheckBox> checkBoxList = new ArrayList<>();
    private VirtualFile rootVirtualFile;
    private Module module;

    public GenerateDialog(@NotNull Project project, @NotNull List<DbTable> dbTableList) {
        // 设置当前弹出窗口布局
        this.setTitle("Mini Code Generate Dialog");
        this.setMinimumSize(JBUI.size(400, -1));
        this.setLayout(new BorderLayout(0, 0));
        this.setModal(true);
        // 主面板布局
        final JPanel contentPanel = new JPanel(new GridLayoutManager(5, 3, JBUI.insets(10), -1, -1));
        this.add(contentPanel, BorderLayout.CENTER);
        // 创建 Module 名称标签并添加到窗口布局
        final JLabel moduleLabel = new JLabel("Module");
        contentPanel.add(moduleLabel, new GridConstraints(0, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建Module选择器并添加到当前窗口布局
        final ComboBox<String> moduleField = new ComboBox<>(getModuleNames(project));
        contentPanel.add(moduleField, new GridConstraints(0, 1, 1, 2, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_CAN_GROW, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建源码目录标签并添加到当前窗口布局
        final JLabel packageLabel = new JLabel("Package");
        contentPanel.add(packageLabel, new GridConstraints(1, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建源码输入框并添加到当前窗口布局
        final JTextField packageField = new JTextField("");
        contentPanel.add(packageField, new GridConstraints(1, 1, 1, 1, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        // 添加源码选择按钮并添加到当前窗口布局
        final JButton packageButton = new JButton("Choose");
        contentPanel.add(packageButton, new GridConstraints(1, 2, 1, 1, ANCHOR_CENTER, FILL_HORIZONTAL, SIZEPOLICY_CAN_SHRINK | SIZEPOLICY_CAN_GROW, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建源码目录标签并添加到当前窗口布局
        final JLabel sourceLabel = new JLabel("Source Path");
        contentPanel.add(sourceLabel, new GridConstraints(2, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建源码输入框并添加到当前窗口布局
        final JTextField sourceField = new JTextField("");
        contentPanel.add(sourceField, new GridConstraints(2, 1, 1, 2, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        sourceField.setEnabled(false);
        // 创建 Module 名称标签并添加到窗口布局
        final JLabel groupNameLabel = new JLabel("Group name");
        contentPanel.add(groupNameLabel, new GridConstraints(3, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建Module选择器并添加到当前窗口布局
        final ComboBox<String> groupNameField = new ComboBox<>(instance.getState().getTemplateGroupMap().getMap().keySet().toArray(new String[0]));
        contentPanel.add(groupNameField, new GridConstraints(3, 1, 1, 2, ANCHOR_WEST, FILL_HORIZONTAL, SIZEPOLICY_CAN_GROW, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建源码目录标签并添加到当前窗口布局
        final JLabel templateLabel = new JLabel("Template");
        contentPanel.add(templateLabel, new GridConstraints(4, 0, 1, 1, ANCHOR_NORTHWEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
        // 创建源码输入框并添加到当前窗口布局
        final JPanel templatePanel = new JPanel(new GridLayout(0, 3));
        contentPanel.add(templatePanel, new GridConstraints(4, 1, 1, 2, ANCHOR_CENTER, FILL_BOTH, SIZEPOLICY_CAN_SHRINK | SIZEPOLICY_CAN_GROW, SIZEPOLICY_CAN_SHRINK | SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        // 按钮布局
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.add(buttonPanel, BorderLayout.SOUTH);
        // 确定按钮
        final JButton okButton = new JButton("OK");
        buttonPanel.add(okButton);
        // 取消按钮
        final JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);
        // 默认选中的按钮
        getRootPane().setDefaultButton(okButton);

        //监听module选择事件
        moduleField.addActionListener(e -> ofNullable(moduleField.getSelectedItem()).map(Object::toString).ifPresent(it -> //
                GenerateDialog.this.module = getModuleByName(project, it)));

        // 包选择按钮点击事件
        packageButton.addActionListener(e -> ofNullable(module).map(it -> new MiniPackageChooserDialog(TITLE_INFO, it)).ifPresent(it -> it.show((packageName, rootDir, directory) -> {
            sourceField.setText(rootDir.getPath());
            packageField.setText(packageName);
            rootVirtualFile = rootDir;
        })));

        // 默认选择 Module
        Optional.ofNullable(moduleField.getSelectedItem()).map(Object::toString).ifPresent(it -> //
                GenerateDialog.this.module = getModuleByName(project, it));

        // 模板分组选择事件
        groupNameField.addActionListener(e -> {
            checkBoxList.removeIf(it -> true);
            templatePanel.removeAll();

            String groupName = (String) groupNameField.getSelectedItem();
            TemplateGroup group = instance.getState().getTemplateGroupMap().get(groupName);
            Optional.ofNullable(group).ifPresent(it -> it.getMap().values().forEach(t -> {
                JCheckBox box = new JCheckBox(t.getName());
                box.setSelected(false);
                templatePanel.add(box);
                checkBoxList.add(box);
            }));

            pack();
        });
        // 默认选中模板
        groupNameField.setSelectedItem(instance.getState().getTemplateGroupName());

        // 确认按钮点击事件
        okButton.addActionListener(e -> {
            if (rootVirtualFile != null) {
                checkBoxList.stream().filter(AbstractButton::isSelected).map(AbstractButton::getText).map(it -> {
                    final String groupName = (String) groupNameField.getSelectedItem();
                    TemplateGroup group = instance.getTemplateGroupMap().get(groupName);
                    return group == null ? null : group.get(it);
                }).filter(Objects::nonNull).forEach(template -> {   //
                    dbTableList.stream().peek(it -> it.setPackageName(packageField.getText())).forEach(it -> { //
                        generate(template.getContent(), it, rootVirtualFile, project);
                    });
                });
                // 从本地重新加载文件
                rootVirtualFile.refresh(true, true);
            }
            // 关闭窗口
            dispose();
        });

        // 取消按钮点击事件
        cancelButton.addActionListener(e -> dispose());

        //刷新和显示窗口
        setLocationRelativeTo(null);
        pack();
    }
}