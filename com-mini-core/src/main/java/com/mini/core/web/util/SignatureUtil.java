package com.mini.core.web.util;

import java.util.EventListener;

@SuppressWarnings("unused")
public final class SignatureUtil implements EventListener {
	// 所有参与签名的自定义头和未强制签名的头信息
	private static final String X_Signature_headers = "X-Signature-Headers";
	// 签名后放入该头信息，用以检验签名是否正确
	private static final String X_Signature = "X-Signature";

	// private static final String api_Signature_ENco
	//换行符
	private static final String API_LF = "\n";
}
