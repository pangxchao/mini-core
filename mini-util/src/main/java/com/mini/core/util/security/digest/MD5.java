package com.mini.core.util.security.digest;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;

public final class MD5 extends BaseDigest {
    public MD5() {
        super("MD5");
    }

    public static String encode(String data, Charset charset) {
        final var v = new MD5().update(data, charset);
        return toHexString(v.digest());
    }

    public static String encode(@Nonnull String data) {
        final var v = new MD5().update(data);
        return toHexString(v.digest());
    }
}