
package sn.mini.java.gen

import sn.mini.java.jdbc.model.DaoTable
import sn.mini.java.util.json.JSONArray
import sn.mini.java.util.lang.StringUtil
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.sql.SQLException
import java.util.*

fun <T> Iterable<T>.join(separator: CharSequence = ", "): String {
    return this.joinToString(separator, "", "", -1, "", null)
}

/**
 * com.cfinal.gen.GENAll.java
 *
 * @author XChao
 */
object GENBeanJKotlin {
    internal val REGEX = "((" + GENConfig.DB_PREFIX_NAME + ")(_)*)"

    private val PACKAGE_NAME_ALL = GENConfig.PACKAGE_NAME
    private val KOTLIN_NAME = GENConfig.TABLE_JAVA_NAME + ".kt"
    private val KOTLIN_CLASS_NAME = GENConfig.TABLE_JAVA_NAME

    internal val COLUMN_TYPE: MutableMap<String, ColumnTypes> = HashMap()

    val beansFilePath: File
        get() {
            val file = File(GENConfig.PROJECT_PATH, GENConfig.SOURCES_NAME)
            return File(file, GENConfig.PACKAGE_NAME.replace(".", "/"))
        }

    class ColumnTypes(val name: String, val impt: String?)

    init {
        COLUMN_TYPE["DEFAULT"] = ColumnTypes("Any", null)
        COLUMN_TYPE["VARCHAR"] = ColumnTypes("String", null)
        COLUMN_TYPE["CHAR"] = ColumnTypes("String", null)
        COLUMN_TYPE["BINARY"] = ColumnTypes("String", null)
        COLUMN_TYPE["TEXT"] = ColumnTypes("String", null)

        COLUMN_TYPE["BIGINT"] = ColumnTypes("Long", null)
        COLUMN_TYPE["INT"] = ColumnTypes("Int", null)
        COLUMN_TYPE["SMALLINT"] = ColumnTypes("Int", null)
        COLUMN_TYPE["TINYINT"] = ColumnTypes("Int", null)

        COLUMN_TYPE["BOOL"] = ColumnTypes("Boolean", null)
        COLUMN_TYPE["BOOLEAN"] = ColumnTypes("Boolean", null)

        COLUMN_TYPE["DOUBLE"] = ColumnTypes("Double", null)
        COLUMN_TYPE["FLOAT"] = ColumnTypes("Float", null)
        COLUMN_TYPE["DECIMAL"] = ColumnTypes("Double", null)

        COLUMN_TYPE["DATE"] = ColumnTypes("Date", "java.util.Date")
        COLUMN_TYPE["TIME"] = ColumnTypes("Date", "java.util.Date")
        COLUMN_TYPE["DATETIME"] = ColumnTypes("Date", "java.util.Date")
        COLUMN_TYPE["TIMESTAMP"] = ColumnTypes("Date", "java.util.Date")

        COLUMN_TYPE["BLOB"] = ColumnTypes("Blob", "java.sql.Blob")
    }

    fun getColumnType(typeName: String): ColumnTypes? {
        return COLUMN_TYPE[typeName.toUpperCase()] ?: return COLUMN_TYPE.get("DEFAULT")
    }

