package com.mini.plugin.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import static com.mini.plugin.config.Settings.DEFAULT_NAME;

/**
 * 数据类型映射分组信息
 * @author xchao
 */
public final class DataTypeGroup extends AbstractClone<DataTypeGroup> implements Serializable {
	public static final TypeReference<TreeMap<String, DataTypeGroup>> TYPE = new TypeReference<TreeMap<String, DataTypeGroup>>() {};
	private LinkedHashMap<String, DataType> dataTypeMap;
	private String groupName;
	
	public DataTypeGroup setDataTypeMap(LinkedHashMap<String, DataType> dataTypeMap) {
		this.dataTypeMap = dataTypeMap;
		return this;
	}
	
	@NotNull
	public synchronized LinkedHashMap<String, DataType> getDataTypeMap() {
		if (DataTypeGroup.this.dataTypeMap == null) {
			dataTypeMap = new LinkedHashMap<>();
		}
		return dataTypeMap;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@NotNull
	public synchronized String getGroupName() {
		if (StringUtil.isEmpty(groupName)) {
			groupName = DEFAULT_NAME;
		}
		return groupName;
	}
	
	public DataTypeGroup addDataType(@NotNull DataType dataType) {
		getDataTypeMap().put(dataType.getDatabaseType(), dataType);
		return this;
	}
	
	@Nullable
	public final DataType getDataType(String databaseType) {
		if (this.dataTypeMap == null) return null;
		return dataTypeMap.get(databaseType);
	}
	
	@NotNull
	@Override
	public final synchronized DataTypeGroup clone() {
		final DataTypeGroup info = new DataTypeGroup();
		info.setDataTypeMap(new LinkedHashMap<>(getDataTypeMap()));
		info.setGroupName(groupName);
		return info;
	}
}
