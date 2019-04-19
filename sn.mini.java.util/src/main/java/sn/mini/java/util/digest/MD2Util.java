package sn.mini.java.util.digest;

import java.io.InputStream;

public final class MD2Util {

    private static final DigestUtil instance = new DigestUtil("MD2");

    /**
     * MD2加密
     * @param data
     * @return
     */
    public static byte[] encodeToByte(String data) {
        return instance.encodeToByte(data);
    }

    /**
     * MD2加密
     * @param data
     * @return
     */
    public static String encode(String data) {
        return instance.encode(data);
    }

    /**
     * MD2加密
     * @param data
     * @return
     */
    public static byte[] encodeToByte(byte[] data) {
        return instance.encodeToByte(data);
    }

    /**
     * MD2加密
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        return instance.encode(data);
    }

    /**
     * MD2加密
     * @param data
     * @return
     */
    public static byte[] encodeToByte(InputStream data) {
        return instance.encodeToByte(data);
    }

    /**
     * MD2加密
     * @param data
     * @return
     */
    public static String encode(InputStream data) {
        return instance.encode(data);
    }

}
