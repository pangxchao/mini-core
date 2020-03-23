package com.mini.core.security.digest;

import java.nio.charset.Charset;

public final class SHA512 extends BaseDigest {
	public SHA512() {
		super("SHA-512");
	}
	
	public static SHA512 getInstance() {
		return new SHA512();
	}
	
	public static String encode(String data, Charset charset) {
		return getInstance().update(data, charset).encode();
	}
	
	public static String encode(String data) {
		return getInstance().update(data).encode();
	}
}
