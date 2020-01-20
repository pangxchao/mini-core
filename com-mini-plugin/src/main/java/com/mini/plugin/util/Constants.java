package com.mini.plugin.util;

public interface Constants {
	// 弹出层标题
	String TITLE_INFO = "Mini Code Title";
	// 确认删除分组
	String CONFIRM_DELETE_GROUP = "Confirm To Remove Group ‘%s’?";
	// 确认删除选中的 Mapper 映射
	String CONFIRM_DELETE_MAPPER = "Confirm Delete Selected Item?";
	// 确认删除模板
	String CONFIRM_DELETE_TEMPLATE = "Are you sure you want to delete ‘%s’?";
	// 重置提示
	String RESET_DEFAULT_SETTING = "Are you sure to reset the default configuration?\n" +
		"Resetting the default configuration will only restore the plug-in's own group " +
		"configuration information, \nand will not delete the user's new group information.";
}
