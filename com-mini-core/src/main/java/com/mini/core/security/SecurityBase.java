package com.mini.core.security;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.EventListener;

public abstract class SecurityBase<T extends SecurityBase<T>> implements EventListener {
	// 用来将字节转换成 16 进制表示的字符
	private static final char[] DIGITS = {   //
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' //
	};

	public abstract T update(byte[] input, int offset, int len);

	public abstract T update(byte[] input);

	public abstract T update(byte input);

	public final T update(String input, Charset charset) {
		return update(input.getBytes(charset));
	}

	public final T update(String input) {
		return update(input.getBytes());
	}

	public abstract byte[] digest(byte[] input);

	public abstract byte[] digest();

	public final byte[] digest(String data, Charset charset) {
		return digest(data.getBytes(charset));
	}

	public final byte[] digest(String data) {
		return digest(data.getBytes());
	}

	public final String encode() {
		byte[] b = this.digest();
		return toHexString(b);
	}

	public final String base64() {
		byte[] bytes = this.digest();
		var code = Base64.getEncoder();
		return code.encodeToString(bytes);
	}

	/** 将byte数组转换成String */
	private static String toHexString(byte[] bytes) {
		char[] result = new char[bytes.length * 2];
		for (int i = 0, n; i < bytes.length; i++) {
			//Integer.toHexString(bytes[i]&0xff);
			int j = (bytes[i] >>> 4) & 0xf;
			result[n = (i * 2)] = DIGITS[j];
			// 不足两位补0
			int k = bytes[i] & 0xf;
			result[n + 1] = DIGITS[k];
		}
		// 换后的结果转换为字符串
		return new String(result);
	}
}
