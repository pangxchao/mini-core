package com.mini.plugin.util;

public interface Constants {
    // 弹出层标题
    String TITLE_INFO = "Mini Code Title";

    // 确认删除分组
    String CONFIRM_DELETE_GROUP = "Confirm To Remove Group '%s'?";

    // 确认删除选中的 Mapper 映射
    String CONFIRM_DELETE_MAPPER = "Confirm Delete Selected Item?";

    // 确认删除模板
    String CONFIRM_DELETE_TEMPLATE = "Are you sure you want to delete '%s'?";

    // 重置提示
    String RESET_DEFAULT_SETTING = "Are you sure to reset the default configuration? \nResetting the default configuration will only restore the plug-in's own group configuration information, \nand will not delete the user's new group information.";

    // 保存表信息失败
    String SAVE_FAILED_TABLE = "Failed to save table information.";

    // 读取表信息失败
    String READ_FAILED_TABLE = "Failed to read table information.";

    // 未发现配置信息
    String NOT_FOUND_INFO = "Configuration information of table '%s' is not found. \nDo you want to configure now";

    // 请选择包
    String CHOOSE_PACKAGE = "Please select the package.";

    // 选择文件夹
    String CHOOSE_DIRECTORY = "Choose where to store generated files";

    // 代码生成错误
    String GENERATED_ERROR = "Error Generating Code";

    String T = "\t";

    // Builder 生成标题
    String BUILDER_TITLE = "Select Fields for the Builder";
}