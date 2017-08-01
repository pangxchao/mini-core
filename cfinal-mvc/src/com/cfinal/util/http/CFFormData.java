/**
 * Created the com.cfinal.util.http.CFFormData.java
 * @created 2017年6月21日 下午3:43:40
 * @version 1.0.0
 */
package com.cfinal.util.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * com.cfinal.util.http.CFFormData.java
 * @author XChao
 */
public class CFFormData {
	// 存储文本参数的对象
	private final Map<String, List<String>> text = new HashMap<>();
	// 存储文件参数对象
	private final Map<String, List<CFFileData>> file = new HashMap<>();

	// 相信纯文本数据
	private String contentBody = null;

	/**
	 * 文本参数KeySet
	 * @return
	 */
	public Set<String> textKeySet() {
		return this.text.keySet();
	}

	/**
	 * 获取文本参数对象
	 * @param name
	 * @return
	 */
	public List<String> getText(String name) {
		return this.text.get(name);
	}

	/**
	 * 添加一个文本参数
	 * @param name
	 * @param value
	 * @return
	 */
	public synchronized CFFormData addText(String name, String value) {
		List<String> values = this.text.get(name);
		if(values == null) {
			values = new ArrayList<>();
			this.text.put(name, values);
		}
		values.add(value);
		return this;
	}

	/**
	 * 文件参数KeySet
	 * @return
	 */
	public Set<String> fileKeySet() {
		return this.file.keySet();
	}

	/**
	 * 获取文件参数对象
	 * @param name
	 * @return
	 */
	public List<CFFileData> getFile(String name) {
		return this.file.get(name);
	}

	/**
	 * 添加一个文本参数
	 * @param name
	 * @param value
	 * @return
	 */
	public synchronized CFFormData addFile(String name, CFFileData value) {
		List<CFFileData> values = this.file.get(name);
		if(values == null) {
			values = new ArrayList<>();
			this.file.put(name, values);
		}
		values.add(value);
		return this;
	}

	/**
	 * @return the contentBody
	 */
	public String getContentBody() {
		return contentBody;
	}

	/**
	 * @param contentBody the contentBody to set
	 */
	public CFFormData setContentBody(String contentBody) {
		this.contentBody = contentBody;
		return this;
	}
}
