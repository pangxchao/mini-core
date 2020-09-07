package com.mini.core.util.security.digest;

import java.nio.charset.Charset;

public final class MD5 extends BaseDigest {
    public MD5() {
        super("MD5");
    }

    public static String encode(String data, Charset charset) {
        return new MD5().update(data, charset).encode();
    }

    public static String encode(String data) {
        return new MD5().update(data).encode();
    }
}