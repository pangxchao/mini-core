package com.mini.core.security.digest;

import com.mini.core.security.SecurityBase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.mini.core.util.ThrowsUtil.hidden;
import static java.security.MessageDigest.getInstance;

/**
 * DigestUtil.java
 * @author xchao
 */
public abstract class BaseDigest extends SecurityBase<BaseDigest> {
	private final MessageDigest digest;
	
	protected BaseDigest(MessageDigest digest) {
		this.digest = digest;
	}
	
	protected BaseDigest(String algorithm) {
		try {
			digest = getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw hidden(e);
		}
		
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
