package com.sjhy.plugin.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.*;
import com.intellij.openapi.ui.ex.MultiLineLabel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.intellij.util.ExceptionUtil;
import com.sjhy.plugin.config.Settings;
import com.sjhy.plugin.constants.MsgValue;
import com.sjhy.plugin.constants.StrState;
import com.sjhy.plugin.entity.*;
import com.sjhy.plugin.tool.CollectionUtil;
import com.sjhy.plugin.tool.HttpUtils;
import com.sjhy.plugin.tool.StringUtils;
import com.sjhy.plugin.ui.base.ListCheckboxPanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 * 主设置面板
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class MainSetting implements Configurable, Configurable.Composite {
	/**
	 * 主面板
	 */
	private JPanel mainPanel;
	/**
	 * 编码选择下拉框
	 */
	private JComboBox encodeComboBox;
	/**
	 * 作者编辑框
	 */
	private JTextField authorTextField;
	/**
	 * 重置默认设置按钮
	 */
	private JButton resetBtn;
	/**
	 * 模板导入按钮
	 */
	private JButton importBtn;
	/**
	 * 模板导出按钮
	 */
	private JButton exportBtn;
	/**
	 * 当前版本号
	 */
	private JLabel versionLabel;

	/**
	 * 重置列表
	 */
	private List<Configurable> resetList;

	/**
	 * 需要保存的列表
	 */
	private List<Configurable> saveList;

	/**
	 * 所有列表
	 */
	private List<Configurable> allList;

	/**
	 * 设置对象
	 */
	private Settings settings = Settings.getInstance();

	/**
	 * 默认构造方法
	 */
	public MainSetting() {
		// 获取默认项目
		Project project = ProjectManager.getInstance().getDefaultProject();
		init();

		//初始化事件
		Settings settings = Settings.getInstance();
		//重置配置信息
		resetBtn.addActionListener(e -> {
			if (MessageDialogBuilder.yesNo(MsgValue.TITLE_INFO, MsgValue.RESET_DEFAULT_SETTING_MSG).isYes()) {
				if (CollectionUtil.isEmpty(resetList)) {
					return;
				}
				// 初始化默认配置
				settings.initDefault();
				// 重置
				resetList.forEach(UnnamedConfigurable::reset);
				if (CollectionUtil.isEmpty(saveList)) {
					return;
				}
				// 保存
				saveList.forEach(configurable -> {
					try {
						configurable.apply();
					} catch (ConfigurationException e1) {
						e1.printStackTrace();
					}
				});
			}
		});

		// 模板导入事件
		importBtn.addActionListener(e -> {
			String token = Messages.showInputDialog("Token:", MsgValue.TITLE_INFO, AllIcons.General.PasswordLock, "", new InputValidator() {
				@Override
				public boolean checkInput(String inputString) {
					return !StringUtils.isEmpty(inputString);
				}

				@Override
				public boolean canClose(String inputString) {
					return this.checkInput(inputString);
				}
			});
			if (token == null) {
				return;
			}
			String result = HttpUtils.get(String.format("/template?token=%s", token));
			if (result == null) {
				return;
			}
			// 解析数据
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				JsonNode jsonNode = objectMapper.readTree(result);
				if (jsonNode == null) {
					return;
				}
				// 配置覆盖
				coverConfig(jsonNode, StrState.TYPE_MAPPER, TypeMapperGroup.class, settings.getTypeMapperGroupMap());
				coverConfig(jsonNode, StrState.TEMPLATE, TemplateGroup.class, settings.getTemplateGroupMap());
				coverConfig(jsonNode, StrState.COLUMN_CONFIG, ColumnConfigGroup.class, settings.getColumnConfigGroupMap());
				coverConfig(jsonNode, StrState.GLOBAL_CONFIG, GlobalConfigGroup.class, settings.getGlobalConfigGroupMap());
				// 重置配置
				allList.forEach(UnnamedConfigurable::reset);
				if (CollectionUtil.isEmpty(saveList)) {
					return;
				}
				// 保存
				allList.forEach(configurable -> {
					try {
						configurable.apply();
					} catch (ConfigurationException e1) {
						e1.printStackTrace();
					}
				});
				// 覆盖提示
				Messages.showInfoMessage("导入完成", MsgValue.TITLE_INFO);
			} catch (IOException e1) {
				ExceptionUtil.rethrow(e1);
			}
		});

		// 模板导出事件
		exportBtn.addActionListener(e -> {
			// 创建一行四列的主面板
			JPanel mainPanel = new JPanel(new GridLayout(1, 4));
			// Type Mapper
			ListCheckboxPanel typeMapperPanel = new ListCheckboxPanel("Type Mapper", settings.getTypeMapperGroupMap().keySet());
			mainPanel.add(typeMapperPanel);
			// Template
			ListCheckboxPanel templatePanel = new ListCheckboxPanel("Template", settings.getTemplateGroupMap().keySet());
			mainPanel.add(templatePanel);
			// Column Config
			ListCheckboxPanel columnConfigPanel = new ListCheckboxPanel("Column Config", settings.getColumnConfigGroupMap().keySet());
			mainPanel.add(columnConfigPanel);
			// GlobalConfig
			ListCheckboxPanel globalConfigPanel = new ListCheckboxPanel("Global Config", settings.getGlobalConfigGroupMap().keySet());
			mainPanel.add(globalConfigPanel);
			// 构建dialog
			DialogBuilder dialogBuilder = new DialogBuilder(project);
			dialogBuilder.setTitle(MsgValue.TITLE_INFO);
			dialogBuilder.setNorthPanel(new MultiLineLabel("请选择要导出的配置分组："));
			dialogBuilder.setCenterPanel(mainPanel);
			dialogBuilder.addActionDescriptor(dialogWrapper -> new AbstractAction("OK") {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!MainSetting.this.isSelected(typeMapperPanel, templatePanel, columnConfigPanel, globalConfigPanel)) {
						Messages.showWarningDialog("至少选择一个模板组！", MsgValue.TITLE_INFO);
						return;
					}
					// 打包数据
					Map<String, Object> param = new HashMap<>(4);

					Map<String, TypeMapperGroup> typeMapper = new LinkedHashMap<>();
					for (String selectedItem : typeMapperPanel.getSelectedItems()) {
						typeMapper.put(selectedItem, settings.getTypeMapperGroupMap().get(selectedItem));
					}
					param.put(StrState.TYPE_MAPPER, typeMapper);

					Map<String, TemplateGroup> template = new LinkedHashMap<>();
					for (String selectedItem : templatePanel.getSelectedItems()) {
						template.put(selectedItem, settings.getTemplateGroupMap().get(selectedItem));
					}
					param.put(StrState.TEMPLATE, template);

					Map<String, ColumnConfigGroup> columnConfig = new LinkedHashMap<>();
					for (String selectedItem : columnConfigPanel.getSelectedItems()) {
						columnConfig.put(selectedItem, settings.getColumnConfigGroupMap().get(selectedItem));
					}
					param.put(StrState.COLUMN_CONFIG, columnConfig);

					Map<String, GlobalConfigGroup> globalConfig = new LinkedHashMap<>();
					for (String selectedItem : globalConfigPanel.getSelectedItems()) {
						globalConfig.put(selectedItem, settings.getGlobalConfigGroupMap().get(selectedItem));
					}
					param.put(StrState.GLOBAL_CONFIG, globalConfig);
					// 上传数据
					String result = HttpUtils.postJson("/template", param);
					// 关闭并退出
					dialogWrapper.close(DialogWrapper.OK_EXIT_CODE);
					if (result != null) {
						// 提取token
						String token = "error";
						if (result.contains("token")) {
							int startLocation = result.indexOf("token") + 6;
							token = result.substring(startLocation, result.indexOf("，", startLocation));
						}
						// 显示token
						Messages.showInputDialog(project, result, MsgValue.TITLE_INFO, AllIcons.General.InformationDialog, token,
							new NonEmptyInputValidator());
					}
				}
			});
			dialogBuilder.show();
		});
	}

	/**
	 * 判断是否选中
	 * @param checkboxPanels 复选框面板
	 * @return 是否选中
	 */
	private boolean isSelected(@NotNull ListCheckboxPanel... checkboxPanels) {
		for (ListCheckboxPanel checkboxPanel : checkboxPanels) {
			if (!CollectionUtil.isEmpty(checkboxPanel.getSelectedItems())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 覆盖配置
	 * @param jsonNode json节点对象
	 * @param name     配置组名称
	 * @param cls      配置组类
	 * @param srcGroup 源分组
	 */
	private <T extends AbstractGroup> void coverConfig(@NotNull JsonNode jsonNode, @NotNull String name, Class<T> cls, @NotNull Map<String, T> srcGroup) {
		ObjectMapper objectMapper = new ObjectMapper();
		if (!jsonNode.has(name)) {
			return;
		}
		try {
			JsonNode node = jsonNode.get(name);
			if (node.size() == 0) {
				return;
			}
			// 覆盖配置
			Iterator<String> names = node.fieldNames();
			while (names.hasNext()) {
				String key = names.next();
				String value = node.get(key).toString();
				T group = objectMapper.readValue(value, cls);
				if (srcGroup.containsKey(key)) {
					if (!MessageDialogBuilder.yesNo(MsgValue.TITLE_INFO, String.format("是否覆盖%s配置中的%s分组？", name, key)).isYes()) {
						continue;
					}
				}
				srcGroup.put(key, group);
			}
		} catch (IOException e) {
			Messages.showWarningDialog("JSON解析错误！", MsgValue.TITLE_INFO);
			ExceptionUtil.rethrow(e);
		}
	}

	/**
	 * 初始化方法
	 */
	private void init() {
		//初始化数据
		versionLabel.setText(settings.getVersion());
		authorTextField.setText(settings.getAuthor());
		encodeComboBox.setSelectedItem(settings.getEncode());
	}

	/**
	 * 设置显示名称
	 * @return 显示名称
	 */
	@Nls
	@Override
	public String getDisplayName() {
		return "Easy Code";
	}

	/**
	 * 更多配置
	 * @return 配置选项
	 */
	@NotNull
	@Override
	public Configurable[] getConfigurables() {
		Configurable[] result = new Configurable[4];
		result[0] = new TypeMapperSetting(settings);
		result[1] = new TemplateSettingPanel();
		result[2] = new TableSettingPanel();
		result[3] = new GlobalConfigSettingPanel();
		// 所有列表
		allList = new ArrayList<>();
		allList.add(result[0]);
		allList.add(result[1]);
		allList.add(result[2]);
		allList.add(result[3]);
		// 需要重置的列表
		resetList = new ArrayList<>();
		resetList.add(result[0]);
		resetList.add(result[1]);
		resetList.add(result[3]);
		// 不需要重置的列表
		saveList = new ArrayList<>();
		saveList.add(this);
		saveList.add(result[2]);
		return result;
	}

	/**
	 * 获取主面板信息
	 * @return 主面板
	 */
	@Nullable
	@Override
	public JComponent createComponent() {
		return mainPanel;
	}

	/**
	 * 判断是否修改
	 * @return 是否修改
	 */
	@Override
	public boolean isModified() {
		return !settings.getEncode().equals(encodeComboBox.getSelectedItem()) || !settings.getAuthor().equals(authorTextField.getText());
	}

	/**
	 * 应用修改
	 */
	@Override
	public void apply() {
		//保存数据
		settings.setAuthor(authorTextField.getText());
		settings.setEncode((String) encodeComboBox.getSelectedItem());
	}

	/**
	 * 重置
	 */
	@Override
	public void reset() {
		init();
	}

	{
		// GUI initializer generated by IntelliJ IDEA GUI Designer
		// >>> IMPORTANT!! <<<
		// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR call it in your code!
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
		final JLabel label1 = new JLabel();
		this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("string").getString("label.default.encode"));
		mainPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED,
			GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label2 = new JLabel();
		this.$$$loadLabelText$$$(label2, ResourceBundle.getBundle("string").getString("label.author.name"));
		mainPanel.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED,
			GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		encodeComboBox = new JComboBox();
		final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
		defaultComboBoxModel1.addElement("UTF-8");
		defaultComboBoxModel1.addElement("GBK");
		defaultComboBoxModel1.addElement("GB2312");
		defaultComboBoxModel1.addElement("UNICODE");
		defaultComboBoxModel1.addElement("ANSI");
		defaultComboBoxModel1.addElement("ASCII");
		defaultComboBoxModel1.addElement("ISO-8859-1");
		encodeComboBox.setModel(defaultComboBoxModel1);
		mainPanel.add(encodeComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
			GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		authorTextField = new JTextField();
		authorTextField.setText("");
		mainPanel.add(authorTextField, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
			GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		resetBtn = new JButton();
		this.$$$loadButtonText$$$(resetBtn, ResourceBundle.getBundle("string").getString("button.reset"));
		mainPanel.add(resetBtn, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
			GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer1 = new Spacer();
		mainPanel.add(spacer1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
			GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
		mainPanel.add(panel1, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
			GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
			GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		importBtn = new JButton();
		importBtn.setText("导入模板");
		panel1.add(importBtn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
			GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer2 = new Spacer();
		panel1.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
			GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
		final Spacer spacer3 = new Spacer();
		panel1.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1,
			GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		exportBtn = new JButton();
		exportBtn.setText("导出模板");
		panel1.add(exportBtn, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
			GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label3 = new JLabel();
		this.$$$loadLabelText$$$(label3, ResourceBundle.getBundle("string").getString("label.version"));
		mainPanel.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED,
			GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		versionLabel = new JLabel();
		versionLabel.setText("");
		mainPanel.add(versionLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED,
			GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		label1.setLabelFor(encodeComboBox);
		label2.setLabelFor(authorTextField);
	}

	/** @noinspection ALL */
	private void $$$loadLabelText$$$(JLabel component, String text) {
		StringBuffer result = new StringBuffer();
		boolean haveMnemonic = false;
		char mnemonic = '\0';
		int mnemonicIndex = -1;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '&') {
				i++;
				if (i == text.length()) break;
				if (!haveMnemonic && text.charAt(i) != '&') {
					haveMnemonic  = true;
					mnemonic      = text.charAt(i);
					mnemonicIndex = result.length();
				}
			}
			result.append(text.charAt(i));
		}
		component.setText(result.toString());
		if (haveMnemonic) {
			component.setDisplayedMnemonic(mnemonic);
			component.setDisplayedMnemonicIndex(mnemonicIndex);
		}
	}

	/** @noinspection ALL */
	private void $$$loadButtonText$$$(AbstractButton component, String text) {
		StringBuffer result = new StringBuffer();
		boolean haveMnemonic = false;
		char mnemonic = '\0';
		int mnemonicIndex = -1;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '&') {
				i++;
				if (i == text.length()) break;
				if (!haveMnemonic && text.charAt(i) != '&') {
					haveMnemonic  = true;
					mnemonic      = text.charAt(i);
					mnemonicIndex = result.length();
				}
			}
			result.append(text.charAt(i));
		}
		component.setText(result.toString());
		if (haveMnemonic) {
			component.setMnemonic(mnemonic);
			component.setDisplayedMnemonicIndex(mnemonicIndex);
		}
	}

	/** @noinspection ALL */
	public JComponent $$$getRootComponent$$$() { return mainPanel; }
}
