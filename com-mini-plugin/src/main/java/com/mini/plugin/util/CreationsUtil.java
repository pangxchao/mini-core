package com.mini.plugin.util;

import com.intellij.database.model.*;
import com.intellij.database.psi.DbElement;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.*;

import static com.intellij.database.util.DasUtil.getForeignKeys;
import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;
import static com.intellij.openapi.util.text.StringUtil.join;
import static java.lang.String.format;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public final class CreationsUtil {
	private static final String PREV_DB_NAME = "PREV_DB_NAME";
	
	@SuppressWarnings("SpellCheckingInspection")
	public static void generator(List<DbTable> tableList, VirtualFile file) {
		File outFile = new File(file.getPath(), "create-init-def.sql");
		try (OutputStreamWriter writer = new FileWriter(outFile)) {
			tableList.stream().map(DbElement::getParent).filter(Objects::nonNull)
					.map(DasObject::getName).distinct().forEach(name -> {
				try {
					writer.write(format("CREATE DATABASE IF NOT EXISTS %s ", name));
					writer.write("DEFAULT CHARACTER SET utf8mb4; \n");
					writer.write("\n");
				} catch (Exception | Error e) {
					ThrowsUtil.hidden(e);
				}
			});
			Map<String, String> database = new HashMap<>();
			tableList.stream().filter(Objects::nonNull).sorted(Comparator.comparing(DasObject::getName)).forEach(table -> {
				String name = of(table).map(DbElement::getParent).map(DasObject::getName).orElseThrow(NullPointerException::new);
				try {
					if (!StringUtil.equals(name, database.get(PREV_DB_NAME))) {
						writer.write(format("USE %s; \n\n", name));
						database.put(PREV_DB_NAME, name);
					}
					writer.write(format("-- %s \n", defaultIfEmpty(table.getComment(), table.getName())));
					writer.write(format("CREATE TABLE %s(\n", table.getName()));
					int index = 0;
					for (DasColumn column : DasUtil.getColumns(table)) {
						if (index > 0) writer.write(", \n");
						String columnType = CreationsUtil.getDataTypeAndLength(column.getDataType());
						writer.write(format("\t%s %s %s", column.getName(), columnType, column.isNotNull() ? "NOT NULL" : ""));
						// 字段默认值
						if (StringUtil.isNotEmpty(column.getDefault())) {
							writer.write(format(" DEFAULT %s", column.getDefault()));
						}
						// 字段说明
						if (StringUtil.isNotEmpty(column.getComment())) {
							writer.write(format(" COMMENT '%s'", column.getComment()));
						}
						index++;
					}
					// 设置主键
					ofNullable(DasUtil.getPrimaryKey(table)).map(DasConstraint::getColumnsRef).map(ref -> join(ref.names(), ",")).ifPresent(keys -> {
						try {
							writer.write(format(", \n\tPRIMARY KEY (%s)", keys));
						} catch (Exception | Error e) {
							ThrowsUtil.hidden(e);
						}
					});
					writer.write("\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ");
					writer.write(format("COMMENT='%s';", defaultIfEmpty(table.getComment(), "")));
					writer.write("\n\n");
				} catch (Exception | Error e) {
					ThrowsUtil.hidden(e);
				}
			});
			
			tableList.stream().filter(Objects::nonNull).sorted(Comparator.comparing(DasObject::getName)).forEach(table -> {
				String name = of(table).map(DbElement::getParent).map(DasObject::getName).orElseThrow(NullPointerException::new);
				try {
					if (!StringUtil.equals(name, database.get(PREV_DB_NAME))) {
						writer.write(format("USE %s; \n\n", name));
						database.put(PREV_DB_NAME, name);
					}
					// 索引信息-包括生成外键时的字段索引
					Optional.ofNullable(DasUtil.getIndices(table)).ifPresent(keys -> {
						try {
							if (keys.isEmpty()) return;
							writer.write(format("\n-- %s \n", defaultIfEmpty(table.getComment(), table.getName())));
						} catch (Exception | Error e) {
							ThrowsUtil.hidden(e);
						}
						keys.toList().forEach(key -> {
							try {
								String columnNames = join(key.getColumnsRef().names(), ", ");
								// 表示该索引为唯一索引
								if (key.isUnique() || key.getName().startsWith("UQ_")) {
									String fromat = "ALTER TABLE %s ADD UNIQUE KEY %s(%s); \n";
									writer.write(format(fromat, table.getName(), key.getName(), columnNames));
								}
								// 表示该索引为全文索引
								else if (key.getName().startsWith("TIX_")) {
									String fromat = "ALTER TABLE %s ADD FULLTEXT KEY %s(%s) WITH PARSER ngram; \n";
									writer.write(format(fromat, table.getName(), key.getName(), columnNames));
								}
								// 表示该索引为外键的索引
								else if (key.getName().startsWith("FK_")) {
									String fromat = "ALTER TABLE %s ADD KEY %s(%s); \n";
									writer.write(format(fromat, table.getName(), key.getName(), columnNames));
								}
								// 表示一般索引
								else if (key.getName().startsWith("IX_")) {
									String fromat = "ALTER TABLE %s ADD INDEX %s(%s); \n";
									writer.write(format(fromat, table.getName(), key.getName(), columnNames));
								}
								// 其它索引或者键
								else {
									String fromat = "ALTER TABLE %s ADD KEY %s(%s); \n";
									writer.write(format(fromat, table.getName(), key.getName(), columnNames));
								}
							} catch (Exception | Error e) {
								ThrowsUtil.hidden(e);
							}
						});
					});
					
					// 外键
					ofNullable(getForeignKeys(table)).ifPresent(keys -> keys.toList().forEach(key -> {
						try {
							String sourceName = join(key.getColumnsRef().names(), ", ");
							String targetName = join(key.getRefColumns().names(), ", ");
							// 删除外键关联时，直接删除当前数据（级联删除）
							if (key.getDeleteRule() == DasForeignKey.RuleAction.CASCADE) {
								String format = "ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY(%s) \n\t REFERENCES %s(%s) ON DELETE CASCADE;\n";
								writer.write(format(format, table.getName(), key.getName(), sourceName, key.getRefTableName(), targetName));
							}
							// 删除外键关联时，将当前数据外键值设置为NULLL（非级联删除）
							else if (key.getDeleteRule() == DasForeignKey.RuleAction.SET_NULL) {
								String format = "ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY(%s) \n\t REFERENCES %s(%s) ON DELETE SET NULL;\n";
								writer.write(format(format, table.getName(), key.getName(), sourceName, key.getRefTableName(), targetName));
							}
							// 删除有外键关联数据时，无法删除
							else {
								String format = "ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY(%s) \n\t REFERENCES %s(%s);\n";
								writer.write(format(format, table.getName(), key.getName(), sourceName, key.getRefTableName(), targetName));
							}
						} catch (Exception | Error e) {
							ThrowsUtil.hidden(e);
						}
					}));
					
				} catch (Exception | Error e) {
					ThrowsUtil.hidden(e);
				}
			});
			// 刷新生成的文件
			file.refresh(true, true);
		} catch (Exception | Error e) {
			ThrowsUtil.hidden(e);
		}
	}
	
	@NotNull
	private static String getDataTypeAndLength(@NotNull DataType dataType) {
		if (StringUtil.isEmpty(dataType.typeName)) {
			return "";
		}
		switch (dataType.typeName.toUpperCase()) {
			case "BIGINT":
			case "LONG":
			case "INT8":
			case "MEDIUMINT":
			case "INTEGER":
			case "INT4":
			case "INT":
			case "SMALLINT":
			case "TINYINT":
			case "BOOLEAN":
			case "BOOL":
			case "BIT":
			case "DOUBLE":
			case "FLOAT":
			case "TIME":
			case "DATE":
			case "DATETIME":
			case "TIMESTAMP":
				return dataType.typeName.toUpperCase();
		}
		return dataType.getSpecification().toUpperCase();
	}
	
}
