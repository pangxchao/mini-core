package com.mini.core.security.digest;

import java.nio.charset.Charset;

public final class SHA256 extends BaseDigest {
	public SHA256() {
		super("SHA-256");
	}
	
	public static SHA256 getInstance() {
		return new SHA256();
	}
	
	public static String encode(String data, Charset charset) {
		return getInstance().update(data, charset).encode();
	}
	
	public static String encode(String data) {
		return getInstance().update(data).encode();
	}
}
