package com.mini.core.util.security.digest;

import java.nio.charset.Charset;

public final class SHA512 extends BaseDigest {
    public SHA512() {
        super("SHA-512");
    }

    public static String encode(String data, Charset charset) {
        return new SHA512().update(data, charset).encode();
    }

    public static String encode(String data) {
        return new SHA512().update(data).encode();
    }
}