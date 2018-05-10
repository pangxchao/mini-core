/**
 * Created the com.cfinal.util.http.CFFormData.java
 * @created 2017年6月21日 下午3:43:40
 * @version 1.0.0
 */
package sn.mini.java.util.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * com.cfinal.util.http.CFFormData.java
 * @author XChao
 */
public class FormData {
	// 存储文本参数的对象
	private final Map<String, List<String>> text = new HashMap<>();
	// 存储文件参数对象
	private final Map<String, List<FileData>> file = new HashMap<>();

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
	public synchronized FormData addText(String name, String value) {
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
	 * 添加一个文本参数
	 * @param name
	 * @param value
	 * @return
	 */
	public synchronized FormData addFile(String name, String fileName, long fileLength, String contentType,
		InputStream inputStream) {
		List<FileData> values = this.file.get(name);//
		FileData value = new FileData(fileName, fileLength, contentType, inputStream);
		if(values == null) {
			values = new ArrayList<>();
			this.file.put(name, values);
		}
		values.add(value);
		return this;
	}

	public int getFileCount(String name) {
		return this.file.get(name).size();
	}

	public String getFileName(String name, int index) {
		return this.file.get(name).get(index).getFileName();
	}

	public long getFileLength(String name, int index) {
		return this.file.get(name).get(index).getFileLength();
	}

	public String getFileContentType(String name, int index) {
		return this.file.get(name).get(index).getContentType();
	}

	public InputStream getFileInputStream(String name, int index) {
		return this.file.get(name).get(index).getInputStream();
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
	public FormData setContentBody(String contentBody) {
		this.contentBody = contentBody;
		return this;
	}

	private static class FileData {
		private String fileName;
		private long fileLength;
		private String contentType;
		private InputStream inputStream;

		public FileData(String name, long length, String type, InputStream input) {
			this.fileName = name;
			this.fileLength = length;
			this.contentType = type;
			this.inputStream = input;
		}

		/**
		 * @return the fileName
		 */
		public String getFileName() {
			return fileName;
		}

		/**
		 * @return the fileLength
		 */
		public long getFileLength() {
			return fileLength;
		}

		/**
		 * @return the contentType
		 */
		public String getContentType() {
			return contentType;
		}

		/**
		 * @return the inputStream
		 */
		public InputStream getInputStream() {
			return inputStream;
		}

	}
}
