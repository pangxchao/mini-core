@file:Suppress("unused")

package com.mini.plugin.util

import com.intellij.database.model.DasTable
import com.intellij.database.model.ObjectKind.TABLE
import com.intellij.database.psi.DbElement
import com.intellij.database.psi.DbTable
import com.intellij.database.util.DasUtil
import com.intellij.database.util.DasUtil.getTables
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys.PSI_ELEMENT
import com.intellij.openapi.actionSystem.LangDataKeys.PSI_ELEMENT_ARRAY
import com.mini.plugin.config.Settings
import com.mini.plugin.state.DbColumn
import com.mini.plugin.state.DbTableGroup
import com.mini.plugin.state.DbTableGroupMap
import org.jetbrains.annotations.NotNull

object TableUtil {
    @JvmStatic
    private val state: Settings = Settings.instance.state

    @NotNull
    @JvmStatic
    fun getChildrenTableList(@NotNull event: AnActionEvent): List<DbTable> {
        return event.getData(PSI_ELEMENT)?.takeIf { it is DbElement }?.let {
            it as DbElement
        }?.getDasChildren(TABLE)?.filterIsInstance<DbTable>() ?: listOf()
    }

    @NotNull
    @JvmStatic
    fun getDbTableListByEvent(@NotNull event: AnActionEvent): List<com.mini.plugin.state.DbTable> {
        return event.getData(PSI_ELEMENT_ARRAY)?.filterIsInstance<DbTable>()?.map {
            readDbTableFromSetting(it)
        }?.toList() ?: listOf()
    }

    @NotNull
    @JvmStatic
    fun readDbTableFromSetting(table: DbTable): com.mini.plugin.state.DbTable {
        return com.mini.plugin.state.DbTable(table).apply {
            DasUtil.getColumns(table)?.forEach {
                this.addColumn(DbColumn(it))
            }
            // 从设置中读取配置信息
            val schemaName: String = DasUtil.getSchema(table)
            state.dbTableGroupMap[schemaName]?.get(table.name)?.let {
                it.columnList.forEach { column: DbColumn -> // 获取原配置的所有字段
                    this.getDbColumn(column.name)?.reset(column)
                }
                packageName = it.packageName
                entityName = it.entityName
                namePrefix = it.namePrefix
                comment = it.comment
            }
        }
    }

    @JvmStatic
    @Suppress("RedundantExplicitType")
    fun saveDbTableToSetting(table: com.mini.plugin.state.DbTable) {
        val dbTableGroupMap: DbTableGroupMap = DbTableGroupMap()
        table.table?.dataSource?.let { getTables(it) }?.forEach { dasTable: DasTable ->
            val schemaName: String = DasUtil.getSchema(dasTable)
            dbTableGroupMap.computeIfAbsent(schemaName) { key: String ->
                return@computeIfAbsent DbTableGroup(key)
            }?.let { dbTableGroup: DbTableGroup ->
                state.dbTableGroupMap[schemaName]?.get(dasTable.name)?.let {
                    dbTableGroup.add(it)
                }
            }
        }
        // 设置新数据
        table.table?.let { DasUtil.getSchema(it) }?.let { schemaName: String ->
            dbTableGroupMap.computeIfAbsent(schemaName) { key: String ->
                return@computeIfAbsent DbTableGroup(key)
            }?.add(table)
        }
        state.dbTableGroupMap = dbTableGroupMap
    }
}