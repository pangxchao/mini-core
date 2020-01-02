package com.mini.core.security.crypto;

import java.util.EventListener;

public final class HmacSHA512 extends BaseMac implements EventListener {
	public HmacSHA512() throws Exception {
		super("HmacSHA512");
	}
}
