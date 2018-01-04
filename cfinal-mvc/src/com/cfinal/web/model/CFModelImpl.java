/**
 * Created the com.cfinal.web.model.CFModelImpl.java
 * @created 2016年9月28日 下午2:47:33
 * @version 1.0.0
 */
package com.cfinal.web.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.cfinal.web.http.CFHttpServletRequest;

/**
 * com.cfinal.web.model.CFModelImpl.java
 * @author XChao
 */
public class CFModelImpl implements CFModel {
	// *: 0 次或者多次, +: 一次或者多次, ?: 0次或者1次, {n}:刚刚n次, {n,m}: n到m次
	// \d: [0-9], \D: [^0-9], \w:[a-zA-Z_0-9], \W:[^a-zA-Z_0-9], \s: [\t\n\r\f], \S: [^\t\n\r\f]
	private static final String EMAIL = "\\w+[@]\\w+[.]\\w+";
	private static final String PHONE = "(010|02[0-9]{1}|0[3-9]{2,3})?[-]?[0-9]{6,8}";
	private static final String MOBILE = "1\\d{10}";
	private static final String NUMBER = "\\d+";
	private static final String LETTER = "\\w+";
	private static final String CHINESE = "[\u4E00-\u9FA5]+";
	private static final String IDCARD = "\\d{15}(\\d{2}[A-Za-z0-9])?";

	private static class ObjectDataModel implements DataModel<Map<? extends String, ? extends Object>> {
		private int error = 0;
		private String message = "";
		private Map<String, Object> data = new HashMap<String, Object>();

		public int getError() {
			return this.error;
		}

		public ObjectDataModel setError(int error) {
			this.error = error;
			return this;
		}

		public String getMessage() {
			return this.message;
		}

		public ObjectDataModel setMessage(String message) {
			this.message = message;
			return this;
		}

		public Map<? extends String, ? extends Object> getData() {
			return this.data;
		}

		public void setDate(Map<? extends String, ? extends Object> data) {
			this.putAll(data);
		}

		public void put(String key, Object value) {
			this.data.put(key, value);
		}

		public void putAll(Map<? extends String, ? extends Object> data) {
			this.data.putAll(data);
		}

		public Object getValue(String key) {
			return this.data.get(key);
		}

		public Set<? extends String> keySet() {
			return this.data.keySet();
		}

		public void add(Object value) {
			throw new RuntimeException("Object cannot use Array method. ");
		}

		public void addAll(List<? extends Object> data) {
			throw new RuntimeException("Object cannot use Array method. ");
		}

		public Object getValue(int index) {
			throw new RuntimeException("Object cannot use Array method. ");
		}

		public List<? extends Object> objSet() {
			throw new RuntimeException("Object cannot use Array method. ");
		}
	};

	private static class ArrayDataModel implements DataModel<List<? extends Object>> {
		private int error = 0;
		private String message = "";
		private List<Object> data = new ArrayList<Object>();

		public int getError() {
			return this.error;
		}

		public ArrayDataModel setError(int error) {
			this.error = error;
			return this;
		}

		public String getMessage() {
			return this.message;
		}

		public ArrayDataModel setMessage(String message) {
			this.message = message;
			return this;
		}

		public List<? extends Object> getData() {
			return this.data;
		}

		public void setDate(List<? extends Object> data) {
			this.addAll(data);
		}

		public void put(String key, Object value) {
			throw new RuntimeException("Array cannot use Object method. ");
		}

		public void putAll(Map<? extends String, ? extends Object> data) {
			throw new RuntimeException("Array cannot use Object method. ");
		}

		public Object getValue(String key) {
			throw new RuntimeException("Array cannot use Object method. ");
		}

		public Set<? extends String> keySet() {
			throw new RuntimeException("Array cannot use Object method. ");
		}

		public void add(Object value) {
			this.data.add(value);
		}

		public void addAll(List<? extends Object> data) {
			this.data.addAll(data);
		}

		public Object getValue(int index) {
			if(index >= this.data.size()) {
				return null;
			}
			return this.data.get(index);
		}

		public List<? extends Object> objSet() {
			return this.data;
		}
	};

	private static class EntryDataModel implements DataModel<Object> {

		private int error = 0;
		private String message = "";
		private Object data;

		public int getError() {
			return this.error;
		}

		public EntryDataModel setError(int error) {
			this.error = error;
			return this;
		}

		public String getMessage() {
			return this.message;
		}

		public EntryDataModel setMessage(String message) {
			this.message = message;
			return this;
		}

		public Object getData() {
			return this.data;
		}

		public void setDate(Object data) {
			this.data = data;
		}

		public void put(String key, Object value) {
			throw new RuntimeException("Entry cannot use Object method. ");
		}

		public void putAll(Map<? extends String, ? extends Object> data) {
			throw new RuntimeException("Entry cannot use Object method. ");
		}

		public Object getValue(String key) {
			throw new RuntimeException("Entry cannot use Object method. ");
		}

		public Set<? extends String> keySet() {
			throw new RuntimeException("Entry cannot use Object method. ");
		}

		public void add(Object value) {
			throw new RuntimeException("Entry cannot use Array method. ");
		}

		public void addAll(List<? extends Object> data) {
			throw new RuntimeException("Entry cannot use Array method. ");
		}

		public Object getValue(int index) {
			throw new RuntimeException("Entry cannot use Array method. ");
		}

		public List<? extends Object> objSet() {
			throw new RuntimeException("Entry cannot use Array method. ");
		}
	}

