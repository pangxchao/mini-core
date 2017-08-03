/**
 * Created the com.cfinal.gen.GENAll.java
 * @created 2017年8月3日 上午10:09:24
 * @version 1.0.0
 */
package com.cfinal.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfinal.db.CFDB;
import com.cfinal.db.CFDBFactory;
import com.cfinal.db.model.mapping.CFDBTables;
import com.cfinal.util.lang.CFDate;
import com.cfinal.util.lang.CFString;

/**
 * com.cfinal.gen.GENAll.java
 * @author XChao
 */
public class GENAll {
	protected static final String REGEX = "((" + GENConfig.DB_PREFIX_NAME + ")(_)*)";

	protected static final String ENTITY_PACKAGE_NAME = "entity";
	protected static final String ENTITY_PACKAGE_NAME_ALL = GENConfig.PACKAGE_NAME + "." + ENTITY_PACKAGE_NAME;
	protected static final String ENTITY_JAVA_NAME = GENConfig.TABLE_JAVA_NAME + "Entity.java";
	protected static final String ENTITY_JAVA_NAME_ALL = ENTITY_PACKAGE_NAME_ALL + "." + ENTITY_JAVA_NAME;
	protected static final String ENTITY_JAVA_CLASS_NAME = GENConfig.TABLE_JAVA_NAME + "Entity";
	protected static final String ENTITY_JAVA_NAME_ALL_UNJAVA = ENTITY_PACKAGE_NAME_ALL + "." + ENTITY_JAVA_CLASS_NAME;

	protected static final String DB_PACKAGE_NAME_ALL = GENConfig.PACKAGE_NAME;
	protected static final String DB_JAVA_NAME = GENConfig.TABLE_JAVA_NAME + ".java";
	protected static final String DB_JAVA_NAME_ALL = DB_PACKAGE_NAME_ALL + "." + DB_JAVA_NAME;
	protected static final String DB_JAVA_CLASS_NAME = GENConfig.TABLE_JAVA_NAME;

	protected static final Map<String, ColumnTypes> COLUMN_TYPE = new HashMap<>();

	public static final class ColumnTypes {
		private String name;
		private String impt;

		public ColumnTypes(String name, String impt) {
			this.name = name;
			this.impt = impt;
		}

		public String getName() {
			return name;
		}

		public String getImpt() {
			return impt;
		}
	}

	static {
		COLUMN_TYPE.put("DEFAULT", new ColumnTypes("Object", null));
		COLUMN_TYPE.put("VARCHAR", new ColumnTypes("String", null));
		COLUMN_TYPE.put("CHAR", new ColumnTypes("String", null));
		COLUMN_TYPE.put("BINARY", new ColumnTypes("String", null));
		COLUMN_TYPE.put("TEXT", new ColumnTypes("String", null));

		COLUMN_TYPE.put("BIGINT", new ColumnTypes("long", null));
		COLUMN_TYPE.put("INT", new ColumnTypes("int", null));
		COLUMN_TYPE.put("SMALLINT", new ColumnTypes("int", null));
		COLUMN_TYPE.put("TINYINT", new ColumnTypes("int", null));

		COLUMN_TYPE.put("BOOL", new ColumnTypes("boolean", null));
		COLUMN_TYPE.put("BOOLEAN", new ColumnTypes("boolean", null));

		COLUMN_TYPE.put("DOUBLE", new ColumnTypes("double", null));
		COLUMN_TYPE.put("FLOAT", new ColumnTypes("float", null));
		COLUMN_TYPE.put("DECIMAL", new ColumnTypes("double", null));

		COLUMN_TYPE.put("DATE", new ColumnTypes("Date", "java.util.Date"));
		COLUMN_TYPE.put("TIME", new ColumnTypes("Date", "java.util.Date"));
		COLUMN_TYPE.put("DATETIME", new ColumnTypes("Date", "java.util.Date"));
		COLUMN_TYPE.put("TIMESTAMP", new ColumnTypes("Date", "java.util.Date"));

		COLUMN_TYPE.put("BLOB", new ColumnTypes("Blob", "java.sql.Blob"));
	}

	public static ColumnTypes getColumnType(String typeName) {
		ColumnTypes types = COLUMN_TYPE.get(typeName.toUpperCase());
		if(types == null) {
			return COLUMN_TYPE.get("DEFAULT");
		}
		return types;
	}

