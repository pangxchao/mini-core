/**
 * Created the com.cfinal.util.CFHashGenerator.java
 *
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package sn.mini.java.util;

import java.util.BitSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Geohash 与经度 纬度的转换工具 和 base32 的转换工具
 * @author XChao
 */
public class HashGenerator {
    private final static double R = 6371393.0;
    private static final int NUMBITS = 6 * 5;
    private final static Map<Character, Integer> LOOKUP = new ConcurrentHashMap<>();
    private final static char[] DIGITS = "0123456789bcdefghjkmnpqrstuvwxyz".toCharArray();
    private static final HashGenerator instence = new HashGenerator();

    public static HashGenerator getInstence() {
        return instence;
    }

    private HashGenerator() {
        for (int i = 0; i < DIGITS.length; i++) {
            LOOKUP.put(DIGITS[i], i);
        }
    }

    /**
     * 获取两经纬度距离
     * @param lng_a
     * @param lat_a
     * @param lng_b
     * @param lat_b
     * @return 距离单位 : 米
     */
    public double distance(double lng_a, double lat_a, double lng_b, double lat_b) {
        double lta = Math.toRadians(lat_a), ltb = Math.toRadians(lat_b);
        double lga = Math.toRadians(lng_a), lgb = Math.toRadians(lng_b);
        return R * Math.acos(Math.cos(lta) * Math.cos(ltb) * Math.cos(lgb - lga) + Math.sin(lta) * Math.sin(ltb));
    }

    /**
     * 把经度 纬度转换成 Geohash 码
     * @param lon 经度
     * @param lat 纬度
     * @return
     */
    public String encode(double lon, double lat) {
        BitSet latbits = getBits(lat, -90, 90);
        BitSet lonbits = getBits(lon, -180, 180);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < NUMBITS; i++) {
            buffer.append((lonbits.get(i)) ? '1' : '0');
            buffer.append((latbits.get(i)) ? '1' : '0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }

    /**
     * 把 Geohash 码转换成经度 纬度
     * @param geohash
     * @return double[0] 经度， double[1] 纬度
     */
    public double[] decode(String geohash) {
        StringBuilder buffer = new StringBuilder();
        for (char c : geohash.toCharArray()) {
            int i = LOOKUP.get(c) + 32;
            buffer.append(Integer.toString(i, 2).substring(1));
        }

        BitSet lonset = new BitSet();
        BitSet latset = new BitSet();

        int j = 0;
        for (int i = 0; i < NUMBITS * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            lonset.set(j++, isSet);
        }

        j = 0;
        for (int i = 1; i < NUMBITS * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            latset.set(j++, isSet);
        }

        double lon = decode(lonset, -180, 180);
        double lat = decode(latset, -90, 90);

        return new double[]{lon, lat};
    }

    private double decode(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        for (int i = 0; i < bs.length(); i++) {
            mid = (floor + ceiling) / 2;
            if (bs.get(i))
                floor = mid;
            else
                ceiling = mid;
        }
        return mid;
    }

    private BitSet getBits(double lat, double floor, double ceiling) {
        BitSet buffer = new BitSet(NUMBITS);
        for (int i = 0; i < NUMBITS; i++) {
            double mid = (floor + ceiling) / 2;
            if (lat >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }

    private static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative)
            i = -i;
        while (i <= -32) {
            buf[charPos--] = DIGITS[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = DIGITS[(int) (-i)];

        if (negative)
            buf[--charPos] = '-';
        return new String(buf, charPos, (65 - charPos));
    }
}
