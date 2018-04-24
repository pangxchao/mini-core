/**
 * Created the sn.mini.java.web.model.DefaultModel.java
 * @created 2016年9月28日 下午2:47:33
 * @version 1.0.0
 */
package sn.mini.java.web.model;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sn.mini.java.util.lang.StringUtil;

/**
 * sn.mini.java.web.model.DefaultModel.java
 * @author XChao
 */
public class DefaultModel implements IModel {
	// *: 0 次或者多次, +: 一次或者多次, ?: 0次或者1次, {n}:刚刚n次, {n,m}: n到m次
	// \d: [0-9], \D: [^0-9], \w:[a-zA-Z_0-9], \W:[^a-zA-Z_0-9], \s: [\t\n\r\f], \S: [^\t\n\r\f]
	private static final String EMAIL = "\\S+[@]\\S+[.]\\S+";
	private static final String PHONE = "(010|02[0-9]{1}|0[3-9]{2,3})?[-]?[0-9]{6,8}";
	private static final String MOBILE = "1\\d{10}";
	private static final String NUMBER = "\\d+";
	private static final String LETTER = "\\w+";
	private static final String CHINESE = "[\u4E00-\u9FA5]+";
	private static final String IDCARD = "\\d{15}(\\d{2}[A-Za-z0-9])?";

//	private int error;
//	private String message;
	private String contentType;
	private String fileName;
	private InputStream inputStream;
	private long contentLength;
	private IModelData<?> modelData;

	public DefaultModel() {
		modelData = new ObjectModelData();
	}

	public IModel setError(int error) {
		this.modelData.setError(error);
		return this;
	}

	public int getError() {
		return this.modelData.getError();
	}

	public IModel setMessage(String message) {
		this.modelData.setMessage(message);
		return this;
	}

	public String getMessage() {
		return this.modelData.getMessage();
	}

	public IModel setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public String getContentType() {
		return this.contentType;
	}

	public IModel setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public String getFileName() {
		return this.fileName;
	}

	public IModel setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		return this;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public IModel setContentLength(long contentLength) {
		this.contentLength = contentLength;
		return this;
	}

	public long getContentLength() {
		return this.contentLength;
	}

	public IModelData<?> getModelData() {
		if(this.modelData == null) {
			this.modelData = new ObjectModelData();
		}
		return this.modelData;
	}

	public <T extends Map<? extends String, ? extends Object>> IModel setData(T data) {
		if(this.modelData == null || !ObjectModelData.class.isInstance(this.modelData)) {
			this.modelData = new ObjectModelData();
		}
		if(data != null) {
			this.modelData.putAll(data);
		}
		return this;
	}

	public <T extends List<? extends Object>> IModel setData(T data) {
		if(this.modelData == null || !ArraysModelData.class.isInstance(this.modelData)) {
			this.modelData = new ArraysModelData();
		}
		if(data != null) {
			this.modelData.addAll(data);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> IModel setData(T data, Class<T> clazz) {
		if(this.modelData == null || !EntryModelData.class.isInstance(this.modelData)) {
			this.modelData = new EntryModelData<T>();
		}
		if(data != null) {
			((EntryModelData<T>) this.modelData).setDate(data);
		}
		return this;
	}

	public IModel addData(String key, Object value) {
		if(this.modelData == null) {
			this.modelData = new ObjectModelData();
		}
		this.modelData.put(key, value);
		return this;
	}

	public IModel addData(Object value) {
		if(this.modelData == null) {
			this.modelData = new ArraysModelData();
		}
		this.modelData.add(value);
		return this;
	}

	public Object getData(String key) {
		if(this.modelData == null) {
			this.modelData = new ObjectModelData();
		}
		return this.modelData.getValue(key);
	}

	public Object getData(int index) {
		if(this.modelData == null) {
			this.modelData = new ArraysModelData();
		}
		return this.modelData.getValue(index);
	}

	public Set<? extends String> keySet() {
		if(this.modelData == null) {
			this.modelData = new ObjectModelData();
		}
		return this.modelData.keySet();
	}

	public List<? extends Object> valSet() {
		if(this.modelData == null) {
			this.modelData = new ArraysModelData();
		}
		return this.modelData.valSet();
	}

	public boolean validateBlank(String String) {
		return StringUtil.isNotBlank(String);

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
		return string != null && string.matches(regex);
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
