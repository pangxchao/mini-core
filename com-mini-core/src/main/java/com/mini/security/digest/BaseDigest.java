package com.mini.security.digest;

import com.mini.security.SecurityBase;

import java.security.MessageDigest;

import static java.security.MessageDigest.getInstance;

/**
 * DigestUtil.java
 * @author xchao
 */
public abstract class BaseDigest extends SecurityBase {
    private final MessageDigest digest;

    @SuppressWarnings("WeakerAccess")
    protected BaseDigest(MessageDigest digest) {
        this.digest = digest;
    }

    @SuppressWarnings("WeakerAccess")
    protected BaseDigest(String algorithm) throws Exception {
        this(getInstance(algorithm));
    }

    public final BaseDigest update(byte[] input, int offset, int len) {
        digest.update(input, offset, len);
        return this;
    }

    public final BaseDigest update(byte[] input) {
        digest.update(input);
        return this;
    }

    public final BaseDigest update(byte input) {
        digest.update(input);
        return this;
    }

    public final byte[] digest(byte[] input) {
        return digest.digest(input);
    }

    public final byte[] digest() {
        return digest.digest();
    }

    public final String toString() {
        return digest.toString();
    }

    public final void reset() {
        digest.reset();
    }
}
