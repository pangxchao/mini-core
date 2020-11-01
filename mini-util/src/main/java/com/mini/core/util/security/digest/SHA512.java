package com.mini.core.util.security.digest;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;

public final class SHA512 extends BaseDigest {
    public SHA512() {
        super("SHA-512");
    }

    public static String encode(String data, Charset charset) {
        final var v = new SHA512().update(data, charset);
        return toHexString(v.digest());
    }

    public static String encode(@Nonnull String data) {
        final var v = new SHA512().update(data);
        return toHexString(v.digest());
    }
}