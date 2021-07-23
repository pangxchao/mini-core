package com.mini.core.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import static java.lang.String.format;

public class RemoveMethod extends AbstractMethod {
    private static final String METHOD = "remove";

    @Override
    @SuppressWarnings({"StringBufferReplaceableByString", "DuplicatedCode", "RedundantSuppression", "StringConcatenationInsideStringBufferAppend"})
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // String sql;
        // if (tableInfo.isWithLogicDelete()) {
        //     // LOGIC_DELETE("delete", "根据 entity 条件逻辑删除记录", "<script>\nUPDATE %s %s %s %s\n</script>"),
        //     SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE;
        //     sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo),
        //             sqlWhereEntityWrapper(true, tableInfo),
        //             sqlComment());
        //     SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        //     return addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
        // } else {
        //     //  DELETE("delete", "根据 entity 条件删除记录", "<script>\nDELETE FROM %s %s %s\n</script>"),
        //     SqlMethod sqlMethod = SqlMethod.DELETE;
        //     sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
        //             sqlWhereEntityWrapper(true, tableInfo),
        //             sqlComment());
        //     SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        //     return this.addDeleteMappedStatement(mapperClass, getMethod(sqlMethod), sqlSource);
        // }
        if (tableInfo.isWithLogicDelete()) {
            // 构建SQL脚本
            StringBuilder builder = new StringBuilder("<script>\nUPDATE ");
            builder.append(tableInfo.getTableName());
            builder.append(sqlLogicSet(tableInfo));
            builder.append(format("\nWHERE %s = #{%s} ", tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty()));
            builder.append(tableInfo.getLogicDeleteSql(true, true));
            builder.append("\n</script>");

            // 流入SQL脚本
            String sql = builder.toString();
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return addUpdateMappedStatement(mapperClass, modelClass, METHOD, sqlSource);
        }

        // 构建SQL脚本
        StringBuilder builder = new StringBuilder("<script>\nDELETE FROM ");
        builder.append(tableInfo.getTableName());
        builder.append(format("\nWHERE %s = #{%s} ", tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty()));
        builder.append("\n</script>");

        // 流入SQL脚本
        String sql = builder.toString();
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addDeleteMappedStatement(mapperClass, METHOD, sqlSource);
    }
}
