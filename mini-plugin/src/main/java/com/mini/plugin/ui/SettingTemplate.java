package com.mini.plugin.ui;

import com.intellij.openapi.options.Configurable;
import com.mini.plugin.config.Settings;
import com.mini.plugin.state.Template;
import com.mini.plugin.state.TemplateGroup;
import com.mini.plugin.state.TemplateGroupMap;
import com.mini.plugin.ui.panel.GroupPanel;
import com.mini.plugin.ui.panel.TemplatePanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import static com.mini.plugin.config.Settings.instance;

public class SettingTemplate extends JPanel implements Configurable {
    private final GroupPanel<TemplateGroup, TemplateGroupMap> groupPanel;
    private final TemplatePanel templatePanel;
    private final Settings state;

    private void reshow() {
        templatePanel.reshow(null);
    }

    public SettingTemplate(Settings state) {
        this.state = state;

        this.setLayout(new BorderLayout(0, 0));
        // 创建模板面板添加添加到主面板下面-南面
        this.templatePanel = new TemplatePanel() {
            @Nullable
            @Override
            public TemplateGroup getTemplateGroup() {
                final String name = state.getTemplateGroupName();
                return state.getTemplateGroupMap().get(name);
            }
        };
        this.add(templatePanel, BorderLayout.CENTER);

        // 添加分组组件到布局的上面（北面）
        this.add(groupPanel = new GroupPanel<TemplateGroup, TemplateGroupMap>() {
            @Override
            protected final void selected(@NotNull TemplateGroup item) {
                state.setTemplateGroupName(item.getName());
                state.getTemplateGroupMap().add(item);
                SettingTemplate.this.reshow();
            }

            @Override
            protected @NotNull TemplateGroupMap data() {
                return state.getTemplateGroupMap();
            }

            @Override
            protected @NotNull String currentName() {
                return state.getTemplateGroupName();
            }

            @Override
            protected @NotNull TemplateGroup newInstance() {
                return new TemplateGroup();
            }
        }, BorderLayout.NORTH);
        this.reshow();
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
        return "Template";
    }

    @Override
    public void apply() {
        instance.getState().resetTemplate(state);
    }

    @Override
    public void reset() {
        state.resetTemplate(instance.getState());
    }

    @Override
    public boolean isModified() {
        return true;
    }
}