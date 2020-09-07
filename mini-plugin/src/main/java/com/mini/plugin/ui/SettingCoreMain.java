package com.mini.plugin.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.config.Settings;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import static com.intellij.openapi.ui.MessageDialogBuilder.yesNo;
import static com.intellij.uiDesigner.core.GridConstraints.*;
import static com.mini.plugin.config.Settings.instance;
import static com.mini.plugin.util.Constants.RESET_DEFAULT_SETTING;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static java.util.Optional.ofNullable;

public class SettingCoreMain extends JPanel implements Configurable, Configurable.Composite {
    private final Settings state = instance.getState().copy();
    private final SettingDataType settingDataType;
    private final SettingTemplate settingTemplate;
    private final ComboBox<String> encodingBox;
    private final JTextField authorField;

    public SettingCoreMain() {
        super(new GridLayoutManager(3, 2, JBUI.emptyInsets(), -1, -1));
        // 创建编码标签，并添加到布局中
        final JLabel encodingLabel = new JLabel("Encoding");
        this.add(encodingLabel, new GridConstraints(0, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));

        // 创建编码下拉框、设置下拉数据并添加到顶部布局中
        encodingBox = new ComboBox<>(new String[]{"UTF-8", "GBK", "GB2312", "UNICODE", "ANSI", "ASCII", "ISO-8859-1"});
        this.add(encodingBox, new GridConstraints(0, 1, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_CAN_GROW, SIZEPOLICY_FIXED, null, null, null, 0, false));
        encodingBox.addItemListener(event -> ofNullable(encodingBox.getSelectedItem()).map(Object::toString).ifPresent(state::setEncoding));

        // 创建作者标签，并添加到顶部布局中
        final JLabel authorLabel = new JLabel("Author");
        this.add(authorLabel, new GridConstraints(1, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));

        // 创建作者输入框并添加到顶部而已中
        authorField = new JTextField("xchao");
        this.add(authorField, new GridConstraints(1, 1, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        authorField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                state.setAuthor(authorField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                state.setAuthor(authorField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                state.setAuthor(authorField.getText());
            }
        });

        // 创建一个空白占们符并添加到顶部布局
        this.add(new Spacer(), new GridConstraints(2, 0, 1, 1, ANCHOR_CENTER, FILL_VERTICAL, 1, SIZEPOLICY_WANT_GROW, null, null, null, 0, false));

        // 创建重置按钮并添加到顶部布局容器
        final JButton resetToDefaultButton = new JButton("Reset To Default");
        this.add(resetToDefaultButton, new GridConstraints(2, 1, 1, 1, ANCHOR_NORTHWEST, FILL_NONE, SIZEPOLICY_CAN_SHRINK | SIZEPOLICY_CAN_GROW, SIZEPOLICY_FIXED, null, null, null, 0, false));

        settingDataType = new SettingDataType(state);
        settingTemplate = new SettingTemplate(state);

        // 重置按钮点击事件
        resetToDefaultButton.addActionListener(event -> {
            if (yesNo(TITLE_INFO, RESET_DEFAULT_SETTING).isYes()) {
                SettingCoreMain.this.state.reset(new Settings());
                settingDataType.resetToDefault();
                settingTemplate.resetToDefault();
                reshow();
            }
        });
        // 初始化所有数据
        reshow();
    }

    // 初始化作者和编码
    private void reshow() {
        encodingBox.setSelectedItem(state.getEncoding());
        authorField.setText(state.getAuthor());
    }

    @Override
    public final String getDisplayName() {
        return "Mini-Code";
    }

    @NotNull
    @Override
    public Configurable[] getConfigurables() {
        return new Configurable[]{
                settingDataType,
                settingTemplate
        };
    }

    @NotNull
    @Override
    public JComponent createComponent() {
        return this;
    }

    @Override
    public boolean isModified() {
        return true;
    }


    @Override
    public void reset() {
        state.resetEncodingAndAuthor(instance.getState());
    }

    @Override
    public void apply() {
        instance.getState().resetEncodingAndAuthor(state);
    }
}