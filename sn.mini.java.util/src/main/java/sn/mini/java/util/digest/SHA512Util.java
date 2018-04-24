/**
 * Created the com.cfinal.util.digest.CFSHA512.java
 * @created 2017年5月16日 下午4:31:08
 * @version 1.0.0
 */
package sn.mini.java.util.digest;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * com.cfinal.util.digest.CFSHA512.java
 * @author XChao
 */
public class SHA512Util {
	/** 用来将字节转换成 16 进制表示的字符 */
	private static final char encode_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
		'E', 'F' };

	/**
	 * 获取SHA512加密文摘
	 * @return
	 */
	private static MessageDigest getDigest() {
		try {
			return MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将byte数组转换成String
	 * @param bytes
	 * @return
	 */
	private static String toString(byte[] bytes) {
		// 每个字节用 16 进制表示的话，使用两个字符
		char result[] = new char[16 * 2];
		// 从第一个字节开始，对每一个字节,转换成 16 进制字符的转换
		for (int i = 0, k = 0; i < 16; i++) {
			// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
			result[k++] = encode_DIGITS[bytes[i] >>> 4 & 0xf];
			// 取字节中低 4 位的数字转换
			result[k++] = encode_DIGITS[bytes[i] & 0xf];
		}
		// 换后的结果转换为字符串
		return new String(result);
	}

	/**
	 * SHA512加密
	 * @param data
	 * @return
	 */
	public static byte[] encodeToByte(String data) {
		return getDigest().digest(data.getBytes());
	}

	/**
	 * SHA512加密
	 * @param data
	 * @return
	 */
	public static String encode(String data) {
		return toString(encodeToByte(data));
	}

	/**
	 * SHA512加密
	 * @param data
	 * @return
	 */
	public static byte[] encodeToByte(byte[] data) {
		return getDigest().digest(data);
	}

	/**
	 * SHA512加密
	 * @param data
	 * @return
	 */
	public static String encode(byte[] data) {
		return toString(encodeToByte(data));
	}

	/**
	 * SHA512加密
	 * @param data
	 * @return
	 */
	public static byte[] encodeToByte(InputStream data) {
		try {
			MessageDigest digest = getDigest();
			byte[] buffer = new byte[1024];
			for (int read = data.read(buffer, 0, 1024); read > -1;) {
				digest.update(buffer, 0, read);
				read = data.read(buffer, 0, 1024);
			}
			return digest.digest();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * SHA512加密
	 * @param data
	 * @return
	 */
	public static String encode(InputStream data) {
		return toString(encodeToByte(data));
	}

}
