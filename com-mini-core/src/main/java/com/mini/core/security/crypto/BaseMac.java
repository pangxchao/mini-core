package com.mini.core.security.crypto;

import com.mini.core.security.SecurityBase;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.spec.AlgorithmParameterSpec;

import static javax.crypto.Mac.getInstance;

public abstract class BaseMac extends SecurityBase<BaseMac> {
    private final String algorithm;
    private final Mac mac;

    public BaseMac(String algorithm) throws Exception {
        this.mac       = getInstance(algorithm);
        this.algorithm = algorithm;
    }

    public final BaseMac init(byte[] input, int offset, int len, AlgorithmParameterSpec params) throws Exception {
        mac.init(new SecretKeySpec(input, offset, len, algorithm), params);
        return this;
    }

    public final BaseMac init(byte[] input, int offset, int len) throws Exception {
        mac.init(new SecretKeySpec(input, offset, len, algorithm));
        return this;
    }

    public final BaseMac init(byte[] input, AlgorithmParameterSpec params) throws Exception {
        mac.init(new SecretKeySpec(input, algorithm), params);
        return this;
    }

    public final BaseMac init(byte[] input) throws Exception {
        mac.init(new SecretKeySpec(input, algorithm));
        return this;
    }

    public final BaseMac init(String input, Charset charset, AlgorithmParameterSpec params) throws Exception {
        return init(input.getBytes(charset), params);
    }

    public final BaseMac init(String input, Charset charset) throws Exception {
        return init(input.getBytes(charset));
    }

    public final BaseMac init(String input, AlgorithmParameterSpec params) throws Exception {
        return init(input.getBytes(), params);
    }

    public final BaseMac init(String input) throws Exception {
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

    public final BaseMac doFinal(byte[] output, int outOffset) throws Exception {
        mac.doFinal(output, outOffset);
        return this;
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
