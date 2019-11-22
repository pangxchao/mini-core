package com.mini.core.security.digest;

import java.nio.charset.Charset;

public final class MD5 extends BaseDigest {
    public MD5() throws Exception {
        super("MD5");
    }

    public static MD5 getInstance() throws Exception {
        return new MD5();
    }

    public static String encode(String data, Charset charset) throws Exception {
        return getInstance().update(data, charset).encode();
    }

    public static String encode(String data) throws Exception {
        return getInstance().update(data).encode();
    }
}
