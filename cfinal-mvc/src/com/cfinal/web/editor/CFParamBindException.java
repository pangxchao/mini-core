/**
 * Created the com.cfinal.web.editor.CFParamBindException.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

/**
 * com.cfinal.web.editor.CFParamBindException.java
 * @author XChao
 */
public class CFParamBindException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CFParamBindException(String message, String paramName) {
		this(message, null, paramName);
	}

	public CFParamBindException(Exception e, String paramName) {
		this(e.getMessage(), e, paramName);
	}

	public CFParamBindException(String message, Exception e, String paramName) {
		super("[" + paramName + "]" + message, e);
	}

	public CFParamBindException(Exception e) {
		super(e);
	}
}
