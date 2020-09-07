package com.mini.plugin.ui.panel;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.state.Template;
import com.mini.plugin.state.TemplateGroup;
import com.mini.plugin.util.EditorUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.EventListener;

import static com.intellij.openapi.ui.MessageDialogBuilder.yesNo;
import static com.mini.plugin.util.Constants.CONFIRM_DELETE_GROUP;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.MessageUtil.showInput;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;


public abstract class TemplatePanel extends JPanel implements EventListener {
    private final CollectionComboBoxModel<String> jbListModel = new CollectionComboBoxModel<>();
    private final Project project = ProjectManager.getInstance().getDefaultProject();
    private final JBList<String> jbList = new JBList<>(jbListModel);
    private final Editor editor;

    @Nullable
    public abstract TemplateGroup getTemplateGroup();

    @Nullable
    private Template getItem(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (getTemplateGroup() == null) {
            return null;
        }
        for (Template t : getTemplateGroup().getValues()) {
            if (name.equals(t.getName())) {
                return t;
            }
        }
        return null;
    }

    //
    public final void reshow(String name) {
        jbListModel.removeAll();
        if (getTemplateGroup() != null) {
            for (Template t : getTemplateGroup().getValues()) {
                jbListModel.add(t.getName());
            }
        }
        jbListModel.update();

        // 设置选中项
        if (StringUtils.isNotBlank(name)) {
            jbList.setSelectedValue(name, false);
        } else jbList.setSelectedIndex(0);
    }

    public TemplatePanel() {
        this.setLayout(new BorderLayout());
        this.editor = EditorUtil.createEditor(project);
        this.setPreferredSize(JBUI.size(400, 300));

        // 创建页面分割比例
        Splitter splitter = new Splitter(false, 0.2f);
        this.add(splitter, BorderLayout.CENTER);

        // 创建左侧面板并添加到分割器中
        JPanel leftPanel = new JPanel(new BorderLayout());
        splitter.setFirstComponent(leftPanel);

        // 创建工具按钮
        JComponent headToolBar = createActionGroupToolBar();
        headToolBar.setBorder(new CustomLineBorder(1, 1, 1, 1));
        leftPanel.add(headToolBar, BorderLayout.NORTH);

        // 将表格添加到左侧面板，并设置表格边框和单选
        jbList.setBorder(new CustomLineBorder(0, 1, 1, 1));
        leftPanel.add(jbList, BorderLayout.CENTER);
        jbList.setSelectionMode(SINGLE_SELECTION);

        // 创建右侧面板-模板内容
        JPanel rightPanel = new JPanel(new BorderLayout());
        splitter.setSecondComponent(rightPanel);

        // 将编辑器对象添加到右侧面板
        rightPanel.add(editor.getComponent(), BorderLayout.CENTER);
        // 表格列表选中事件
        jbList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            ofNullable(getItem(jbList.getSelectedValue())).map(Template::getContent).map(it -> it.replace("\r", "")).ifPresent(it ->
                    WriteCommandAction.runWriteCommandAction(project, () -> {
                        editor.getDocument().setReadOnly(false);
                        editor.getDocument().setText(it);
                    }));
        });
        // 设置编辑回调
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                final String name = defaultIfBlank(jbList.getSelectedValue(), "");
                String text = defaultIfBlank(editor.getDocument().getText(), "");
                ofNullable(getItem(name)).ifPresent(it -> it.setContent(text));
            }
        });
        // 显示数据
        this.reshow(null);
    }

    // 创建编辑按钮
    @SuppressWarnings("DuplicatedCode")
    private JComponent createActionGroupToolBar() {
        DefaultActionGroup action = new DefaultActionGroup();
        // 复制事件
        action.add(new AnAction(AllIcons.Actions.Copy) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                ofNullable(getItem(jbList.getSelectedValue())).ifPresent(t -> {
                    String newName = showInput("Template Name", t.getName() + "Copy", (s) -> {
                        if (StringUtils.isBlank(s)) {
                            return false;
                        }
                        return getItem(s) == null;
                    });
                    if (getTemplateGroup() != null) {
                        Template newT = t.copy();
                        newT.setName(newName);
                        getTemplateGroup().add(newT);
                    }
                    reshow(newName);
                });
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                final int it = jbList.getSelectedIndex();
                e.getPresentation().setEnabled(it >= 0);
            }
        });

        // 修改名称
        action.add(new AnAction(AllIcons.Actions.Edit) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                ofNullable(getItem(jbList.getSelectedValue())).ifPresent(t -> {
                    String newName = showInput("Template Name", t.getName(), (s) -> {
                        if (StringUtils.isBlank(s)) {
                            return false;
                        }
                        return getItem(s) == null;
                    });
                    t.setName(newName);
                    reshow(newName);
                });
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                final int it = jbList.getSelectedIndex();
                e.getPresentation().setEnabled(it >= 0);
            }
        });

        // 新增事件
        action.add(new AnAction(AllIcons.General.Add) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                String newName = showInput("Template Name", "", (s) -> {
                    if (StringUtils.isBlank(s)) {
                        return false;
                    }
                    return getItem(s) == null;
                });
                if (getTemplateGroup() != null) {
                    getTemplateGroup().add(new Template(newName));
                }
                reshow(newName);
            }
        });

        // 删除事件
        action.add(new AnAction(AllIcons.General.Remove) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                ofNullable(getItem(jbList.getSelectedValue())).ifPresent(t -> {
                    if (yesNo(TITLE_INFO, format(CONFIRM_DELETE_GROUP, t)).isYes()) {
                        if (getTemplateGroup() != null) {
                            getTemplateGroup().getMap().remove(t.getName());
                        }
                        reshow(null);
                    }
                });
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                final int it = jbList.getSelectedIndex();
                e.getPresentation().setEnabled(it >= 0);
            }
        });
        String title = "Head Toolbar";
        ActionManager m = ActionManager.getInstance();
        return m.createActionToolbar(title, action, true).getComponent();
    }
}