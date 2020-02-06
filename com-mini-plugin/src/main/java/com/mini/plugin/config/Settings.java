package com.mini.plugin.config;

import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.intellij.openapi.components.ServiceManager.getService;
import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;
import static com.mini.plugin.util.ThrowsUtil.hidden;

/**
 * 设置信息类
 * @author xchao
 */
@State(name = "MiniCode", storages = @Storage("Mini-Code.xml"))
public final class Settings implements Serializable, PersistentStateComponent<Settings> {
	public static final String DEFAULT_NAME = "Default";
	private String mapperGroupName = DEFAULT_NAME;
	private String codeGroupName = DEFAULT_NAME;
	private String dbGroupName = DEFAULT_NAME;
	private Map<String, GroupMapper> mapperGroup;
	private Map<String, GroupCode> codeGroup;
	private Map<String, GroupDB> dbGroup;
	private String encoding = "UTF-8";
	private String author = "xchao";
	
	public synchronized void setMapperGroup(Map<String, GroupMapper> mapper) {
		Optional.ofNullable(mapper).ifPresent(m -> {
			this.getMapperGroup().clear();
			getMapperGroup().putAll(m);
		});
	}
	
	public synchronized void setCodeGroup(Map<String, GroupCode> code) {
		Optional.ofNullable(code).ifPresent(c -> {
			this.getCodeGroup().clear();
			getCodeGroup().putAll(c);
		});
	}
	
	public synchronized void setDbGroup(Map<String, GroupDB> db) {
		Optional.ofNullable(db).ifPresent(d -> {
			this.getDbGroup().clear();
			getDbGroup().putAll(db);
		});
	}
	
	public synchronized void setMapperGroupName(String groupName) {
		mapperGroupName = defaultIfEmpty(groupName, DEFAULT_NAME);
	}
	
	public synchronized void setCodeGroupName(String groupName) {
		codeGroupName = defaultIfEmpty(groupName, DEFAULT_NAME);
	}
	
	public synchronized void setDbGroupName(String groupName) {
		dbGroupName = defaultIfEmpty(groupName, DEFAULT_NAME);
	}
	
	public synchronized void setEncoding(String encoding) {
		this.encoding = defaultIfEmpty(encoding, "UTF-8");
	}
	
	public synchronized void setAuthor(String author) {
		this.author = defaultIfEmpty(author, "xchao");
	}
	
	@NotNull
	public synchronized Map<String, GroupMapper> getMapperGroup() {
		if (Settings.this.mapperGroup == null) {
			mapperGroup = new LinkedHashMap<>();
		}
		return mapperGroup;
	}
	
	@NotNull
	public synchronized Map<String, GroupCode> getCodeGroup() {
		if (Settings.this.codeGroup == null) {
			codeGroup = new LinkedHashMap<>();
		}
		return codeGroup;
	}
	
	@NotNull
	public synchronized Map<String, GroupDB> getDbGroup() {
		if (Settings.this.dbGroup == null) {
			dbGroup = new LinkedHashMap<>();
		}
		return dbGroup;
	}
	
	@NotNull
	public synchronized String getMapperGroupName() {
		if (StringUtil.isEmpty(mapperGroupName)) {
			mapperGroupName = DEFAULT_NAME;
		}
		return mapperGroupName;
	}
	
	@NotNull
	public synchronized String getCodeGroupName() {
		if (StringUtil.isEmpty(codeGroupName)) {
			codeGroupName = DEFAULT_NAME;
		}
		return codeGroupName;
	}
	
	@NotNull
	public synchronized String getDbGroupName() {
		if (StringUtil.isEmpty(dbGroupName)) {
			dbGroupName = DEFAULT_NAME;
		}
		return dbGroupName;
	}
	
