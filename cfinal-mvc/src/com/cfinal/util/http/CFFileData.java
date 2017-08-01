/**
 * Created the com.cfinal.util.http.CFFileData.java
 * @created 2017年6月21日 下午3:48:24
 * @version 1.0.0
 */
package com.cfinal.util.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * com.cfinal.util.http.java
 * @author XChao
 */
public class CFFileData {

	private String fileName;
	private long fileLength;
	private String contentType;
	private InputStream inputStream;

	public CFFileData() {
	}

	public CFFileData(File file) {
		try {
			this.fileName = file.getName();
			this.fileLength = file.length();
			this.contentType = URLConnection.guessContentTypeFromName(file.getAbsolutePath());
			this.inputStream = new FileInputStream(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileLength
	 */
	public long getFileLength() {
		return fileLength;
	}

	/**
	 * @param fileLength the fileLength to set
	 */
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
