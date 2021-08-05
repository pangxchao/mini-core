package com.mini.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

/**
 * Mini String Utils 工具类
 * <p>
 * 正则表达式匹配规则
 * </p>
 * <ul>
 *     <li>*: 0 次或者多次</li>
 *     <li>+: 一次或者多次</li>
 *     <li>+: 一次或者多次</li>
 *     <li>?: 0次或者1次</li>
 *     <li>{n}:刚刚n次</li>
 *     <li>{n,m}: n到m次</li>
 * </ul>
 * <p>正则表达式快速匹配</p>
 * <ul>
 *     <li>\d: [0-9]</li>
 *     <li>\D: [^0-9]</li>
 *     <li>\w:[a-zA-Z_0-9]</li>
 *     <li>\W:[^a-zA-Z_0-9]</li>
 *     <li>\s: [\t\n\r\f]</li>
 *     <li>\S: [^\t\n\r\f]</li>
 * </ul>
 *
 * @author pangchao
 */
public final class StringUtil extends StringUtils {
    /**
     * 中国大陆身份证号正则表达式
     */
    public static final String ID_CARD_REGEX = "^\\d{15}(\\d{2}[A-Za-z0-9])?$";

    /**
     * 能用邮件地址正则表达式
     */
    public static final String EMAIL_REGEX = "^\\S+[@]\\S+[.]\\S+$";

    /**
     * 中文正则表达式
     */
    public static final String CHINESE_REGEX = "^[\u4E00-\u9FA5]+$";

    /**
     * 一般用户名正则表达式
     */
    public static final String REQUIRE_REGEX = "^[a-z_][a-z0-9_]*$";

    /**
     * 纯数字正则表达式
     */
    public static final String NUMBER_REGEX = "^\\d+$";

    /**
     * 纯英文正则表达式
     */
    public static final String LETTER_REGEX = "^\\w+$";

    /**
     * 将byte数组转换成base64的字符串编码
     *
     * @param bytes byte数组
     * @return 转换结果
     */
    public static String base64Encoder(byte[] bytes) {
        Base64.Encoder code = Base64.getEncoder();
        return code.encodeToString(bytes);
    }

    /**
     * 私有构造方法
     */
    private StringUtil() {
    }
}
