package com.mini.core.security.digest;

import java.nio.charset.Charset;

public final class SHA1 extends BaseDigest {
	public SHA1() {
		super("SHA-1");
	}
	
	public static SHA1 getInstance() {
		return new SHA1();
	}
	
	public static String encode(String data, Charset charset) {
		return getInstance().update(data, charset).encode();
	}
	
	public static String encode(String data) {
		return getInstance().update(data).encode();
	}
}
