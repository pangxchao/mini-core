
package com.mini.core.util.security.digest;

import java.nio.charset.Charset;

public final class SHA1 extends BaseDigest {
    public SHA1() {
        super("SHA-1");
    }

    public static String encode(String data, Charset charset) {
        return new SHA1().update(data, charset).encode();
    }

    public static String encode(String data) {
        return new SHA1().update(data).encode();
    }
}