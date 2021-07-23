package com.mini.core.mybatis.injector.methods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import static com.baomidou.mybatisplus.core.metadata.TableInfoHelper.genKeyGenerator;
import static com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils.convertTrim;

public class SaveMethod extends AbstractMethod {
    private static final String METHOD = "save";

    @Override
    @SuppressWarnings({"StringBufferReplaceableByString", "DuplicatedCode", "RedundantSuppression"})
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 构建SQL脚本
        StringBuilder builder = new StringBuilder("<script>\nINSERT INTO ");
        builder.append(tableInfo.getTableName());
        builder.append(convertTrim(tableInfo.getAllInsertSqlColumnMaybeIf(null), LEFT_BRACKET, RIGHT_BRACKET, null, COMMA));
        builder.append("\nVALUES ");
        builder.append(convertTrim(tableInfo.getAllInsertSqlPropertyMaybeIf(null), LEFT_BRACKET, RIGHT_BRACKET, null, COMMA));
        builder.append("\nON DUPLICATE KEY UPDATE ");
        builder.append(convertTrim(tableInfo.getAllSqlSet(tableInfo.isWithLogicDelete(), ""), null, null, null, COMMA));
        builder.append("\n</script>");

        // 处理主键生成
        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        String keyProperty = null, keyColumn = null;
        if (StringUtils.isNotBlank(tableInfo.getKeyProperty())) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                keyGenerator = Jdbc3KeyGenerator.INSTANCE;
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            } else if (null != tableInfo.getKeySequence()) {
                keyGenerator = genKeyGenerator(METHOD, tableInfo, builderAssistant);
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            }
        }

        // 流入SQL脚本
        String sql = builder.toString();
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addInsertMappedStatement(mapperClass, modelClass, METHOD, sqlSource, keyGenerator, keyProperty, keyColumn);
    }
}
