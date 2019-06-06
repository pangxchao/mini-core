package com.mini.util.lang.scan;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Set;

public interface ClassScanner {

    /**
     * 扫描指定包下的所有Class对象
     * @param packageName 指定包名
     * @param annotation  指定注解
     * @return 扫描结果
     */
    Set<Class<?>> scanner(String packageName, Class<? extends Annotation> annotation);


    /**
     * 将包名转换成包路径
     * @param packageName 包名
     */
    @Nonnull
    static String packageNameToFilePath(@Nonnull String packageName) {
        return packageName.replaceAll("[.]", "/");
    }
}
