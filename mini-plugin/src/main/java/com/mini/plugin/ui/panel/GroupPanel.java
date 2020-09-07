package com.mini.plugin.ui.panel;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.state.AbstractData;
import com.mini.plugin.state.AbstractMap;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Optional;

import static com.intellij.openapi.ui.MessageDialogBuilder.yesNo;
import static com.mini.plugin.util.Constants.CONFIRM_DELETE_GROUP;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.MessageUtil.showInput;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public abstract class GroupPanel<G extends AbstractData<G>, M extends AbstractMap<G>> extends JPanel implements EventListener {
    private final CollectionComboBoxModel<String> model = new CollectionComboBoxModel<>();
    private final ComboBox<String> comboBox = new ComboBox<>(model);

    protected abstract void selected(@NotNull G item);

    @NotNull
    protected abstract String currentName();

    @NotNull
    protected abstract G newInstance();

    @NotNull
    protected abstract M data();

    @NotNull
    private List<String> nameList() {
        return new ArrayList<>(data().getMap().keySet());
    }

    @Nullable
    private G getItem() {
        return data().get(currentName());
    }

    public void reshow(@Nullable String name) {
        model.removeAll();
        model.add(nameList());
        model.update();

        if (isNotBlank(name)) {
            comboBox.setSelectedItem(name);
        } else {
            comboBox.setSelectedIndex(0);
        }
    }

    public GroupPanel() {
        super(new FlowLayout(FlowLayout.LEFT));
        // 添加标签和下拉框
        this.add(new Label("Group Name"));
        this.add(comboBox);

        // 创建事件按钮并在面板上添加按钮工具
        this.add(createActionGroupToolBar());
        this.setPreferredSize(JBUI.size(600, 40));

        // 下拉框架选中事件
        comboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                final String name = this.model.getSelected();
                ofNullable(data().get(name)).ifPresent(this::selected);
            }
        });
        reshow(currentName());
    }

    // 创建事件按钮
    private JComponent createActionGroupToolBar() {
        DefaultActionGroup action = new DefaultActionGroup();
        // 复制
        action.add(new AnAction(AllIcons.Actions.Copy) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                String name = (String) comboBox.getSelectedItem();
                Optional.ofNullable(getItem()).ifPresent(it -> {
                    String newName = showInput("Group Name", name + "Copy", s -> {
                        if (StringUtils.isBlank(s)) return false;
                        return !nameList().contains(s);
                    });
                    final G newInstance = it.copy();
                    newInstance.setName(newName);
                    data().add(newInstance);
                    reshow(newName);
                });
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                final int it = comboBox.getSelectedIndex();
                e.getPresentation().setEnabled(it >= 0);
            }
        });
        // 修改名称
        action.add(new AnAction(AllIcons.Actions.Edit) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                String name = (String) comboBox.getSelectedItem();
                Optional.ofNullable(getItem()).ifPresent(it -> {
                    String newName = showInput("Group Name", name + "", s -> {
                        if (StringUtils.isBlank(s)) return false;
                        return !nameList().contains(s);
                    });
                    it.setName(newName);
                    data().put(newName, it);
                    data().remove(name);
                    reshow(newName);
                });
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                final int it = comboBox.getSelectedIndex();
                e.getPresentation().setEnabled(it >= 0);
            }
        });
        // 新增事件
        action.add(new AnAction(AllIcons.General.Add) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                String newName = showInput("Group Name", "", s -> {
                    if (StringUtils.isBlank(s)) return false;
                    return !nameList().contains(s);
                });
                G newInstance = newInstance();
                newInstance.setName(newName);
                data().put(newName, newInstance);
                reshow(newName);
            }
        });
        // 删除事件
        action.add(new AnAction(AllIcons.General.Remove) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                String name = (String) comboBox.getSelectedItem();
                if (yesNo(TITLE_INFO, format(CONFIRM_DELETE_GROUP, name)).isYes()) {
                    data().remove(name);
                    reshow(null);
                }
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                final int it = comboBox.getSelectedIndex();
                e.getPresentation().setEnabled(it >= 0);
            }
        });
        String title = "Group Toolbar";
        ActionManager manager = ActionManager.getInstance();
        return manager.createActionToolbar(title, action, true).getComponent();
    }


}