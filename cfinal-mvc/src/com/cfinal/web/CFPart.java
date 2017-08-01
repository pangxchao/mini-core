/**
 * Created the com.cfinal.web.CFPart.java
 * @created 2016年11月14日 上午10:05:11
 * @version 1.0.0
 */
package com.cfinal.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.Part;

/**
 * com.cfinal.web.CFPart.java
 * @author XChao
 */
public class CFPart implements Part {
	private Part part;

	public CFPart(Part part) {
		this.part = part;
	}

	@Override
	public void delete() throws IOException {
		this.part.delete();
	}

	@Override
	public String getContentType() {
		return this.part.getContentType();
	}

	@Override
	public String getHeader(String arg0) {
		return this.part.getHeader(arg0);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return this.part.getHeaderNames();
	}

	@Override
	public Collection<String> getHeaders(String arg0) {
		return this.part.getHeaders(arg0);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.part.getInputStream();
	}

	@Override
	public String getName() {
		return this.part.getName();
	}

	@Override
	public long getSize() {
		return this.part.getSize();
	}

	@Override
	public String getSubmittedFileName() {
		return this.part.getSubmittedFileName();
	}

	@Override
	public void write(String arg0) throws IOException {
		this.part.write(arg0);
	}

	/**
	 * 获取文件后缀名 带.
	 * @return
	 */
	public String getSuffix() {
		return this.getSubmittedFileName().substring(Math.max(this.getSubmittedFileName().lastIndexOf("."), 0));
	}
}
