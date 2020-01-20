package com.mini.plugin.config;

import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.intellij.openapi.components.ServiceManager.getService;
import static com.mini.plugin.util.ThrowsUtil.hidden;

/**
 * @author xchao
 */
@State(name = "MiniCode", storages = @Storage("Mini-Code.xml"))
public final class Settings implements PersistentStateComponent<Settings> {
	public static final String DEFAULT_NAME = "Default";
	private String mapperGroupName = DEFAULT_NAME;
	private String codeGroupName = DEFAULT_NAME;
	private String dbGroupName = DEFAULT_NAME;
	private Map<String, GroupMapper> mapperGroup;
	private Map<String, GroupCode> codeGroup;
	private Map<String, GroupDB> dbGroup;
	private String encoding = "UTF-8";
	private String author = "xchao";
	
	public void setMapperGroup(Map<String, GroupMapper> mapper) {
		this.mapperGroup = mapper;
	}
	
	public void setCodeGroup(Map<String, GroupCode> code) {
		this.codeGroup = code;
	}
	
	public void setDbGroup(Map<String, GroupDB> db) {
		this.dbGroup = db;
	}
	
	public void setMapperGroupName(String mapperGroupName) {
		this.mapperGroupName = mapperGroupName;
	}
	
	public void setCodeGroupName(String codeGroupName) {
		this.codeGroupName = codeGroupName;
	}
	