    internal fun genBean(columns: JSONArray, pkMaps: Map<String, Boolean>) {
        try {
            // --------------- 生成实体 -----------------------
            // 头声明
            val packages = ArrayList<String>()
            // import 语句
            val imports = HashSet<String>()
            // import 语句
            val headers = ArrayList<String>()
            // 内容
            val contents = ArrayList<String>()
            // 结尾
            val footers = ArrayList<String>()

            packages.add("package $PACKAGE_NAME_ALL")
            packages.add("\r\n")

            imports.add("import sn.mini.java.jdbc.annotaion.Table")
            imports.add("import sn.mini.java.jdbc.annotaion.Column")
            imports.add("import sn.mini.java.jdbc.model.IDaoModel")
            headers.add("""@Table("${GENConfig.TABLE_DB_NAME}")""")
            headers.add("""data class ${KOTLIN_CLASS_NAME} (  """)
            ///
            val companion_header = ArrayList<String>()
            val companion_content = ArrayList<String>()
            val companion_footer = ArrayList<String>()

            val idJavaNameAndType = ArrayList<String>()
            val idJavaName = ArrayList<String>()
            val idDbName = ArrayList<String>()
            val allJavaNameAndType = ArrayList<String>()
            val allJavaName = ArrayList<String>()
            val allDbName = ArrayList<String>()
            //
            companion_header.add("  companion object {")
            //companion_content.add("""       val INSTANCE = ${KOTLIN_CLASS_NAME}() """)
            companion_content.add("""       /** ${GENConfig.TABLE_DB_NAME} */ """)
            companion_content.add("""       const val TABLE_NAME = "${GENConfig.TABLE_DB_NAME}" """)
            companion_footer.add("  }")
            run {
                var i = 0
                val len = columns.size
                while (i < len) {
                    val columnItem = columns.getJSONObject(i)
                    val columnName = columnItem.getString("COLUMN_NAME") // 字段名称
                    val columnType = columnItem.getString("TYPE_NAME") // 类型名称
                    val columnRemarks = columnItem.getString("REMARKS") // 字段说明
                    val columnAlias = columnName.replaceFirst(REGEX.toRegex(), "") // 别名名称
                    val types = getColumnType(columnType) // 类型
                    val javaPropName = StringUtil.toJavaName(columnAlias, false) // java属性名称
                    //val gstterName = StringUtil.toJavaName(columnAlias, true) // java getter，setter主要名称

                    pkMaps[columnName]?.let {
                        if (it == true) {
                            idJavaNameAndType.add("${javaPropName}:${types?.name}")
                            idJavaName.add(javaPropName)
                            idDbName.add(columnName.toUpperCase())
                        }
                    }
                    allJavaNameAndType.add("${javaPropName}:${types?.name}")
                    allJavaName.add(javaPropName)
                    allDbName.add(columnName.toUpperCase())

                    types?.impt?.let {
                        imports.add("import ${types.impt} ")
                    }
                    var isPK = false
                    pkMaps[columnName]?.let {
                        if (it == true) {
                            isPK = true
                        }
                    }
                    headers.add("\t/** ${columnRemarks} */")
                    if (isPK) {
                        headers.add("""	@Column(value="${columnName}", des = 2)""")
                    } else {
                        headers.add("""	@Column(value="${columnName}")""")
                    }
                    if (i != (len - 1)) {
                        headers.add("""	var ${javaPropName}:${types?.name}? = null, """)
                    } else {
                        headers.add("""	var ${javaPropName}:${types?.name}? = null""")
                    }

                    companion_content.add(""" /** ${columnRemarks}: ${columnName} */  """)
                    companion_content.add(""" const val ${columnName.toUpperCase()} = "${columnName}" """)

                    i++
                }
            }



            headers.add(""") :IDaoModel<${KOTLIN_CLASS_NAME}>{ """)

            // 引入IDao, Sql, Paging, 类
            imports.add("import sn.mini.java.jdbc.IDao")
            imports.add("import sn.mini.java.jdbc.Sql")
            imports.add("import sn.mini.java.jdbc.Paging")

            // 生 insert 方法
            companion_content.add("""
	fun insert(dao:IDao, ${allJavaNameAndType.join(", ")}):Int {
		return dao.insert(TABLE_NAME, arrayOf(${allDbName.join(", ")}), ${allJavaName.join(", ")})
	}""")

            // 生成 根据ID修改信息的方法
            companion_content.add("""
	fun updateById(dao:IDao, ${allJavaNameAndType.join(", ")}):Int {
		return dao.update(TABLE_NAME, arrayOf(${allDbName.join(", ")}), arrayOf(${idDbName.join(", ")}), ${allJavaName.join(", ")}, ${idJavaName.join(", ")})
	}""")

            // 根据ID 删除实体信息方法
            companion_content.add("""
	fun deleteById(dao:IDao, ${idJavaNameAndType.join(", ")}):Int {
		return dao.delete(TABLE_NAME, arrayOf(${idDbName.join(", ")}), ${idJavaName.join(", ")})
	}""")

            // 根据ID 查询实体方法
            companion_content.add("""
	fun findById(dao:IDao, ${idJavaNameAndType.join(", ")}): ${KOTLIN_CLASS_NAME} {
		val sql = Sql.createSelect(TABLE_NAME, ${allDbName.join(", ")}).whereTrue()""")
            var i = 0
            val len = idDbName.size
            while (i < len) {
                companion_content.add("	sql.andEq(" + idDbName[i] + ").params(" + idJavaName[i] + ")")
                i++
            }
            companion_content.add("""
		return dao.queryOne(${KOTLIN_CLASS_NAME}::class.java, sql)
	}""")

            companion_content.add("""
	fun find(dao:IDao): MutableList<${KOTLIN_CLASS_NAME}> {
		val sql = Sql.createSelect(TABLE_NAME, ${allDbName.join(", ")}).whereTrue()
		return dao.query(${KOTLIN_CLASS_NAME}::class.java, sql)
	}""")

            companion_content.add("""
	fun find(paging:Paging, dao:IDao): MutableList<${KOTLIN_CLASS_NAME}> {
		val sql = Sql.createSelect(TABLE_NAME, ${allDbName.join(", ")}).whereTrue()
		return dao.query(paging, ${KOTLIN_CLASS_NAME}::class.java, sql)
	}""")

            footers.add("} ")

            var writer: OutputStreamWriter? = null
            var outputStream: OutputStream? = null
            try {
                val file = beansFilePath
                if (!file.exists() && file.mkdirs()) {
                    println("创建文件夹成功")
                }
                println("entity_package_name_all: $PACKAGE_NAME_ALL")
                outputStream = FileOutputStream(File(file, KOTLIN_NAME))
                // 创建JAVA文件 response
                writer = OutputStreamWriter(outputStream)
                // 写入package 语句
                writer.write(StringUtil.join(packages, "\r\n"))
                writer.write("\r\n")
                // 写入import 语句
                writer.write(StringUtil.join(imports, "\r\n"))
                writer.write("\r\n")
                // 写入JAVA类声明
                writer.write(StringUtil.join(headers, "\r\n"))
                writer.write("\r\n")

                // 写入静态字段信息
                writer.write(StringUtil.join(companion_header, "\r\n"))
                writer.write(StringUtil.join(companion_content, "\r\n"))
                writer.write(StringUtil.join(companion_footer, "\r\n"))

                // 写入JAVA类 内容（属性，方法）
                for (method in contents) {
                    writer.write("\t" + method + "\r\n")
                }
                // 写入JAVA类结束
                writer.write(StringUtil.join(footers, "\r\n"))
                writer.flush() // 刷新buffer

            } finally {
                if (writer != null) {
                    writer.close()
                }
                if (outputStream != null) {
                    outputStream.close()
                }
            }

            // 生成实体代码
            println("------------------  Beans 生成完成 ---------------------------")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @JvmStatic
    @Throws(SQLException::class, Exception::class)
    fun main(args: Array<String>) {
        GENConfig.getDao().use {dao ->
            val columns = DaoTable.getColumns(dao, GENConfig.TABLE_DB_NAME)
            val pks = DaoTable.getPrimaryKey(dao, GENConfig.TABLE_DB_NAME)
            val pkMaps = HashMap<String, Boolean>()
            var i = 0
            val len = pks.size
            while (i < len) {
                val columnItem = pks.getJSONObject(i)
                pkMaps[columnItem.getString("COLUMN_NAME")] = true
                i++
            }

            genBean(columns, pkMaps)
        }
    }

}
