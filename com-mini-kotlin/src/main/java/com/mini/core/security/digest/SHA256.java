package com.mini.core.security.digest;

import java.nio.charset.Charset;

public final class SHA256 extends BaseDigest {
    public SHA256() throws Exception {
        super("SHA-256");
    }

    public static SHA256 getInstance() throws Exception {
        return new SHA256();
    }

    public static String encode(String data, Charset charset) throws Exception {
        return getInstance().update(data, charset).encode();
    }

    public static String encode(String data) throws Exception {
        return getInstance().update(data).encode();
    }
}
