package sn.mini.java.util.digest;

import java.io.InputStream;

/**
 * com.cfinal.util.digest.SHA256Util.java
 *
 * @author XChao
 */
public class SHA256Util {
    private static final DigestUtil instance = new DigestUtil("SHA-256");


    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    public static byte[] encodeToByte(String data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    public static String encode(String data) {
        return instance.encode(data);
    }

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    public static byte[] encodeToByte(byte[] data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        return instance.encode(data);
    }

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    public static byte[] encodeToByte(InputStream data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    public static String encode(InputStream data) {
        return instance.encode(data);
    }

}