	public static File getBeansFilePath() {
		File file = new File(GENConfig.PROJECT_PATH, GENConfig.SOURCES_NAME);
		return new File(file, GENConfig.PACKAGE_NAME.replace(".", "/"));
	}

	protected static void genBean(JSONArray columns) {
		try {
			// --------------- 生成实体 -----------------------
			// 头声明
			List<String> packages = new ArrayList<>();
			// import 语句
			Set<String> imports = new HashSet<>();
			// import 语句
			List<String> headers = new ArrayList<>();
			// 内容
			List<String> contents = new ArrayList<>();
			// 结尾
			List<String> footers = new ArrayList<>();

			packages.add("/**");
			packages.add(" * Created the " + ENTITY_JAVA_NAME_ALL);
			packages.add(" * @created " + CFDate.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			packages.add("*/");
			packages.add("package " + ENTITY_PACKAGE_NAME_ALL + ";");
			packages.add("\r\n");

			headers.add("/**");
			headers.add(" * " + ENTITY_JAVA_NAME_ALL);
			headers.add(" * @author XChao");
			headers.add(" */");
			headers.add("@CFDBName(\"" + GENConfig.TABLE_DB_NAME + "\")");
			headers.add("public class " + ENTITY_JAVA_CLASS_NAME + " { ");

			imports.add("import com.cfinal.db.model.mapping.CFDBName;");
			contents.add("public static final String TABLE_NAME = \"" + GENConfig.TABLE_DB_NAME + "\";");

			List<String> static_contents = new ArrayList<>();
			List<String> prop_contents = new ArrayList<>();
			List<String> gstter_contents = new ArrayList<>();
			for (int i = 0, len = columns.size(); i < len; i++) {
				JSONObject columnItem = columns.getJSONObject(i);
				String columnName = columnItem.getString("COLUMN_NAME"); // 字段名称
				String columnType = columnItem.getString("TYPE_NAME"); // 类型名称
				String columnRemarks = columnItem.getString("REMARKS"); // 字段说明
				String columnAlias = columnName.replaceFirst(REGEX, ""); // 别名名称
				ColumnTypes types = getColumnType(columnType); // 类型
				String javaPropName = CFString.toJavaName(columnAlias, false); // java属性名称
				String gstterName = CFString.toJavaName(columnAlias, true); // java getter，setter主要名称
				if(StringUtils.isNotBlank(types.getImpt())) {
					imports.add("import " + types.getImpt() + ";");
				}
				// 字段名称静态常量生成
				static_contents.add("\r\n");
				static_contents.add("public static final String " + columnName.toUpperCase() //
					+ " = \"" + columnName + "\";");

				// 属性生成
				prop_contents.add("\r\n");
				prop_contents.add("private " + types.getName() + " " + javaPropName + "; // " + columnRemarks);

				gstter_contents.add("\r\n");
				gstter_contents.add("/**");
				gstter_contents.add(" * @return the " + javaPropName);
				gstter_contents.add("*/");
				gstter_contents.add("public " + types.getName() + " get" + gstterName + "() { ");
				gstter_contents.add("\treturn this." + javaPropName + ";");
				gstter_contents.add("} ");

				gstter_contents.add("\r\n");
				gstter_contents.add("/**");
				gstter_contents.add("* @param " + javaPropName + " the " + javaPropName + " to set");
				gstter_contents.add(" */");
				gstter_contents.add("@CFDBName(\"" + columnName + "\")");
				gstter_contents
					.add("public void set" + gstterName + "(" + types.getName() + " " + javaPropName + ") { ");
				gstter_contents.add("\tthis." + javaPropName + " = " + javaPropName + ";");
				gstter_contents.add("} ");

			}
			contents.addAll(static_contents);
			contents.addAll(prop_contents);
			contents.addAll(gstter_contents);

			footers.add("} ");

			OutputStreamWriter writer = null;
			OutputStream outputStream = null;
			try {
				File file = new File(getBeansFilePath(), ENTITY_PACKAGE_NAME);
				if(!file.exists() && file.mkdirs()) {
					System.out.println("创建文件夹成功");
				}
				System.out.println("entity_package_name_all: " + ENTITY_PACKAGE_NAME_ALL);
				outputStream = new FileOutputStream(new File(file, ENTITY_JAVA_NAME));
				// 创建JAVA文件 response
				writer = new OutputStreamWriter(outputStream);
				// 写入package 语句
				writer.write(StringUtils.join(packages, "\r\n"));
				writer.write("\r\n");
				// 写入import 语句
				writer.write(StringUtils.join(imports, "\r\n"));
				writer.write("\r\n");
				// 写入JAVA类声明
				writer.write(StringUtils.join(headers, "\r\n"));
				writer.write("\r\n");
				// 写入JAVA类 内容（属性，方法）
				for (String method : contents) {
					writer.write("\t" + method + "\r\n");
				}
				// 写入JAVA类结束
				writer.write(StringUtils.join(footers, "\r\n"));
				writer.flush(); // 刷新buffer

			} finally {
				if(writer != null) {
					writer.close();
				}
				if(outputStream != null) {
					outputStream.close();
				}
			}

			// 生成实体代码
			System.out.println("------------------  Beans 生成完成 ---------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void genDB(JSONArray columns, JSONArray pks) {
		try {
			// 头声明
			List<String> packages = new ArrayList<>();
			// import 语句
			Set<String> imports = new HashSet<>();
			// import 语句
			List<String> headers = new ArrayList<>();
			// 内容
			List<String> contents = new ArrayList<>();
			// 结尾
			List<String> footers = new ArrayList<>();

			packages.add("/**");
			packages.add(" * Created the " + DB_JAVA_NAME_ALL);
			packages.add(" * @created " + CFDate.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			packages.add("*/");
			packages.add("package " + DB_PACKAGE_NAME_ALL + ";");
			packages.add("\r\n");

			imports.add("import com.cfinal.db.model.mapping.CFDBName;");
			imports.add("import " + ENTITY_JAVA_NAME_ALL_UNJAVA + ";");
			imports.add("import com.cfinal.db.model.CFDBModel;");

			headers.add("/**");
			headers.add(" * " + DB_JAVA_NAME_ALL);
			headers.add(" * @author XChao");
			headers.add(" */");
			headers.add("@CFDBName(\"" + GENConfig.TABLE_DB_NAME + "\")");
			headers.add("public class " + DB_JAVA_CLASS_NAME + " extends " + //
				ENTITY_JAVA_CLASS_NAME + " implements CFDBModel<" + DB_JAVA_CLASS_NAME + "> { ");
			contents.add("private static final long serialVersionUID = 1L;");
			contents.add("\r\n");

			Map<String, Boolean> pkMaps = new HashMap<>();
			for (int i = 0, len = pks.size(); i < len; i++) {
				JSONObject columnItem = pks.getJSONObject(i);
				pkMaps.put(columnItem.getString("COLUMN_NAME"), true);
			}

			List<String> idJavaNameAndType = new ArrayList<>();
			List<String> idJavaName = new ArrayList<>();
			List<String> idDbName = new ArrayList<>();
			List<String> allJavaNameAndType = new ArrayList<>();
			List<String> allJavaName = new ArrayList<>();
			List<String> allDbName = new ArrayList<>();
			for (int i = 0, len = columns.size(); i < len; i++) {
				JSONObject columnItem = columns.getJSONObject(i);
				String columnName = columnItem.getString("COLUMN_NAME"); // 字段名称
				String columnType = columnItem.getString("TYPE_NAME"); // 类型名称
				String columnAlias = columnName.replaceFirst(REGEX, ""); // 别名名称
				ColumnTypes types = getColumnType(columnType); // 类型
				String javaPropName = CFString.toJavaName(columnAlias, false); // java属性名称
				if(pkMaps.get(columnName) != null && pkMaps.get(columnName)) {
					idJavaNameAndType.add(types.getName() + " " + javaPropName);
					idJavaName.add(javaPropName);
					idDbName.add(columnName.toUpperCase());
				}
				allJavaNameAndType.add(types.getName() + " " + javaPropName);
				allJavaName.add(javaPropName);
				allDbName.add(columnName.toUpperCase());
			}

			// 根据ID 删除实体信息方法
			imports.add("import com.cfinal.db.CFDB;");
			contents.add("public static int deleteById(CFDB db, " + //
				StringUtils.join(idJavaNameAndType, ", ") + ") {");
			contents.add("\treturn db.delete(TABLE_NAME, CFSql.join(" + StringUtils.join(idDbName, ", ") + //
				"), " + StringUtils.join(idJavaName, ", ") + ");");
			contents.add("}");
			contents.add("\r\n");

			// 根据ID 查询实体方法
			imports.add("import com.cfinal.db.CFSql;");
			contents.add("public static " + DB_JAVA_CLASS_NAME + " findById(CFDB db, " + //
				StringUtils.join(idJavaNameAndType, ", ") + ") {");
			contents.add("\tCFSql sql = CFSql.createQuery(TABLE_NAME, " + StringUtils.join(allDbName, ", ") + ");");
			if(idDbName.size() == 1) {
				contents.add(
					"\tsql.appendWhereTrue().appendWhereEqInAnd(" + idDbName.get(0) + ", " + idJavaName.get(0) + ");");
			} else {
				contents.add("\tsql.appendWhereTrue();");
				for (int i = 0, len = idDbName.size(); i < len; i++) {
					contents.add("\tsql.appendWhereEqInAnd(" + idDbName.get(i) + ", " + idJavaName.get(i) + ");");
				}
			}
			contents.add("\treturn db.queryOne(" + DB_JAVA_CLASS_NAME + ".class, sql);");
			contents.add("}");
			contents.add("\r\n");

			// public static List<info> find(CFDB db)
			imports.add("import java.util.List;");
			contents.add("public static List<" + DB_JAVA_CLASS_NAME + "> find(CFDB db) {");
			contents.add("\tCFSql sql = CFSql.createQuery(TABLE_NAME, " + StringUtils.join(allDbName, ", ") + ");");
			contents.add("\tsql.appendWhereTrue();");
			contents.add("\treturn db.query(" + DB_JAVA_CLASS_NAME + ".class, sql);");
			contents.add("}");
			contents.add("\r\n");

			imports.add("import com.cfinal.db.paging.CFPaging;");
			contents.add("public static List<" + DB_JAVA_CLASS_NAME + "> find(CFPaging paging, CFDB db) {");
			contents.add("\tCFSql sql = CFSql.createQuery(TABLE_NAME, " + StringUtils.join(allDbName, ", ") + ");");
			contents.add("\tsql.appendWhereTrue();");
			contents.add("\treturn db.query(paging, " + DB_JAVA_CLASS_NAME + ".class, sql);");
			contents.add("}");
			contents.add("\r\n");

			footers.add("} ");

			OutputStreamWriter writer = null;
			OutputStream outputStream = null;
			try {
				File file = getBeansFilePath();
				if(!file.exists() && file.mkdirs()) {
					System.out.println("创建文件夹成功");
				}
				System.out.println("db_package_name_all: " + DB_PACKAGE_NAME_ALL);
				outputStream = new FileOutputStream(new File(file, DB_JAVA_NAME));
				// 创建JAVA文件 response
				writer = new OutputStreamWriter(outputStream);
				// 写入package 语句
				writer.write(StringUtils.join(packages, "\r\n"));
				writer.write("\r\n");
				// 写入import 语句
				writer.write(StringUtils.join(imports, "\r\n"));
				writer.write("\r\n");
				// 写入JAVA类声明
				writer.write(StringUtils.join(headers, "\r\n"));
				writer.write("\r\n");
				// 写入JAVA类 内容（属性，方法）
				for (String method : contents) {
					writer.write("\t" + method + "\r\n");
				}
				// 写入JAVA类结束
				writer.write(StringUtils.join(footers, "\r\n"));
				writer.flush(); // 刷新buffer
			} finally {
				if(writer != null) {
					writer.close();
				}
				if(outputStream != null) {
					outputStream.close();
				}
			}
			System.out.println("------------------  BeanDB 生成完成 ---------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException, Exception {
		CFDB db = null;
		try {
			db = GENConfig.getDB(); // 创建数据库连接
			// 查询当前表的所有字段
			JSONArray columns = CFDBTables.getColumns(GENConfig.getDB(), GENConfig.TABLE_DB_NAME);
			genBean(columns);
			genDB(columns, CFDBTables.getPrimaryKey(db, GENConfig.TABLE_DB_NAME));
		} finally {
			CFDBFactory.close(db);
		}
	}

}
