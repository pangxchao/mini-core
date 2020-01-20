package com.mini.plugin.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Splitter;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.JBUI;
import com.mini.plugin.config.Template;
import com.mini.plugin.util.EditorUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.EventListener;
import java.util.List;

import static com.intellij.openapi.ui.Messages.showInputDialog;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static java.util.Optional.ofNullable;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

/**
 * 模板选择面板
 * @author xchao
 */
public abstract class BaseTemplatePanel extends JPanel implements Serializable, EventListener {
	private final CollectionComboBoxModel<String> jbListModel;
	private final JBList<String> jbList;
	
	// 输入模板名称
	private synchronized String inputItemName(String initValue) {
		InputValidator inputValidator = new InputValidator() {
			public final boolean checkInput(String text) {
				return StringUtil.isNotEmpty(text) && //
					getTemplate(text) == null;
			}
			
			@Override
			public boolean canClose(String inputString) {
				return this.checkInput(inputString);
			}
		};
		return showInputDialog("Template Name", TITLE_INFO, //
			null, initValue, inputValidator);
	}
	
	protected synchronized void resetModelData(String name) {
		BaseTemplatePanel.this.jbListModel.removeAll();
		this.jbListModel.add(getTemplateData());
		this.jbListModel.update();
		
		// 设置选中项
		if (StringUtil.isNotEmpty(name)) {
			jbList.setSelectedValue(name, false);
		} else jbList.setSelectedIndex(0);
	}
	
	protected BaseTemplatePanel(Project project) {
		super(new BorderLayout());
		
		// 创建左侧和右侧的面板、设置分割并添加到主面板
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel rightPanel = new JPanel(new BorderLayout());
		Splitter splitter = new Splitter(false, 0.2F);
		splitter.setFirstComponent(leftPanel);
		splitter.setSecondComponent(rightPanel);
		this.add(splitter, BorderLayout.CENTER);
		setPreferredSize(JBUI.size(400, 300));
		
		// 创建编辑按钮并添加到左侧面板上面
		JComponent headToolBar = createActionGroupToolBar();
		headToolBar.setBorder(new CustomLineBorder(1, 1, 1, 1));
		leftPanel.add(headToolBar, BorderLayout.NORTH);
		
		// 创建左侧列表面板、添加到左侧面板中心并设置数据
		this.jbListModel = new CollectionComboBoxModel<>();
		this.jbList      = new JBList<>(this.jbListModel);
		jbList.setBorder(new CustomLineBorder(0, 1, 1, 1));
		leftPanel.add(this.jbList, BorderLayout.CENTER);
		jbList.setSelectionMode(SINGLE_SELECTION);
		
		// 创建编辑器对象并添加到右侧面板
		Editor editor = EditorUtil.createEditor(project);
		rightPanel.add(editor.getComponent(), BorderLayout.CENTER);
		
		// 表格列表选中事件
		BaseTemplatePanel.this.jbList.addListSelectionListener(event -> {
			if (event.getValueIsAdjusting()) return;
			Template t = ofNullable(jbList.getSelectedValue()).map(this::getTemplate)
				.orElse(Template.builder().name("newFile").code("").build());
			EditorHighlighterFactory f = EditorHighlighterFactory.getInstance();
			LightVirtualFile file = new LightVirtualFile(t.getName() + ".vm");
			EditorHighlighter h = f.createEditorHighlighter(project, file);
			((EditorEx) editor).setHighlighter(h);
			// 重置文本内容
			editor.getDocument().setReadOnly(false);
			WriteCommandAction.runWriteCommandAction(project, () -> {
				editor.getDocument().setText(t.getCode() + ""); //
			});
		});
		
		// 编辑器内容修改回调事件
		editor.getDocument().addDocumentListener(new DocumentListener() {
			public void documentChanged(@NotNull DocumentEvent event) {
				String text = editor.getDocument().getText();
				String name = jbList.getSelectedValue();
				modifiedHandler(name, text);
			}
		});
		
		// 默认选中第一个
		// this.resetModelData(null);
		if (this.jbListModel.getSize() > 0) {
			jbList.setSelectedIndex(0);
		}
	}
	
