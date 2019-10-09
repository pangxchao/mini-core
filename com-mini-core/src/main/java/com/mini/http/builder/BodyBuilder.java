package com.mini.http.builder;

import okhttp3.RequestBody;

public final class BodyBuilder<T> extends AbstractBuilder<T> {
	private RequestBody requestBody;

	@Override
	protected RequestBody getRequestBody() {
		return requestBody;
	}

	public BodyBuilder<T> setRequestBody(RequestBody body) {
		this.requestBody = body;
		return this;
	}
}
