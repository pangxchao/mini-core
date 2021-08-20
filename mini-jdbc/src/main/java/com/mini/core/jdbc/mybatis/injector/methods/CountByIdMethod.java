package com.mini.core.jdbc.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import static java.lang.String.format;

public class CountByIdMethod extends AbstractMethod {
    private static final String METHOD = "countById";

    @Override
    @SuppressWarnings({"StringBufferReplaceableByString", "DuplicatedCode", "RedundantSuppression", "StringConcatenationInsideStringBufferAppend"})
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 构建SQL脚本
        StringBuilder builder = new StringBuilder("<script>\nSELECT COUNT(*) FROM ");
        builder.append(tableInfo.getTableName());
        builder.append(format("\nWHERE %s = #{%s}", tableInfo.getKeyColumn(), tableInfo.getKeyProperty()));
        builder.append(tableInfo.getLogicDeleteSql(true, true));
        builder.append("\n</script>");

        // 流入SQL脚本
        String sql = builder.toString();
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addSelectMappedStatementForOther(mapperClass, METHOD, sqlSource, int.class);
    }
}
