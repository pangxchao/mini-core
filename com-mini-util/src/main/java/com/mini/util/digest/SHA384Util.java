/**
 * Created the com.cfinal.util.digest.SHA384Util.java
 * @created 2017年5月16日 下午4:31:08
 * @version 1.0.0
 */
package com.mini.util.digest;

import java.io.InputStream;

/**
 * com.cfinal.util.digest.SHA384Util.java
 * @author XChao
 */
public class SHA384Util {
    private static final DigestUtil instance = new DigestUtil("SHA-384");

    /**
     * SHA384加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(String data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA384加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(String data) {
        return instance.encode(data);
    }

    /**
     * SHA384加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(byte[] data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA384加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(byte[] data) {
        return instance.encode(data);
    }

    /**
     * SHA384加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(InputStream data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA384加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(InputStream data) {
        return instance.encode(data);
    }

}
