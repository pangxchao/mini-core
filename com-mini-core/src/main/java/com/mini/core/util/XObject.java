package com.mini.core.util;

import javax.annotation.Nonnull;

public final class XObject extends XAbstract<Object, XObject> {
	private XObject(Object value) {
		super(value);
	}

	@Nonnull
	@Override
	protected XObject getThis() {
		return this;
	}
}
