package com.mini.core.security.digest;

import java.nio.charset.Charset;

public final class SHA512 extends BaseDigest {
	public SHA512() throws Exception {
		super("SHA-512");
	}

	public static SHA512 getInstance() throws Exception {
		return new SHA512();
	}

	public static String encode(String data, Charset charset) throws Exception {
		return getInstance().update(data, charset).encode();
	}

	public static String encode(String data) throws Exception {
		return getInstance().update(data).encode();
	}
}
