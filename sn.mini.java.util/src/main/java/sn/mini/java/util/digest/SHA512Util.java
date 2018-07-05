/**
 * Created the com.cfinal.util.digest.CFSHA512.java
 *
 * @created 2017年5月16日 下午4:31:08
 * @version 1.0.0
 */
package sn.mini.java.util.digest;

import java.io.InputStream;

/**
 * com.cfinal.util.digest.CFSHA512.java
 * @author XChao
 */
public class SHA512Util {
    private static final DigestUtil instance = new DigestUtil("SHA-512");

    /**
     * SHA512加密
     * @param data
     * @return
     */
    public static byte[] encodeToByte(String data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    public static String encode(String data) {
        return instance.encode(data);
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    public static byte[] encodeToByte(byte[] data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        return instance.encode(data);
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    public static byte[] encodeToByte(InputStream data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    public static String encode(InputStream data) {
        return instance.encode(data);
    }

}
