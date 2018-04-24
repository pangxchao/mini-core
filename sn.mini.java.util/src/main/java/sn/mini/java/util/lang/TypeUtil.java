package sn.mini.java.util.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * sn.mini.util.converter.TypeUtil.java
 *
 * @author XChao
 */
public  final class TypeUtil {
    private TypeUtil() { // 无法new当前类
    }


    public static long castToLongValue(String value) {
        return StringUtil.isBlank(value) ? 0 : Long.valueOf(value);
    }

    public static Long castToLong(String value) {
        return StringUtil.isBlank(value) ? null : Long.valueOf(value);
    }

    public static int castToIntValue(String value) {
        return StringUtil.isBlank(value) ? 0 : Integer.valueOf(value);
    }

    public static Integer castToInt(String value) {
        return StringUtil.isBlank(value) ? null : Integer.valueOf(value);
    }

    public static short castToShortValue(String value) {
        return StringUtil.isBlank(value) ? 0 : Short.valueOf(value);
    }

    public static Short castToShort(String value) {
        return StringUtil.isBlank(value) ? null : Short.valueOf(value);
    }

    public static byte castToByteValue(String value) {
        return StringUtil.isBlank(value) ? 0 : Byte.valueOf(value);
    }

    public static Byte castToByte(String value) {
        return StringUtil.isBlank(value) ? null : Byte.valueOf(value);
    }

    public static double castToDoubleValue(String value) {
        return StringUtil.isBlank(value) ? 0 : Double.valueOf(value);
    }

    public static Double castToDouble(String value) {
        return StringUtil.isBlank(value) ? null : Double.valueOf(value);
    }

    public static float castToFloatValue(String value) {
        return StringUtil.isBlank(value) ? 0f : Float.valueOf(value);
    }

    public static Float castToFloat(String value) {
        return StringUtil.isBlank(value) ? null : Float.valueOf(value);
    }

    public static boolean castToBoolValue(String value) {
        return StringUtil.isBlank(value) ? false : Boolean.valueOf(value);
    }

    public static Boolean castToBool(String value) {
        return StringUtil.isBlank(value) ? null : Boolean.valueOf(value);
    }

    public static Date castToDate(String value) {
        if (StringUtil.isBlank(value)) {
            return null;
        }
        for (DateFormat format : formats)
            try {
                return format.parse(value);
            } catch (Exception ignored) {
            }
        throw new RuntimeException("Unparseable date: \"" + value + "\"");
    }

    private static DateFormat[] formats = new DateFormat[]{ //
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"), //
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), //
            new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"), //
            new SimpleDateFormat("yyyy/dd/MM HH:mm:ss"), //
            new SimpleDateFormat("yy-M-d H:m:s"), //
            new SimpleDateFormat("yy/M/d H:m:s"), //
            new SimpleDateFormat("yy年M月d日H:m:s"), //
            new SimpleDateFormat("yy/d/M H:m:s"), //
            new SimpleDateFormat("yyyy-MM-dd'Y'HH:mm:ss'Z'"), //
            new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒"), //
            new SimpleDateFormat("yyyy-MM-dd"), //
            new SimpleDateFormat("yyyy年MM月dd日"), //
            new SimpleDateFormat("yyyy/MM/dd"), //
            new SimpleDateFormat("yyyy/dd/MM"), //
            new SimpleDateFormat("yy-M-d"), //
            new SimpleDateFormat("yy/M/d"), //
            new SimpleDateFormat("yy年M月d日"), //
            new SimpleDateFormat("HH:mm:ss.SSS"), //
            new SimpleDateFormat("H:m:s"), //
            new SimpleDateFormat("H时m分s秒") //
    };
}