	private int error = 0;
	private String message = "";
	private String contentType;
	private String fileName;
	private InputStream inputStream;
	private long contentLength;
	private DataModel<?> dataModel;

	public CFModelImpl(CFHttpServletRequest request) {
		request.setAttribute(CFModel.MODEL_KEY, this);
	}

	public CFModel setError(int error) {
		this.error = error;
		return this;
	}

	public int getError() {
		return this.error;
	}

	public CFModel setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getMessage() {
		return this.message;
	}

	public CFModel setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public String getContentType() {
		return this.contentType;
	}

	public CFModel setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public String getFileName() {
		return this.fileName;
	}

	public CFModel setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		return this;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public CFModel setContentLength(long contentLength) {
		this.contentLength = contentLength;
		return this;
	}

	public long getContentLength() {
		return this.contentLength;
	}

	public DataModel<?> getDataModel() {
		if(this.dataModel == null) {
			this.dataModel = new ObjectDataModel();
		}
		return this.dataModel.setError(this.error).setMessage(this.message);
	}

	public <T extends Map<? extends String, ? extends Object>> CFModel setData(T data) {
		if(this.dataModel == null || !ObjectDataModel.class.isInstance(this.dataModel)) {
			this.dataModel = new ObjectDataModel();
		}
		this.dataModel.putAll(data);
		return this;
	}

	public <T extends List<? extends Object>> CFModel setData(T data) {
		if(this.dataModel == null || !ArrayDataModel.class.isInstance(this.dataModel)) {
			this.dataModel = new ArrayDataModel();
		}
		this.dataModel.addAll(data);
		return this;
	}

	public <T> CFModel setData(T data, Class<T> clazz) {
		if(this.dataModel == null || !EntryDataModel.class.isInstance(this.dataModel)) {
			this.dataModel = new EntryDataModel();
		}
		((EntryDataModel) this.dataModel).setDate(data);
		return this;
	}

	public CFModel addData(String key, Object value) {
		if(this.dataModel == null) {
			this.dataModel = new ObjectDataModel();
		}
		this.dataModel.put(key, value);
		return this;
	}

	public CFModel addData(Object value) {
		if(this.dataModel == null) {
			this.dataModel = new ArrayDataModel();
		}
		this.dataModel.add(value);
		return this;
	}

	public Object getData(String key) {
		if(this.dataModel == null) {
			this.dataModel = new ObjectDataModel();
		}
		return this.dataModel.getValue(key);
	}

	public Object getData(int index) {
		if(this.dataModel == null) {
			this.dataModel = new ArrayDataModel();
		}
		return this.dataModel.getValue(index);
	}

	public Set<? extends String> keySet() {
		if(this.dataModel == null) {
			this.dataModel = new ObjectDataModel();
		}
		return this.dataModel.keySet();
	}

	public List<? extends Object> objSet() {
		if(this.dataModel == null) {
			this.dataModel = new ArrayDataModel();
		}
		return this.dataModel.objSet();
	}

	public boolean validateBlank(String String) {
		return StringUtils.isNotBlank(String);

	}

	public void validateBlank(String string, int error, String message) {
		validate(validateBlank(string), error, message);
	}

	public boolean validateNull(Object object) {
		return object != null;
	}

	public void validateNull(Object object, int error, String message) {
		validate(validateNull(object), error, message);
	}

	public boolean validateEmail(String email) {
		return validate(email, EMAIL);
	}

	public void validateEmail(String email, int error, String message) {
		validate(email, EMAIL, error, message);
	}

	public boolean validatePhone(String phone) {
		return validate(phone, PHONE);
	}

	public void validatePhone(String phone, int error, String message) {
		validate(phone, PHONE, error, message);
	}

	public boolean validateMobile(String mobile) {
		return validate(mobile, MOBILE);
	}

	public void validateMobile(String mobile, int error, String message) {
		validate(mobile, MOBILE, error, message);
	}

	public boolean validateMobilePhone(String mobilePhone) {
		return validate(mobilePhone, PHONE) || validate(mobilePhone, MOBILE);
	}

	public void validateMobilePhone(String mobilePhone, int error, String message) {
		validate(validateMobilePhone(mobilePhone), error, message);
	}

	public boolean validateLetter(String letter) {
		return validate(letter, LETTER);
	}

	public void validateLetter(String letter, int error, String message) {
		validate(letter, LETTER, error, message);
	}

	public boolean validateNumber(String number) {
		return validate(number, NUMBER);
	}

	public void validateNumber(String number, int error, String message) {
		validate(number, NUMBER, error, message);
	}

	public boolean validateChinese(String chinese) {
		return validate(chinese, CHINESE);
	}

	public void validateChinese(String chinese, int error, String message) {
		validate(chinese, CHINESE, error, message);
	}

	public boolean validateCard(String card) {
		return validate(card, IDCARD);
	}

	public void validateCard(String card, int error, String message) {
		validate(card, IDCARD, error, message);
	}

	public boolean validate(String string, String regex) {
		return Pattern.compile(regex).matcher(string).matches();
	}

	public void validate(String string, String regex, int error, String message) {
		validate(validate(string, regex), error, message);
	}

	public void validate(boolean validate, int error, String message) {
		if(validate == false) {
			validate(error, message);
		}
	}

	public void validate(int error, String message) {
		this.setError(error).setMessage(message);
		throw new RuntimeException(message);
	}

}
