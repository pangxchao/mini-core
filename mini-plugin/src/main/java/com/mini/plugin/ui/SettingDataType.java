package com.mini.plugin.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.options.Configurable;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.mini.plugin.config.DataTypeModel;
import com.mini.plugin.config.Settings;
import com.mini.plugin.state.AbstractGroup;
import com.mini.plugin.state.DataType;
import com.mini.plugin.state.DataTypeGroup;
import com.mini.plugin.state.DataTypeGroupMap;
import com.mini.plugin.ui.panel.GroupPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.intellij.openapi.ui.MessageDialogBuilder.yesNo;
import static com.mini.plugin.config.Settings.instance;
import static com.mini.plugin.util.Constants.CONFIRM_DELETE_MAPPER;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class SettingDataType extends JPanel implements Configurable {
    private final GroupPanel<DataTypeGroup, DataTypeGroupMap> groupPanel;
    private final DataTypeModel model = new DataTypeModel();
    private final JBTable jbTable = new JBTable(model);
    private final Settings state;

    private void reshow() {
        of(state.getDataTypeGroupMap()).map(it -> it.get(state.getDataTypeGroupName()))
                .map(AbstractGroup::getValues).ifPresent(model::setTableData);
    }

    public SettingDataType(Settings state) {
        this.state = state;

        this.setLayout(new BorderLayout());
        this.add(groupPanel = new GroupPanel<DataTypeGroup, DataTypeGroupMap>() {
            @Override
            protected final void selected(@NotNull DataTypeGroup item) {
                state.setDataTypeGroupName(item.getName());
                state.getDataTypeGroupMap().add(item);
                SettingDataType.this.reshow();
            }

            @Override
            protected @NotNull DataTypeGroupMap data() {
                return state.getDataTypeGroupMap();
            }

            @Override
            protected @NotNull String currentName() {
                return state.getDataTypeGroupName();
            }

            @NotNull
            @Override
            protected DataTypeGroup newInstance() {
                return new DataTypeGroup();
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
        final JBScrollPane scrollPane = new JBScrollPane();
        scrollPane.setBorder(new CustomLineBorder(1, 1, 1, 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        jbTable.setSelectionMode(SINGLE_SELECTION);
        scrollPane.setViewportView(jbTable);

        model.addChangeListener((row, column) -> of(state.getDataTypeGroupMap()).map(it -> it.get(model.getRowName(row))).ifPresent(it -> {
            it.add(new DataType(model.getValueAtString(row, 0), model.getValueAtString(row, 1))); //
        }));
        // 重新渲染数据
        this.reshow();
    }

    // 创建事件按钮
    private JComponent createActionGroupToolBar() {
        DefaultActionGroup action = new DefaultActionGroup();
        // 新增事件
        action.add(new AnAction(AllIcons.General.Add) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                ofNullable(state.getDataTypeGroupMap().get(state.getDataTypeGroupName())).ifPresent(it -> {
                    final String dbType = "DATABASE_TYPE" + it.getMap().size();
                    it.add(new DataType(dbType, null));
                    reshow();
                });
            }
        });
        // 删除事件
        action.add(new AnAction(AllIcons.General.Remove) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                if (yesNo(TITLE_INFO, CONFIRM_DELETE_MAPPER).isYes()) {
                    ofNullable(state.getDataTypeGroupMap().get(state.getDataTypeGroupName())).ifPresent(it -> {
                        int row = SettingDataType.this.jbTable.getSelectedRow();
                        if (row >= 0 && row < jbTable.getRowCount()) {
                            it.getMap().remove(model.getRowName(row));
                            reshow();
                        }
                    });
                }
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                Presentation pre = e.getPresentation();
                if (jbTable.getSelectedRow() >= 0) {
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

    public void resetToDefault() {
        groupPanel.reshow(null);
        this.reshow();
    }

    @Override
    public final JComponent createComponent() {
        return this;
    }

    @Override
    public final String getDisplayName() {
        return "DataType";
    }

    @Override
    public void apply() {
        instance.getState().resetDateType(state);
    }

    @Override
    public void reset() {
        state.resetDateType(instance.getState());
    }

    @Override
    public final boolean isModified() {
        return true;
    }
}