	@NotNull
	public synchronized String getEncoding() {
		if (StringUtil.isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		return encoding;
	}
	
	@NotNull
	public synchronized String getAuthor() {
		if (StringUtil.isEmpty(author)) {
			author = "xchao";
		}
		return author;
	}
	
	public final void setCurrentGroupMapper(@NotNull GroupMapper mapper) {
		this.getMapperGroup().put(mapper.getName(), mapper);
		this.setMapperGroupName(mapper.getName());
	}
	
	public final void setCurrentGroupCode(@NotNull GroupCode code) {
		this.getCodeGroup().put(code.getName(), code);
		this.setCodeGroupName(code.getName());
	}
	
	public final void setCurrentGroupDB(@NotNull GroupDB db) {
		this.getDbGroup().put(db.getName(), db);
		this.setDbGroupName(db.getName());
	}
	
	@Nullable
	public final GroupMapper getGroupMapper(@NotNull String groupName) {
		return getMapperGroup().get(groupName);
	}
	
	@Nullable
	public final GroupCode getGroupCode(@NotNull String groupName) {
		return getCodeGroup().get(groupName);
	}
	
	@Nullable
	public final GroupDB getGroupDB(@NotNull String groupName) {
		return getDbGroup().get(groupName);
	}
	
	@Nullable
	public final GroupMapper getCurrentGroupMapper() {
		return getGroupMapper(getMapperGroupName());
	}
	
	@Nullable
	public final GroupCode getCurrentGroupCode() {
		return getGroupCode(getCodeGroupName());
	}
	
	@Nullable
	public final GroupDB getCurrentGroupDB() {
		return getGroupDB(getDbGroupName());
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
		if (!StringUtil.isEmpty(settings.getMapperGroupName())) {
			setMapperGroupName(settings.getMapperGroupName());
		}
		if (!StringUtil.isEmpty(settings.getCodeGroupName())) {
			setCodeGroupName(settings.getCodeGroupName());
		}
		if (!StringUtil.isEmpty(settings.getDbGroupName())) {
			setDbGroupName(settings.getDbGroupName());
		}
		if (!StringUtil.isEmpty(settings.getEncoding())) {
			setEncoding(settings.getEncoding());
		}
		if (!StringUtil.isEmpty(settings.getAuthor())) {
			setAuthor(settings.getAuthor());
		}
		if (!settings.getMapperGroup().isEmpty()) {
			setMapperGroup(settings.getMapperGroup());
		}
		if (!settings.getCodeGroup().isEmpty()) {
			setCodeGroup(settings.getCodeGroup());
		}
		if (!settings.getDbGroup().isEmpty()) {
			setDbGroup(settings.getDbGroup());
		}
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
		// 添加默认类型映射
		this.setCurrentGroupMapper(GroupMapper.builder()
			.name(Settings.DEFAULT_NAME)
			// LONGTEXT => String
			.element(TypeMapper.builder()
				.nullJavaType(String.class.getCanonicalName())
				.javaType(String.class.getCanonicalName())
				.databaseType("LONGTEXT")
				.build())
			// MEDIUMTEXT => String
			.element(TypeMapper.builder()
				.nullJavaType(String.class.getCanonicalName())
				.javaType(String.class.getCanonicalName())
				.databaseType("MEDIUMTEXT")
				.build())
			// TEXT => String
			.element(TypeMapper.builder()
				.nullJavaType(String.class.getCanonicalName())
				.javaType(String.class.getCanonicalName())
				.databaseType("TEXT")
				.build())
			// TINYTEXT => String
			.element(TypeMapper.builder()
				.nullJavaType(String.class.getCanonicalName())
				.javaType(String.class.getCanonicalName())
				.databaseType("TINYTEXT")
				.build())
			// VARCHAR => String
			.element(TypeMapper.builder()
				.nullJavaType(String.class.getCanonicalName())
				.javaType(String.class.getCanonicalName())
				.javaType(String.class.getCanonicalName())
				.databaseType("VARCHAR")
				.build())
			// CHAR => String
			.element(TypeMapper.builder()
				.nullJavaType(String.class.getCanonicalName())
				.javaType(String.class.getCanonicalName())
				.databaseType("CHAR")
				.build())
			// NUMERIC => BigDecimal
			.element(TypeMapper.builder()
				.nullJavaType(BigDecimal.class.getCanonicalName())
				.javaType(BigDecimal.class.getCanonicalName())
				.databaseType("NUMERIC")
				.build())
			// BIGINT => long
			.element(TypeMapper.builder()
				.nullJavaType(Long.class.getCanonicalName())
				.javaType(long.class.getCanonicalName())
				.databaseType("BIGINT")
				.build())
			// LONG => long
			.element(TypeMapper.builder()
				.nullJavaType(Long.class.getCanonicalName())
				.javaType(long.class.getCanonicalName())
				.databaseType("LONG")
				.build())
			// INT8 => long
			.element(TypeMapper.builder()
				.nullJavaType(Long.class.getCanonicalName())
				.javaType(long.class.getCanonicalName())
				.databaseType("INT8")
				.build())
			// MEDIUMINT => int
			.element(TypeMapper.builder()
				.nullJavaType(Integer.class.getCanonicalName())
				.javaType(Integer.class.getCanonicalName())
				.databaseType("MEDIUMINT")
				.build())
			// INTEGER => int
			.element(TypeMapper.builder()
				.nullJavaType(Integer.class.getCanonicalName())
				.javaType(Integer.class.getCanonicalName())
				.databaseType("INTEGER")
				.build())
			// INT4 => int
			.element(TypeMapper.builder()
				.nullJavaType(Integer.class.getCanonicalName())
				.javaType(int.class.getCanonicalName())
				.databaseType("INT4")
				.build())
			// INT => int
			.element(TypeMapper.builder()
				.nullJavaType(Integer.class.getCanonicalName())
				.javaType(int.class.getCanonicalName())
				.databaseType("INT")
				.build())
			// SMALLINT => int
			.element(TypeMapper.builder()
				.nullJavaType(Short.class.getCanonicalName())
				.javaType(short.class.getCanonicalName())
				.databaseType("SMALLINT")
				.build())
			// TINYINT => int
			.element(TypeMapper.builder()
				.nullJavaType(Byte.class.getCanonicalName())
				.javaType(byte.class.getCanonicalName())
				.databaseType("TINYINT")
				.build())
			// BOOLEAN => boolean
			.element(TypeMapper.builder()
				.nullJavaType(Boolean.class.getCanonicalName())
				.javaType(boolean.class.getCanonicalName())
				.databaseType("BOOLEAN")
				.build())
			// BOOL => boolean
			.element(TypeMapper.builder()
				.nullJavaType(Boolean.class.getCanonicalName())
				.javaType(boolean.class.getCanonicalName())
				.databaseType("BOOL")
				.build())
			// BIT => boolean
			.element(TypeMapper.builder()
				.nullJavaType(Boolean.class.getCanonicalName())
				.javaType(boolean.class.getCanonicalName())
				.databaseType("BIT")
				.build())
			// DECIMAL => double
			.element(TypeMapper.builder()
				.nullJavaType(Double.class.getCanonicalName())
				.javaType(double.class.getCanonicalName())
				.databaseType("DECIMAL")
				.build())
			// DOUBLE => double
			.element(TypeMapper.builder()
				.nullJavaType(Double.class.getCanonicalName())
				.javaType(double.class.getCanonicalName())
				.databaseType("DOUBLE")
				.build())
			// FLOAT => float
			.element(TypeMapper.builder()
				.nullJavaType(Float.class.getCanonicalName())
				.javaType(float.class.getCanonicalName())
				.databaseType("FLOAT")
				.build())
			// TIME => java.sql.Time
			.element(TypeMapper.builder()
				.nullJavaType(java.sql.Time.class.getCanonicalName())
				.javaType(java.sql.Time.class.getCanonicalName())
				.databaseType("TIME")
				.build())
			// DATE => java.util.Date
			.element(TypeMapper.builder()
				.nullJavaType(java.util.Date.class.getCanonicalName())
				.javaType(java.util.Date.class.getCanonicalName())
				.databaseType("DATE")
				.build())
			// DATETIME => java.util.Date
			.element(TypeMapper.builder()
				.nullJavaType(java.util.Date.class.getCanonicalName())
				.javaType(java.util.Date.class.getCanonicalName())
				.databaseType("DATETIME")
				.build())
			// TIMESTAMP => java.util.Date
			.element(TypeMapper.builder()
				.nullJavaType(java.util.Date.class.getCanonicalName())
				.javaType(java.util.Date.class.getCanonicalName())
				.databaseType("TIMESTAMP")
				.build())
			// LONGBLOB => Blob.
			.element(TypeMapper.builder()
				.javaType(Blob.class.getCanonicalName())
				.databaseType("LONGBLOB")
				.build())
			// MEDIUMBLOB => Blob.
			.element(TypeMapper.builder()
				.javaType(Blob.class.getCanonicalName())
				.databaseType("MEDIUMBLOB")
				.build())
			// BLOB => Blob.
			.element(TypeMapper.builder()
				.javaType(Blob.class.getCanonicalName())
				.databaseType("BLOB")
				.build())
			// TINYBLOB => Blob.
			.element(TypeMapper.builder()
				.javaType(Blob.class.getCanonicalName())
				.databaseType("TINYBLOB")
				.build())
			.build());
		
		// 加载默认的数据库模板
		this.setCurrentGroupDB(GroupDB.builder()
			.name(Settings.DEFAULT_NAME)
			// 实体信息
			.element(Template.builder()
				.code(loadTemplateText("Entity"))
				.name("Entity")
				.build())
			// 扩展信息
			.element(Template.builder()
				.code(loadTemplateText("EntityVO"))
				.name("EntityVO")
				.build())
			// DaoBase
			.element(Template.builder()
				.code(loadTemplateText("DaoBase"))
				.name("DaoBase")
				.build())
			// Dao
			.element(Template.builder()
				.code(loadTemplateText("Dao"))
				.name("Dao")
				.build())
			// DaoImpl
			.element(Template.builder()
				.code(loadTemplateText("DaoImpl"))
				.name("DaoImpl")
				.build())
			.build());
		
		// 加载默认的代码生成模板
		this.setCurrentGroupCode(GroupCode.builder()
			.name(Settings.DEFAULT_NAME)
			.element(Template.builder()
				.code(loadTemplateText("Builder"))
				.name("Builder")
				.build())
			.build());
		
		// 编码和作者
		this.setEncoding("UTF-8");
		this.setAuthor("xchao");
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
