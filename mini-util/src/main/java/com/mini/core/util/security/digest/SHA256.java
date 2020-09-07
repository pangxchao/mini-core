package com.mini.core.util.security.digest;

import java.nio.charset.Charset;

public final class SHA256 extends BaseDigest {
    public SHA256() {
        super("SHA-256");
    }

    public static String encode(String data, Charset charset) {
        return new SHA256().update(data, charset).encode();
    }

    public static String encode(String data) {
        return new SHA256().update(data).encode();
    }
}