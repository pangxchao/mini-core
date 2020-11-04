package com.mini.plugin.config;

import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.mini.plugin.state.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import static java.util.Objects.requireNonNull;


@State(name = "Mini-Code", storages = @Storage("Mini-Code.xml"))
public class Settings implements PersistentStateComponent<Settings>, Serializable {
    public static final Settings instance = ServiceManager.getService(Settings.class);

    private static String load(String template) throws RuntimeException {
        try {
            final ClassLoader loader = Settings.class.getClassLoader();
            URL url = loader.getResource("/" + template + ".groovy");
            return UrlUtil.loadText(requireNonNull(url));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static final String DEFAULT_NAME = "Default";
    private DataTypeGroupMap dataTypeGroupMap;
    private TemplateGroupMap templateGroupMap;
    private DbTableGroupMap dbTableGroupMap;

    private String dataTypeGroupName;
    private String templateGroupName;
    private String encoding = "UTF-8";
    private String author = "xchao";

    public void setDataTypeGroupMap(DataTypeGroupMap dataTypeGroupMap) {
        this.dataTypeGroupMap = dataTypeGroupMap;
    }

    public void setTemplateGroupMap(TemplateGroupMap templateGroupMap) {
        this.templateGroupMap = templateGroupMap;
    }

    public void setDbTableGroupMap(DbTableGroupMap dbTableGroupMap) {
        this.dbTableGroupMap = dbTableGroupMap;
    }

    public void setDataTypeGroupName(String dataTypeGroupName) {
        this.dataTypeGroupName = dataTypeGroupName;
    }

    public void setTemplateGroupName(String templateGroupName) {
        this.templateGroupName = templateGroupName;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @NotNull
    public final DataTypeGroupMap getDataTypeGroupMap() {
        if (Settings.this.dataTypeGroupMap == null) {
            dataTypeGroupMap = new DataTypeGroupMap();
        }
        return dataTypeGroupMap;
    }

    @NotNull
    public final TemplateGroupMap getTemplateGroupMap() {
        if (Settings.this.templateGroupMap == null) {
            templateGroupMap = new TemplateGroupMap();
        }
        return templateGroupMap;
    }

    @NotNull
    public final DbTableGroupMap getDbTableGroupMap() {
        if (Settings.this.dbTableGroupMap == null) {
            dbTableGroupMap = new DbTableGroupMap();
        }
        return dbTableGroupMap;
    }

    @NotNull
    public final String getDataTypeGroupName() {
        if (dataTypeGroupName == null) {
            return DEFAULT_NAME;
        }
        return dataTypeGroupName;
    }

    @NotNull
    public String getTemplateGroupName() {
        if (templateGroupName == null) {
            return DEFAULT_NAME;
        }
        return templateGroupName;
    }

    @NotNull
    public final String getEncoding() {
        if (encoding == null) {
            return "UTF-8";
        }
        return encoding;
    }


    @NotNull
    public final String getAuthor() {
        if (author == null) {
            return "xchao";
        }
        return author;
    }

    @NotNull
    public final Settings copy() {
        Settings state = new Settings();
        state.setDataTypeGroupMap(getDataTypeGroupMap().copy());
        state.setTemplateGroupMap(getTemplateGroupMap().copy());
        state.setDbTableGroupMap(getDbTableGroupMap().copy());

        state.setDataTypeGroupName(getDataTypeGroupName());
        state.setTemplateGroupName(getTemplateGroupName());
        state.setEncoding(getEncoding());
        state.setAuthor(getAuthor());

        return state;
    }

    public final void resetDateType(@NotNull Settings state) {
        setDataTypeGroupMap(state.getDataTypeGroupMap().copy());
        setDataTypeGroupName(state.getDataTypeGroupName());
    }

    public final void resetTemplate(@NotNull Settings state) {
        setTemplateGroupMap(state.getTemplateGroupMap().copy());
        setTemplateGroupName(state.getTemplateGroupName());
    }

    public final void resetEncodingAndAuthor(@NotNull Settings state) {
        setEncoding(state.getEncoding());
        setAuthor(state.getAuthor());
    }

    public final void reset(@NotNull Settings state) {
        setDataTypeGroupName(state.getDataTypeGroupName());
        setTemplateGroupName(state.getTemplateGroupName());
        setDataTypeGroupMap(state.getDataTypeGroupMap());
        setTemplateGroupMap(state.getTemplateGroupMap());
        setEncoding(state.getEncoding());
        setAuthor(state.getAuthor());
    }

    public Settings() {
        // 数据类型映射
        getDataTypeGroupMap().add(new DataTypeGroup(DEFAULT_NAME) {{
            // 字符串类型
            add(new DataType("MEDIUMTEXT", "String"));
            add(new DataType("LONGTEXT", "String"));
            add(new DataType("TINYTEXT", "String"));
            add(new DataType("VARCHAR", "String"));
            add(new DataType("TEXT", "String"));
            add(new DataType("CHAR", "String"));
            // 数字类型
            add(new DataType("NUMERIC", "java.math.BigDecimal"));
            add(new DataType("MEDIUMINT", "Integer"));
            add(new DataType("INTEGER", "Integer"));
            add(new DataType("SMALLINT", "Short"));
            add(new DataType("DECIMAL", "Double"));
            add(new DataType("DOUBLE", "Double"));
            add(new DataType("INT4", "Integer"));
            add(new DataType("TINYINT", "Byte"));
            add(new DataType("FLOAT", "Float"));
            add(new DataType("BIGINT", "Long"));
            add(new DataType("INT", "Integer"));
            add(new DataType("LONG", "Long"));
            add(new DataType("INT8", "Long"));
            // 布尔值类型
            add(new DataType("BOOLEAN", "Boolean"));
            add(new DataType("BOOL", "Boolean"));
            add(new DataType("BIT", "Boolean"));
            // 日期和时间类型
            add(new DataType("TIMESTAMP", "java.util.Date"));
            add(new DataType("DATETIME", "java.util.Date"));
            add(new DataType("DATE", "java.util.Date"));
            add(new DataType("TIME", "java.sql.Time"));
            // 大数据类型/文件类型
            add(new DataType("MEDIUMBLOB", "java.sql.Blob"));
            add(new DataType("TINYBLOB", "java.sql.Blob"));
            add(new DataType("LONGBLOB", "java.sql.Blob"));
            add(new DataType("BLOB", "java.sql.Blob"));
        }});

        //   Default 模板
        getTemplateGroupMap().add(new TemplateGroup("JavaDefault") {{
            add(new Template("Entity", load("Java-Default")));
        }});

        // Java Jdbc 模板
        getTemplateGroupMap().add(new TemplateGroup("JavaJdbc") {{
            add(new Template("Repository", load("Java-Jdbc-Repository")));
            add(new Template("Entity", load("Java-Jdbc-Entity")));
        }});

        // Java Jpa 模板
        getTemplateGroupMap().add(new TemplateGroup("JavaJpa") {{
            add(new Template("Repository", load("Java-Jpa-Repository")));
            add(new Template("Entity", load("Java-Jpa-Entity")));
        }});

        //  Kotlin Default 模板
        getTemplateGroupMap().add(new TemplateGroup("KotlinDefault") {{
            add(new Template("Entity", load("Kotlin-Default")));
        }});

        // Kotlin Jdbc 模板
        getTemplateGroupMap().add(new TemplateGroup("KotlinJdbc") {{
            add(new Template("Repository", load("Kotlin-Jdbc-Repository")));
            add(new Template("Entity", load("Kotlin-Jdbc-Entity")));
        }});

        // Kotlin Jpa 模板
        getTemplateGroupMap().add(new TemplateGroup("KotlinJpa") {{
            add(new Template("Repository", load("Kotlin-Jpa-Repository")));
            add(new Template("Entity", load("Kotlin-Jpa-Entity")));
        }});
    }

    @NotNull
    @Override
    public Settings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull Settings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}