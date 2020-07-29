package com.mini.plugin.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.mini.plugin.util.StringUtil;
import com.mini.plugin.util.ThrowsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.util.HashMap;
import java.util.TreeMap;

import static com.intellij.openapi.components.ServiceManager.getService;
import static com.mini.plugin.util.ThrowsUtil.hidden;

/**
 * 设置信息类
 * @author xchao
 */
@State(name = "MiniCode", storages = @Storage("Mini-Code.xml"))
public final class Settings implements Serializable, PersistentStateComponent<Settings.SettingState> {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final SettingState STATE = new SettingState();
	public static final String DEFAULT_NAME = "Default";
	
	
	public final synchronized void setDataTypeGroupMap(TreeMap<String, DataTypeGroup> dataTypeGroup) {
		try {
			if (dataTypeGroup == null) dataTypeGroup = new TreeMap<>();
			ObjectWriter writer = MAPPER.writerWithDefaultPrettyPrinter();
			String content = writer.writeValueAsString(dataTypeGroup);
			Settings.STATE.setDataTypeGroup(content);
		} catch (JsonProcessingException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	public final synchronized void setTemplateGroupMap(TreeMap<String, TemplateGroup> templateGroup) {
		try {
			if (templateGroup == null) templateGroup = new TreeMap<>();
			ObjectWriter writer = MAPPER.writerWithDefaultPrettyPrinter();
			String content = writer.writeValueAsString(templateGroup);
			Settings.STATE.setTemplateGroup(content);
		} catch (JsonProcessingException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	public final synchronized void setDatabaseInfoMap(HashMap<String, DatabaseInfo> databaseInfo) {
		try {
			if (databaseInfo == null) databaseInfo = new HashMap<>();
			ObjectWriter writer = MAPPER.writerWithDefaultPrettyPrinter();
			String content = writer.writeValueAsString(databaseInfo);
			Settings.STATE.setDatabaseInfo(content);
		} catch (JsonProcessingException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	@NotNull
	public final synchronized TreeMap<String, DataTypeGroup> getDataTypeGroupMap() {
		try {
			if (StringUtil.isEmpty(STATE.dataTypeGroup)) {
				return new TreeMap<>();
			}
			String v = Settings.STATE.dataTypeGroup;
			return MAPPER.readValue(v, DataTypeGroup.TYPE);
		} catch (IOException | RuntimeException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	@NotNull
	public final synchronized TreeMap<String, TemplateGroup> getTemplateGroupMap() {
		try {
			if (StringUtil.isEmpty(STATE.templateGroup)) {
				return new TreeMap<>();
			}
			String v = Settings.STATE.templateGroup;
			return MAPPER.readValue(v, TemplateGroup.TYPE);
		} catch (IOException | RuntimeException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	@NotNull
	public final synchronized HashMap<String, DatabaseInfo> getDatabaseInfoMap() {
		try {
			if (StringUtil.isEmpty(STATE.databaseInfo)) {
				return new HashMap<>();
			}
			String v = Settings.STATE.databaseInfo;
			return MAPPER.readValue(v, DatabaseInfo.TYPE);
		} catch (IOException | RuntimeException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	
	public final synchronized void setDataTypeGroupName(String dataTypeGroupName) {
		STATE.dataTypeGroupName = dataTypeGroupName;
	}
	
	public final synchronized void setTemplateGroupName(String templateGroupName) {
		STATE.templateGroupName = templateGroupName;
	}
	
	public final synchronized void setEncoding(String encoding) {
		STATE.encoding = encoding;
	}
	
	public final synchronized void setAuthor(String author) {
		STATE.author = author;
	}
	
	@NotNull
	public final synchronized String getDataTypeGroupName() {
		if (StringUtil.isNotEmpty(STATE.dataTypeGroupName)) {
			return STATE.dataTypeGroupName;
		}
		return DEFAULT_NAME;
	}
	
	
	@NotNull
	public final synchronized String getTemplateGroupName() {
		if (StringUtil.isNotEmpty(STATE.templateGroupName)) {
			return STATE.templateGroupName;
		}
		return DEFAULT_NAME;
	}
	
	@NotNull
	public final synchronized String getEncoding() {
		if (StringUtil.isNotEmpty(STATE.encoding)) {
			return STATE.encoding;
		}
		return "UTF-8";
	}
	
	@NotNull
	public final synchronized String getAuthor() {
		if (StringUtil.isNotEmpty(STATE.author)) {
			return STATE.author;
		}
		return "xchao";
	}
	
	/**
	 * 添加或者修改一组数据类型映射信息
	 * @param dataTypeGroup 数据类型映射信息
	 */
	public final synchronized void putDataTypeGroup(@NotNull DataTypeGroup dataTypeGroup) {
		TreeMap<String, DataTypeGroup> map = getDataTypeGroupMap();
		map.put(dataTypeGroup.getGroupName(), dataTypeGroup);
		this.setDataTypeGroupMap(map);
	}
	
	/**
	 * 添加或者修改一组模板分组信息
	 * @param templateGroup 模板分组信息
	 */
	public final synchronized void putTemplateGroup(@NotNull TemplateGroup templateGroup) {
		TreeMap<String, TemplateGroup> map = getTemplateGroupMap();
		map.put(templateGroup.getGroupName(), templateGroup);
		this.setTemplateGroupMap(map);
	}
	
	/**
	 * 添加或者修改一组数据库配置信息
	 * @param databaseInfo 数据库信息
	 */
	public final synchronized void putDatabaseInfo(@NotNull DatabaseInfo databaseInfo) {
		HashMap<String, DatabaseInfo> map = getDatabaseInfoMap();
		map.put(databaseInfo.getDatabaseName(), databaseInfo);
		this.setDatabaseInfoMap(map);
	}
	
	/**
	 * 获取指定分组名称的数据映射分组信息
	 * @param groupName 分组名称
	 * @return 数据映射分组信息
	 */
	@Nullable
	public final synchronized DataTypeGroup getDataTypeGroup(String groupName) {
		return getDataTypeGroupMap().get(groupName);
	}
	
	/**
	 * 获取指定分组名称的模板分组信息
	 * @param groupName 分组名称
	 * @return 模板分组信息
	 */
	@Nullable
	public final synchronized TemplateGroup getTemplateGroup(String groupName) {
		return getTemplateGroupMap().get(groupName);
	}
	
	/**
	 * 获取指定数据名称下的数据库信息
	 * @param databaseName 数据库名称
	 * @return 数据库信息
	 */
	@Nullable
	public final synchronized DatabaseInfo getDatabaseInfo(String databaseName) {
		return getDatabaseInfoMap().get(databaseName);
	}
	
	/**
	 * 获取当前数据类型映射信息
	 * @return 数据类型映射信息
	 */
	@Nullable
	public final synchronized DataTypeGroup getDataTypeGroup() {
		return getDataTypeGroup(getDataTypeGroupName());
	}
	
	/**
	 * 获取当前模板分组信息
	 * @return 模板分组信息
	 */
	@Nullable
	public final synchronized TemplateGroup getTemplateGroup() {
		return getTemplateGroup(getTemplateGroupName());
	}
	
	// 加载默认配置
	public Settings() {
		init_default();
	}
	
	/**
	 * 获取单例实例对象
	 * @return 实例对象
	 */
	public synchronized static Settings getInstance() {
		return getService(Settings.class);
	}
	
	@Override
	public final void loadState(@NotNull SettingState state) {
		if (StringUtil.isNotEmpty(state.getDataTypeGroupName())) {
			STATE.setDataTypeGroupName(state.getDataTypeGroupName());
		}
		if (StringUtil.isNotEmpty(state.getTemplateGroupName())) {
			STATE.setTemplateGroupName(state.getTemplateGroupName());
		}
		if (StringUtil.isNotEmpty(state.getDataTypeGroup())) {
			STATE.setDataTypeGroup(state.getDataTypeGroup());
		}
		if (StringUtil.isNotEmpty(state.getTemplateGroup())) {
			STATE.setTemplateGroup(state.getTemplateGroup());
		}
		if (StringUtil.isNotEmpty(state.getDatabaseInfo())) {
			STATE.setDatabaseInfo(state.getDatabaseInfo());
		}
		if (StringUtil.isNotEmpty(state.getEncoding())) {
			STATE.setEncoding(state.getEncoding());
		}
		if (StringUtil.isNotEmpty(state.getAuthor())) {
			STATE.setAuthor(state.getAuthor());
		}
	}
	
	@NotNull
	@Override
	public final SettingState getState() {
		return STATE;
	}
	
	public synchronized void resetToDefault() {
		STATE.setDataTypeGroupName(null);
		STATE.setTemplateGroupName(null);
		STATE.setDataTypeGroup(null);
		STATE.setTemplateGroup(null);
		STATE.setEncoding(null);
		STATE.setAuthor(null);
		init_default();
	}
	
	// 重置到默认配置
	private synchronized void init_default() {
		// 构造 DataTypeGroup
		DataType dataType;
		DataTypeGroup dataTypeGroup = new DataTypeGroup();
		dataTypeGroup.setGroupName(Settings.DEFAULT_NAME);
		// LONGTEXT => String
		dataType = new DataType();
		dataType.setDatabaseType("LONGTEXT");
		dataType.setJavaType(String.class.getCanonicalName());
		dataType.setNullJavaType(String.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// MEDIUMTEXT => String
		dataType = new DataType();
		dataType.setDatabaseType("MEDIUMTEXT");
		dataType.setJavaType(String.class.getCanonicalName());
		dataType.setNullJavaType(String.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// TEXT => String
		dataType = new DataType();
		dataType.setDatabaseType("TEXT");
		dataType.setJavaType(String.class.getCanonicalName());
		dataType.setNullJavaType(String.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// TINYTEXT => String
		dataType = new DataType();
		dataType.setDatabaseType("TINYTEXT");
		dataType.setJavaType(String.class.getCanonicalName());
		dataType.setNullJavaType(String.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// VARCHAR => String
		dataType = new DataType();
		dataType.setDatabaseType("VARCHAR");
		dataType.setJavaType(String.class.getCanonicalName());
		dataType.setNullJavaType(String.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// CHAR => String
		dataType = new DataType();
		dataType.setDatabaseType("CHAR");
		dataType.setJavaType(String.class.getCanonicalName());
		dataType.setNullJavaType(String.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// NUMERIC => BigDecimal
		dataType = new DataType();
		dataType.setDatabaseType("NUMERIC");
		dataType.setJavaType(BigDecimal.class.getCanonicalName());
		dataType.setNullJavaType(BigDecimal.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// BIGINT => long
		dataType = new DataType();
		dataType.setDatabaseType("BIGINT");
		dataType.setJavaType(long.class.getCanonicalName());
		dataType.setNullJavaType(Long.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// LONG => long
		dataType = new DataType();
		dataType.setDatabaseType("LONG");
		dataType.setJavaType(long.class.getCanonicalName());
		dataType.setNullJavaType(Long.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// INT8 => long
		dataType = new DataType();
		dataType.setDatabaseType("INT8");
		dataType.setJavaType(long.class.getCanonicalName());
		dataType.setNullJavaType(Long.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// MEDIUMINT => int
		dataType = new DataType();
		dataType.setDatabaseType("MEDIUMINT");
		dataType.setJavaType(int.class.getCanonicalName());
		dataType.setNullJavaType(Integer.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// INTEGER => int
		dataType = new DataType();
		dataType.setDatabaseType("INTEGER");
		dataType.setJavaType(int.class.getCanonicalName());
		dataType.setNullJavaType(Integer.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// INT4 => int
		dataType = new DataType();
		dataType.setDatabaseType("INT4");
		dataType.setJavaType(int.class.getCanonicalName());
		dataType.setNullJavaType(Integer.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// INT => int
		dataType = new DataType();
		dataType.setDatabaseType("INT");
		dataType.setJavaType(int.class.getCanonicalName());
		dataType.setNullJavaType(Integer.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// SMALLINT => short
		dataType = new DataType();
		dataType.setDatabaseType("SMALLINT");
		dataType.setJavaType(short.class.getCanonicalName());
		dataType.setNullJavaType(Short.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// TINYINT => byte
		dataType = new DataType();
		dataType.setDatabaseType("TINYINT");
		dataType.setJavaType(byte.class.getCanonicalName());
		dataType.setNullJavaType(Byte.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// BOOLEAN => boolean
		dataType = new DataType();
		dataType.setDatabaseType("BOOLEAN");
		dataType.setJavaType(boolean.class.getCanonicalName());
		dataType.setNullJavaType(Boolean.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// BOOL => boolean
		dataType = new DataType();
		dataType.setDatabaseType("BOOL");
		dataType.setJavaType(boolean.class.getCanonicalName());
		dataType.setNullJavaType(Boolean.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// BIT => boolean
		dataType = new DataType();
		dataType.setDatabaseType("BIT");
		dataType.setJavaType(boolean.class.getCanonicalName());
		dataType.setNullJavaType(Boolean.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// DECIMAL => double
		dataType = new DataType();
		dataType.setDatabaseType("DECIMAL");
		dataType.setJavaType(double.class.getCanonicalName());
		dataType.setNullJavaType(Double.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// DOUBLE => double
		dataType = new DataType();
		dataType.setDatabaseType("DOUBLE");
		dataType.setJavaType(double.class.getCanonicalName());
		dataType.setNullJavaType(Double.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// FLOAT => float
		dataType = new DataType();
		dataType.setDatabaseType("FLOAT");
		dataType.setJavaType(float.class.getCanonicalName());
		dataType.setNullJavaType(Float.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// TIME => java.sql.Time
		dataType = new DataType();
		dataType.setDatabaseType("TIME");
		dataType.setJavaType(java.sql.Time.class.getCanonicalName());
		dataType.setNullJavaType(java.sql.Time.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// DATE => java.util.Date
		dataType = new DataType();
		dataType.setDatabaseType("DATE");
		dataType.setJavaType(java.util.Date.class.getCanonicalName());
		dataType.setNullJavaType(java.util.Date.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// DATETIME => java.util.Date
		dataType = new DataType();
		dataType.setDatabaseType("DATETIME");
		dataType.setJavaType(java.util.Date.class.getCanonicalName());
		dataType.setNullJavaType(java.util.Date.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// TIMESTAMP => java.util.Date
		dataType = new DataType();
		dataType.setDatabaseType("TIMESTAMP");
		dataType.setJavaType(java.util.Date.class.getCanonicalName());
		dataType.setNullJavaType(java.util.Date.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// LONGBLOB => Blob
		dataType = new DataType();
		dataType.setDatabaseType("LONGBLOB");
		dataType.setJavaType(Blob.class.getCanonicalName());
		dataType.setNullJavaType(Blob.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// MEDIUMBLOB => Blob
		dataType = new DataType();
		dataType.setDatabaseType("MEDIUMBLOB");
		dataType.setJavaType(Blob.class.getCanonicalName());
		dataType.setNullJavaType(Blob.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// BLOB => Blob
		dataType = new DataType();
		dataType.setDatabaseType("BLOB");
		dataType.setJavaType(Blob.class.getCanonicalName());
		dataType.setNullJavaType(Blob.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		// TINYBLOB => Blob
		dataType = new DataType();
		dataType.setDatabaseType("TINYBLOB");
		dataType.setJavaType(Blob.class.getCanonicalName());
		dataType.setNullJavaType(Blob.class.getCanonicalName());
		dataTypeGroup.addDataType(dataType);
		//将 DataTypeGroup 添加到设置信息中
		this.putDataTypeGroup(dataTypeGroup);
		
		// 构造 TemplateGroup
		Template template;
		TemplateGroup templateGroup = new TemplateGroup();
		templateGroup.setGroupName(Settings.DEFAULT_NAME);
		
		// 实体信息-Entity
		template = new Template();
		template.setName("Entity");
		template.setContent(loadTemplateText("Entity"));
		templateGroup.addTemplate(template);
		// 数据库基础-DaoBase
		template = new Template();
		template.setName("BaseDao");
		template.setContent(loadTemplateText("BaseDao"));
		templateGroup.addTemplate(template);
		// 数据库-Dao
		template = new Template();
		template.setName("Dao");
		template.setContent(loadTemplateText("Dao"));
		templateGroup.addTemplate(template);
		// 数据库实现-DaoImpl
		template = new Template();
		template.setName("DaoImpl");
		template.setContent(loadTemplateText("DaoImpl"));
		templateGroup.addTemplate(template);
		// 将 TemplateGroup 添加到设置信息中
		this.putTemplateGroup(templateGroup);
		
		// 默认分组名称、编码和作者
		this.setDataTypeGroupName(Settings.DEFAULT_NAME);
		this.setTemplateGroupName(Settings.DEFAULT_NAME);
		this.setEncoding("UTF-8");
		this.setAuthor("xchao");
	}
	
	public static class SettingState implements Serializable {
		private String dataTypeGroupName;
		private String templateGroupName;
		private String dataTypeGroup;
		private String templateGroup;
		private String databaseInfo;
		private String encoding;
		private String author;
		
		public void setDataTypeGroupName(String dataTypeGroupName) {
			this.dataTypeGroupName = dataTypeGroupName;
		}
		
		public void setTemplateGroupName(String templateGroupName) {
			this.templateGroupName = templateGroupName;
		}
		
		public void setDataTypeGroup(String dataTypeGroup) {
			this.dataTypeGroup = dataTypeGroup;
		}
		
		public void setTemplateGroup(String templateGroup) {
			this.templateGroup = templateGroup;
		}
		
		public void setDatabaseInfo(String databaseInfo) {
			this.databaseInfo = databaseInfo;
		}
		
		public void setEncoding(String encoding) {
			this.encoding = encoding;
		}
		
		public void setAuthor(String author) {
			this.author = author;
		}
		
		public String getDataTypeGroupName() {
			return dataTypeGroupName;
		}
		
		public String getTemplateGroupName() {
			return templateGroupName;
		}
		
		public String getDataTypeGroup() {
			return dataTypeGroup;
		}
		
		public String getTemplateGroup() {
			return templateGroup;
		}
		
		public String getDatabaseInfo() {
			return databaseInfo;
		}
		
		public String getEncoding() {
			return encoding;
		}
		
		public String getAuthor() {
			return author;
		}
	}
	
	private String loadTemplateText(String template) {
		try {
			String path = "/" + template + ".groovy";
			URL url = getClass().getResource(path);
			return UrlUtil.loadText(url);
		} catch (IOException e) {
			throw hidden(e);
		}
	}
}
