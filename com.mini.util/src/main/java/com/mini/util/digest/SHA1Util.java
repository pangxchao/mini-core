/**
 * Created the com.cfinal.util.digest.SHA1Util.java
 *
 * @created 2017年5月16日 下午4:31:08
 * @version 1.0.0
 */
package com.mini.util.digest;

import java.io.InputStream;

/**
 * com.cfinal.util.digest.SHA1Util.java
 *
 * @author XChao
 */
public class SHA1Util {
    private static final DigestUtil instance = new DigestUtil("SHA-1");

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    public static byte[] encodeToByte(String data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    public static String encode(String data) {
        return instance.encode(data);
    }

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    public static byte[] encodeToByte(byte[] data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        return instance.encode(data);
    }

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    public static byte[] encodeToByte(InputStream data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    public static String encode(InputStream data) {
        return instance.encode(data);
    }

}