	public void setDbGroupName(String dbGroupName) {
		this.dbGroupName = dbGroupName;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Nullable
	public Map<String, GroupMapper> getMapperGroup() {
		return mapperGroup;
	}
	
	@Nullable
	public Map<String, GroupCode> getCodeGroup() {
		return codeGroup;
	}
	
	@Nullable
	public Map<String, GroupDB> getDbGroup() {
		return dbGroup;
	}
	
	@Nullable
	public String getMapperGroupName() {
		return mapperGroupName;
	}
	
	@Nullable
	public String getCodeGroupName() {
		return codeGroupName;
	}
	
	@Nullable
	public String getDbGroupName() {
		return dbGroupName;
	}
	
	@Nullable
	public String getEncoding() {
		return encoding;
	}
	
	@Nullable
	public String getAuthor() {
		return author;
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
	public final void loadState(@NotNull Settings settings) {
		XmlSerializerUtil.copyBean(settings, this);
	}
	
	@Override
	public final Settings getState() {
		return this;
	}
	
	public synchronized void resetToDefault() {
		init_default();
	}
	
	// 重置到默认配置
	private synchronized void init_default() {
		this.setMapperGroup(new LinkedHashMap<>());
		this.setCodeGroup(new LinkedHashMap<>());
		this.setDbGroup(new LinkedHashMap<>());
		// 默认类型映射
		GroupMapper groupMapper = new GroupMapper();
		groupMapper.setName(DEFAULT_NAME);
		// LONGTEXT => String
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(String.class.getCanonicalName())
			.javaType(String.class.getCanonicalName())
			.databaseType("LONGTEXT")
			.build());
		// MEDIUMTEXT => String
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(String.class.getCanonicalName())
			.javaType(String.class.getCanonicalName())
			.databaseType("MEDIUMTEXT")
			.build());
		// TEXT => String
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(String.class.getCanonicalName())
			.javaType(String.class.getCanonicalName())
			.databaseType("TEXT")
			.build());
		// TINYTEXT => String
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(String.class.getCanonicalName())
			.javaType(String.class.getCanonicalName())
			.databaseType("TINYTEXT")
			.build());
		// VARCHAR => String
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(String.class.getCanonicalName())
			.javaType(String.class.getCanonicalName())
			.javaType(String.class.getCanonicalName())
			.databaseType("VARCHAR")
			.build());
		// CHAR => String
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(String.class.getCanonicalName())
			.javaType(String.class.getCanonicalName())
			.databaseType("CHAR")
			.build());
		// NUMERIC => BigDecimal
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(BigDecimal.class.getCanonicalName())
			.javaType(BigDecimal.class.getCanonicalName())
			.databaseType("NUMERIC")
			.build());
		// BIGINT => long
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Long.class.getCanonicalName())
			.javaType(long.class.getCanonicalName())
			.databaseType("BIGINT")
			.build());
		// LONG => long
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Long.class.getCanonicalName())
			.javaType(long.class.getCanonicalName())
			.databaseType("LONG")
			.build());
		// INT8 => long
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Long.class.getCanonicalName())
			.javaType(long.class.getCanonicalName())
			.databaseType("INT8")
			.build());
		// MEDIUMINT => int
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Integer.class.getCanonicalName())
			.javaType(Integer.class.getCanonicalName())
			.databaseType("MEDIUMINT")
			.build());
		// INTEGER => int
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Integer.class.getCanonicalName())
			.javaType(Integer.class.getCanonicalName())
			.databaseType("INTEGER")
			.build());
		// INT4 => int
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Integer.class.getCanonicalName())
			.javaType(int.class.getCanonicalName())
			.databaseType("INT4")
			.build());
		// INT => int
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Integer.class.getCanonicalName())
			.javaType(int.class.getCanonicalName())
			.databaseType("INT")
			.build());
		// SMALLINT => int
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Short.class.getCanonicalName())
			.javaType(short.class.getCanonicalName())
			.databaseType("SMALLINT")
			.build());
		// TINYINT => int
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Byte.class.getCanonicalName())
			.javaType(byte.class.getCanonicalName())
			.databaseType("TINYINT")
			.build());
		// BOOLEAN => boolean
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Boolean.class.getCanonicalName())
			.javaType(boolean.class.getCanonicalName())
			.databaseType("BOOLEAN")
			.build());
		// BOOL => boolean
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Boolean.class.getCanonicalName())
			.javaType(boolean.class.getCanonicalName())
			.databaseType("BOOL")
			.build());
		// BIT => boolean
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Boolean.class.getCanonicalName())
			.javaType(boolean.class.getCanonicalName())
			.databaseType("BIT")
			.build());
		// DECIMAL => double
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Double.class.getCanonicalName())
			.javaType(double.class.getCanonicalName())
			.databaseType("DECIMAL")
			.build());
		// DOUBLE => double
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Double.class.getCanonicalName())
			.javaType(double.class.getCanonicalName())
			.databaseType("DOUBLE")
			.build());
		// FLOAT => float
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(Float.class.getCanonicalName())
			.javaType(float.class.getCanonicalName())
			.databaseType("FLOAT")
			.build());
		// TIME => java.sql.Time
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(java.sql.Time.class.getCanonicalName())
			.javaType(java.sql.Time.class.getCanonicalName())
			.databaseType("TIME")
			.build());
		// DATE => java.util.Date
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(java.util.Date.class.getCanonicalName())
			.javaType(java.util.Date.class.getCanonicalName())
			.databaseType("DATE")
			.build());
		// DATETIME => java.util.Date
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(java.util.Date.class.getCanonicalName())
			.javaType(java.util.Date.class.getCanonicalName())
			.databaseType("DATETIME")
			.build());
		// TIMESTAMP => java.util.Date
		groupMapper.addElement(TypeMapper.builder()
			.nullJavaType(java.util.Date.class.getCanonicalName())
			.javaType(java.util.Date.class.getCanonicalName())
			.databaseType("TIMESTAMP")
			.build());
		// LONGBLOB => Blob.
		groupMapper.addElement(TypeMapper.builder()
			.javaType(Blob.class.getCanonicalName())
			.databaseType("LONGBLOB")
			.build());
		// MEDIUMBLOB => Blob.
		groupMapper.addElement(TypeMapper.builder()
			.javaType(Blob.class.getCanonicalName())
			.databaseType("MEDIUMBLOB")
			.build());
		// BLOB => Blob.
		groupMapper.addElement(TypeMapper.builder()
			.javaType(Blob.class.getCanonicalName())
			.databaseType("BLOB")
			.build());
		// TINYBLOB => Blob.
		groupMapper.addElement(TypeMapper.builder()
			.javaType(Blob.class.getCanonicalName())
			.databaseType("TINYBLOB")
			.build());
		// 添加默认类型映射
		this.mapperGroup.put(DEFAULT_NAME, groupMapper);
		
		// 加载默认的数据库模板
		GroupDB groupDB = new GroupDB();
		groupDB.setName(Settings.DEFAULT_NAME);
		// 实体信息
		groupDB.addElement(Template.builder()
			.code(loadTemplateText("Entity_PO"))
			.name("Entity_PO")
			.build());
		// 扩展信息
		groupDB.addElement(Template.builder()
			.code(loadTemplateText("Entity_VO"))
			.name("Entity_VO")
			.build());
		// Mapper对象
		groupDB.addElement(Template.builder()
			.code(loadTemplateText("Mapper"))
			.name("Mapper")
			.build());
		// SQLBuilder对象
		groupDB.addElement(Template.builder()
			.code(loadTemplateText("SQLBuilder"))
			.name("SQLBuilder")
			.build());
		// DaoBase
		groupDB.addElement(Template.builder()
			.code(loadTemplateText("DaoBase"))
			.name("DaoBase")
			.build());
		// Dao
		groupDB.addElement(Template.builder()
			.code(loadTemplateText("Dao"))
			.name("Dao")
			.build());
		// DaoImpl
		groupDB.addElement(Template.builder()
			.code(loadTemplateText("DaoImpl"))
			.name("DaoImpl")
			.build());
		// 添加默认代码生成模板
		this.dbGroup.put(DEFAULT_NAME, groupDB);
		
		// 加载默认的代码生成模板
		GroupCode groupCode = new GroupCode();
		groupCode.setName(DEFAULT_NAME);
		// Builder 模式对象
		groupCode.addElement(Template.builder()
			.code(loadTemplateText("Builder"))
			.name("Builder")
			.build());
		// 添加默认数据库模板
		this.codeGroup.put(DEFAULT_NAME, groupCode);
	}
	
	private String loadTemplateText(String template) {
		try {
			String path = "/" + template + ".vm";
			URL url = getClass().getResource(path);
			return UrlUtil.loadText(url);
		} catch (IOException e) {
			throw hidden(e);
		}
	}
}