	// 创建编辑按钮
	private JComponent createActionGroupToolBar() {
		DefaultActionGroup action = new DefaultActionGroup();
		action.add(new AnAction(AllIcons.Actions.Copy) { // 复制事件
			public void actionPerformed(@NotNull AnActionEvent event) {
				if (jbList.getSelectedIndex() < 0) return;
				String name = jbList.getSelectedValue();
				String newName = inputItemName(name + "Copy");
				copyHandler(name, newName);
				resetModelData(newName);
			}
			
			public void update(@NotNull AnActionEvent event) {
				Presentation pre = event.getPresentation();
				if (jbList.getSelectedIndex() >= 0) {
					pre.setEnabled(true);
					return;
				}
				pre.setEnabled(false);
			}
		});
		action.add(new AnAction(AllIcons.Actions.Edit) { // 修改名称
			public void actionPerformed(@NotNull AnActionEvent event) {
				if (jbList.getSelectedIndex() < 0) return;
				String name = jbList.getSelectedValue();
				String newName = inputItemName(name + "Copy");
				renameHandler(name, newName);
				resetModelData(newName);
			}
			
			public final void update(@NotNull AnActionEvent event) {
				Presentation pre = event.getPresentation();
				if (jbList.getSelectedIndex() >= 0) {
					pre.setEnabled(true);
					return;
				}
				pre.setEnabled(false);
			}
		});
		action.add(new AnAction(AllIcons.General.Add) { // 新增事件
			public void actionPerformed(@NotNull AnActionEvent event) {
				String name = inputItemName("");
				addHandler(name);
				resetModelData(name);
			}
		});
		
		action.add(new AnAction(AllIcons.General.Remove) { // 删除事件
			public void actionPerformed(@NotNull AnActionEvent event) {
				if (jbList.getSelectedIndex() < 0) return;
				String name = jbList.getSelectedValue();
				deleteHandler(name);
				resetModelData(null);
			}
			
			@Override
			public void update(@NotNull AnActionEvent event) {
				Presentation pre = event.getPresentation();
				if (jbList.getSelectedIndex() >= 0) {
					pre.setEnabled(true);
					return;
				}
				pre.setEnabled(false);
			}
		});
		// 上移事件
		action.add(new AnAction(AllIcons.Actions.MoveUp) {
			@Override
			public void actionPerformed(@NotNull AnActionEvent event) {
				int index = jbList.getSelectedIndex();
				int length = jbListModel.getSize();
				if (length <= 1 || index <= 0) {
					return;
				}
				String name = jbList.getSelectedValue();
				moveUpHandler(name, index);
				resetModelData(name);
			}
			
			@Override
			public void update(@NotNull AnActionEvent event) {
				Presentation pre = event.getPresentation();
				int index = jbList.getSelectedIndex();
				int length = jbListModel.getSize();
				if (length <= 1 || index <= 0) {
					pre.setEnabled(false);
					return;
				}
				pre.setEnabled(true);
			}
		});
		// 下移事件
		action.add(new AnAction(AllIcons.Actions.MoveDown) {
			@Override
			public void actionPerformed(@NotNull AnActionEvent event) {
				int index = jbList.getSelectedIndex();
				int length = jbListModel.getSize();
				if (length <= 1 || index >= (length - 1)) {
					return;
				}
				String name = jbList.getSelectedValue();
				moveDownHandler(name, index);
				resetModelData(name);
			}
			
			@Override
			public void update(@NotNull AnActionEvent event) {
				Presentation pre = event.getPresentation();
				int index = jbList.getSelectedIndex();
				int length = jbListModel.getSize();
				if (length <= 1 || index >= (length - 1)) {
					pre.setEnabled(false);
					return;
				}
				pre.setEnabled(true);
			}
		});
		
		String title = "Head Toolbar";
		ActionManager m = ActionManager.getInstance();
		return m.createActionToolbar(title, action, //
			true).getComponent();
	}
	
	protected abstract void renameHandler(String name, String newName);
	
	protected abstract void modifiedHandler(String name, String text);
	
	protected abstract void copyHandler(String name, String newName);
	
	protected abstract void moveDownHandler(String name, int index);
	
	protected abstract void moveUpHandler(String name, int index);
	
	@Nullable
	protected abstract Template getTemplate(String name);
	
	protected abstract void deleteHandler(String name);
	
	@NotNull
	protected abstract List<String> getTemplateData();
	
	protected abstract void addHandler(String name);
	
	
}
