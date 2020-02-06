package com.mini.plugin.util;

import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DasConstraint;
import com.intellij.database.model.DasForeignKey;
import com.intellij.database.model.DasObject;
import com.intellij.database.psi.DbElement;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;

import static com.intellij.database.util.DasUtil.getForeignKeys;
import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;
import static java.util.Optional.ofNullable;

public final class CreationsUtil {
	@SuppressWarnings("SpellCheckingInspection")
	public static void generator(List<DbTable> tableList, VirtualFile file) {
		File outFile = new File(file.getPath(), "create-table.sql");
		try (OutputStreamWriter writer = new FileWriter(outFile)) {
			tableList.stream().map(DbElement::getParent).filter(Objects::nonNull)
				.map(DasObject::getName).distinct().forEach(name -> {
				try {
					writer.write(String.format("CREATE DATABASE IF NOT EXISTS %s ", name));
					writer.write("DEFAULT CHARACTER SET utf8mb4; \n");
					writer.write("\n");
				} catch (Exception | Error e) {
					ThrowsUtil.hidden(e);
				}
			});
			tableList.stream().sorted((info, table) -> 0).forEach(table -> {
				String name = ofNullable(table).map(DbElement::getParent)
					.map(DasObject::getName).orElseThrow(NullPointerException::new);
				try {
					writer.write(String.format("USE %s; \n", name));
					writer.write(String.format("CREATE TABLE %s.%s(\n", name, table.getName()));
					int index = 0;
					for (DasColumn column : DasUtil.getColumns(table)) {
						if (index > 0) writer.write(", \n");
						writer.write(String.format("\t%s %s %s COMMENT '%s'", column.getName(),
							defaultIfEmpty(column.getDataType().typeName, "").toUpperCase(),
							column.isNotNull() ? "NOT NULL" : "",
							column.getComment()));
						index++;
					}
					// 设置主键
					ofNullable(DasUtil.getPrimaryKey(table)).map(DasConstraint::getColumnsRef)
						.map(ref -> StringUtil.join(ref.names(), ",")).ifPresent(keys -> {
						try {
							writer.write(String.format(", \n\tPRIMARY KEY (%s)", keys));
						} catch (Exception | Error e) {
							ThrowsUtil.hidden(e);
						}
					});
					// 索引信息
					ofNullable(DasUtil.getTableKeys(table)).ifPresent(keys -> keys.toList() //
						.stream().filter(key -> !key.isPrimary()).forEach(key -> {
							try {
								writer.write(String.format(", \n\tKEY %s(%s)", key.getName(), //
									StringUtil.join(key.getColumnsRef().names(), ", ")));
							} catch (Exception | Error e) {
								ThrowsUtil.hidden(e);
							}
						}));
					
					// 外键
					ofNullable(getForeignKeys(table)).ifPresent(keys -> keys.toList().forEach(key -> {
						try {
							String sourceName = StringUtil.join(key.getColumnsRef().names(), ", ");
							String targetName = StringUtil.join(key.getRefColumns().names(), ", ");
							writer.write(String.format(", \n\tKEY %s(%s)", key.getName(), sourceName));
							String deleteRule = "";
							if (key.getDeleteRule() == DasForeignKey.RuleAction.CASCADE) {
								deleteRule = "ON DELETE CASCADE";
							} else if (key.getDeleteRule() == DasForeignKey.RuleAction.SET_NULL) {
								deleteRule = "ON DELETE SET NULL";
							}
							writer.write(String.format(", \n\tCONSTRAINT %s FOREIGN KEY(%s) REFERENCES " +
									"%s(%s) %s", key.getName(), sourceName, key.getRefTableName(),
								targetName, deleteRule));
						} catch (Exception | Error e) {
							ThrowsUtil.hidden(e);
						}
					}));
					
					writer.write("\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ");
					writer.write(String.format("COMMENT='%s';\n\n", table.getComment()));
				} catch (Exception | Error e) {
					ThrowsUtil.hidden(e);
				}
			});
		} catch (Exception |
			Error e) {
			ThrowsUtil.hidden(e);
		}
	}
	
}
