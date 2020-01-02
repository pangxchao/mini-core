package com.mini.core.security.digest;

import java.nio.charset.Charset;

public final class SHA1 extends BaseDigest {
	public SHA1() throws Exception {
		super("SHA-1");
	}

	public static SHA1 getInstance() throws Exception {
		return new SHA1();
	}

	public static String encode(String data, Charset charset) throws Exception {
		return getInstance().update(data, charset).encode();
	}

	public static String encode(String data) throws Exception {
		return getInstance().update(data).encode();
	}
}
