package com.mini.core.util.security.crypto;

import com.mini.core.util.security.SecurityBase;

import javax.crypto.Mac;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import static com.mini.core.util.ThrowableKt.hidden;
import static javax.crypto.Mac.getInstance;

public abstract class BaseMac extends SecurityBase<BaseMac> {
    private final String algorithm;
    private final Mac mac;

    public BaseMac(String algorithm) {
        try {
            mac = getInstance(algorithm);
            this.algorithm = algorithm;
        } catch (NoSuchAlgorithmException e) {
            throw hidden(e);
        }
    }

    public final BaseMac init(byte[] input, int offset, int len, AlgorithmParameterSpec params) {
        try {
            mac.init(new SecretKeySpec(input, offset, len, algorithm), params);
            return this;
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw hidden(e);
        }
    }

    public final BaseMac init(byte[] input, int offset, int len) {
        try {
            mac.init(new SecretKeySpec(input, offset, len, algorithm));
            return this;
        } catch (InvalidKeyException e) {
            throw hidden(e);
        }
    }

    public final BaseMac init(byte[] input, AlgorithmParameterSpec params) {
        try {
            mac.init(new SecretKeySpec(input, algorithm), params);
            return this;
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw hidden(e);
        }
    }

    public final BaseMac init(byte[] input) {
        try {
            mac.init(new SecretKeySpec(input, algorithm));
            return this;
        } catch (InvalidKeyException e) {
            throw hidden(e);
        }
    }

    public final BaseMac init(String input, Charset charset, AlgorithmParameterSpec params) {
        return init(input.getBytes(charset), params);
    }

    public final BaseMac init(String input, Charset charset) {
        return init(input.getBytes(charset));
    }

    public final BaseMac init(String input, AlgorithmParameterSpec params) {
        return init(input.getBytes(), params);
    }

    public final BaseMac init(String input) {
        return init(input.getBytes());
    }

    public final BaseMac update(byte[] input, int offset, int len) {
        mac.update(input, offset, len);
        return this;
    }

    public final BaseMac update(byte[] input) {
        mac.update(input);
        return this;
    }

    public final BaseMac update(byte input) {
        mac.update(input);
        return this;
    }

    public final BaseMac update(ByteBuffer input) {
        mac.update(input);
        return this;
    }

    public final BaseMac doFinal(byte[] output, int outOffset) {
        try {
            mac.doFinal(output, outOffset);
            return this;
        } catch (ShortBufferException e) {
            throw hidden(e);
        }
    }

    public final byte[] doFinal(byte[] input) {
        return mac.doFinal(input);
    }

    public final byte[] doFinal() {
        return mac.doFinal();
    }

    @Override
    public byte[] digest(byte[] input) {
        return doFinal(input);
    }

    @Override
    public byte[] digest() {
        return doFinal();
    }

    public final String toString() {
        return mac.toString();
    }

    public final void reset() {
        mac.reset();
    }
}