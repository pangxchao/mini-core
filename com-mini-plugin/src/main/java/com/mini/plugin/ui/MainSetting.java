package com.mini.plugin.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.config.Settings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.intellij.openapi.project.ProjectManager.getInstance;
import static com.intellij.openapi.ui.MessageDialogBuilder.yesNo;
import static com.intellij.uiDesigner.core.GridConstraints.*;
import static com.mini.plugin.util.Constants.RESET_DEFAULT_SETTING;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static org.jetbrains.annotations.Nls.Capitalization.Title;

public class MainSetting extends JPanel implements Configurable, Configurable.Composite {
	private final Settings settings = Settings.getInstance();
	private final ComboBox<String> encodingBox;
	private final MapperSetting mapperSetting;
	private final CodeSetting codeSetting;
	private final JTextField authorField;
	private final DBSetting dbSetting;
	
	public MainSetting() {
		// 创建主面板并设置度面板布局
		this.setLayout(new GridLayoutManager(3, 2, JBUI.emptyInsets(), -1, -1));
		
		// 创建编码标签，并添加到布局中
		JLabel encodingLabel = new JLabel("Encoding");
		this.add(encodingLabel, new GridConstraints(0, 0, 1, 1, ANCHOR_WEST, FILL_NONE,
			SIZEPOLICY_FIXED, SIZEPOLICY_FIXED, null, null, null, 0, false));
		
		// 创建编码下拉框、设置下拉数据并添加到顶部布局中
		this.encodingBox = new ComboBox<>(new String[]{"UTF-8", "GBK", "GB2312", "UNICODE", "ANSI", "ASCII", "ISO-8859-1"});
		this.add(encodingBox, new GridConstraints(0, 1, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_CAN_GROW,
			SIZEPOLICY_FIXED, null, null, null, 0, false));
		
		// 创建作者标签，并添加到顶部布局中
		JLabel authorLabel = new JLabel("Author");
		this.add(authorLabel, new GridConstraints(1, 0, 1, 1, ANCHOR_WEST, FILL_NONE, SIZEPOLICY_FIXED,
			SIZEPOLICY_FIXED, null, null, null, 0, false));
		
		// 创建作者输入框并添加到顶部而已中
		this.authorField = new JTextField("xchao");
		this.add(authorField, new GridConstraints(1, 1, 1, 1, ANCHOR_WEST, FILL_NONE,
			SIZEPOLICY_WANT_GROW, SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		
		// 创建一个空白占们符并添加到顶部布局
		this.add(new Spacer(), new GridConstraints(2, 0, 1, 1, ANCHOR_CENTER, FILL_VERTICAL, 1,
			SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		
		// 创建重置按钮并添加到顶部布局容器
		JButton resetToDefaultButton = new JButton("Reset To Default");
		this.add(resetToDefaultButton, new GridConstraints(2, 1, 1, 1, ANCHOR_NORTHWEST, FILL_NONE,
			SIZEPOLICY_CAN_SHRINK | SIZEPOLICY_CAN_GROW, SIZEPOLICY_FIXED,
			null, null, null, 0, false));
		
		// 创建 Mapper Setting、DB Setting、Code Setting
		Project project = getInstance().getDefaultProject();
		mapperSetting = new MapperSetting(settings, project);
		codeSetting   = new CodeSetting(settings, project);
		dbSetting     = new DBSetting(settings, project);
		
		// 重置按钮点击事件
		resetToDefaultButton.addActionListener(actionListenerEvent -> {
			if (yesNo(TITLE_INFO, RESET_DEFAULT_SETTING).isYes()) {
				this.settings.resetToDefault();
				MainSetting.this.reset(settings);
			}
		});
		
		// 初始化所有数据
		MainSetting.this.init_data();
	}
	
	// 初始化作者和编码
	private synchronized void init_data() {
		encodingBox.setSelectedItem(settings.getEncoding());
		authorField.setText(settings.getAuthor());
	}
	
	// 重置操作
	public synchronized final void reset(Settings settings) {
		encodingBox.setSelectedItem(settings.getEncoding());
		authorField.setText(settings.getAuthor());
		mapperSetting.reset(settings);
		codeSetting.reset(settings);
		dbSetting.reset(settings);
	}
	
	// 重置操作
	@Override
	public synchronized final void reset() {
		encodingBox.setSelectedItem(settings.getEncoding());
		authorField.setText(settings.getAuthor());
		mapperSetting.reset();
		codeSetting.reset();
		dbSetting.reset();
	}
	
	// 设置数据是否有修改过
	@Override
	public synchronized final boolean isModified() {
		return !Objects.equals(settings.getAuthor(), authorField.getText())
			|| !StringUtil.equals(settings.getEncoding(), (String) encodingBox.getSelectedItem())
			|| mapperSetting.isModified()
			|| codeSetting.isModified()
			|| dbSetting.isModified();
	}
	
	// 点击应用按钮保存设置
	@Override
	public synchronized final void apply() {
		Object val = encodingBox.getSelectedItem();
		settings.setAuthor(authorField.getText());
		settings.setEncoding((String) val);
		mapperSetting.apply();
		codeSetting.apply();
		dbSetting.apply();
	}
	
	// 获取主面板信息
	@Override
	public final JComponent createComponent() {
		return this;
	}
	
	@NotNull
	@Override
	public final Configurable[] getConfigurables() {
		return new Configurable[]{
			mapperSetting,
			dbSetting,
			codeSetting
		};
	}
	
	// 返回设置菜单名称
	@Override
	@Nls(capitalization = Title)
	public final String getDisplayName() {
		return "Mini-Code";
	}
	
	@Override
	public final void disposeUIResources() {
		mapperSetting.disposeUIResources();
		codeSetting.disposeUIResources();
		dbSetting.disposeUIResources();
	}
}
