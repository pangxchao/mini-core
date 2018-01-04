/**
 * Created the com.cfinal.web.model.CFModel.java
 * @created 2016年9月24日 下午7:43:28
 * @version 1.0.0
 */
package sn.mini.web.model;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 视图和数据类
 * @author XChao
 */
public interface IModel {
	public static final String MODEL_KEY = "SN_REQUEST_IMODEL_KEY";

	/**
	 * 设置返回错误码
	 * @param error
	 */
	public IModel setError(int error);

	/**
	 * 获取返回错误码
	 * @return
	 */
	public int getError();

	/**
	 * 设置返回错误信息
	 * @param message
	 */
	public IModel setMessage(String message);

	/**
	 * 获取返回错误信息
	 * @return
	 */
	public String getMessage();

	/**
	 * 设置返回ContentType<br/>
	 * 一般用于文件下载
	 * @param contentType
	 * @return
	 */
	public IModel setContentType(String contentType);

	/**
	 * 获取返回ContentType<br/>
	 * 一般用于文件下载
	 * @return
	 */
	public String getContentType();

	/**
	 * 设置下载文件名称
	 * @param fileName
	 * @return
	 */
	public IModel setFileName(String fileName);

	/**
	 * 获取下载文件名称
	 * @return
	 */
	public String getFileName();

	/**
	 * 设置以View.stream 返回数据时的输入流
	 * @param input
	 */
	public IModel setInputStream(InputStream input);

	/**
	 * 获取以View.stream 返回数据时的输入流
	 * @return
	 */
	public InputStream getInputStream();

	/**
	 * 设置文件下载大小
	 * @param contentLength
	 * @return
	 */
	public IModel setContentLength(long contentLength);

	/**
	 * 获取文件下载大小
	 * @return
	 */
	public long getContentLength();

	/**
	 * 获取 model 中的data 字段数据
	 * @return
	 */
	public IModelData<?> getModelData();

	/**
	 * 设置 model中的data字段数据, 数据类型为Map类型
	 * @param data
	 * @return
	 */
	public <T extends Map<? extends String, ? extends Object>> IModel setData(T data);

	/**
	 * 设置 model中的data字段数据, 数据类型为List类型
	 * @param data
	 * @return
	 */
	public <T extends List<? extends Object>> IModel setData(T data);

	/**
	 * 设置 model中的data字段数据, 数据类型为普通实体类型<br/>
	 * 该数据类型时,无法调用 addData, getData 和迭代器方法
	 * @param data
	 * @return
	 */
	public <T> IModel setData(T data, Class<T> clazz);

	/**
	 * 添加一个 data 数据中以map为数据类型的数据,追加一个map的key和value
	 * @param key
	 * @param value
	 */
	public IModel addData(String key, Object value);

	/**
	 * 添加一个 data数据中以list为数据类型的数据, 追加到list后台
	 * @param value
	 * @return
	 */
	public IModel addData(Object value);

	/**
	 * 获取data为map时的其中一项数据, 以map的key获取
	 * @param key
	 * @return
	 */
	public Object getData(String key);

	/**
	 * 获取data为list 时的其中一项数据, 以list下标获取
	 * @param index
	 * @return
	 */
	public Object getData(int index);

	/**
	 * data为map时的迭代器
	 * @return
	 */
	public Set<? extends String> keySet();

	/**
	 * data为list的迭代器
	 * @return
	 */
	public List<? extends Object> valSet();

	/**
	 * 字符串的非空验证
	 * @param String
	 * @return true: 验证通过, false: 验证不通过
	 */
	public boolean validateBlank(String String);

	/**
	 * 字符串非空验证, 该方法在验证不通过时, 会抛出XValidateException, 系统已经处理该异常无需自己处理
	 * @param string
	 * @param message 提示消息字符串
	 */
	public void validateBlank(String string, int error, String message);

	/**
	 * 对象非空验证
	 * @param object
	 * @return
	 */
	public boolean validateNull(Object object);

	/**
	 * 对象非空验证, 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param object
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validateNull(Object object, int error, String message);

	/**
	 * 邮箱验证
	 * @param email
	 * @return
	 */
	public boolean validateEmail(String email);

	/**
	 * 邮箱验证, 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param email
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validateEmail(String email, int error, String message);

	/**
	 * 电话号码验证, 固定电话号码, 匹配: (010|02[0-9]{1}|0[3-9]{2,3})?[-]?[0-9]{6,8}
	 * @param phone
	 * @return
	 */
	public boolean validatePhone(String phone);

	/**
	 * 电话号码验证, 固定电话号码, 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param phone
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validatePhone(String phone, int error, String message);

	/**
	 * 手机号码验证, 匹配 1\\d{10}
	 * @param mobile
	 * @return
	 */
	public boolean validateMobile(String mobile);

	/**
	 * 手机号码验证, 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param mobile
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validateMobile(String mobile, int error, String message);

	/**
	 * 手机和固定电话号码验证 ,匹配: (010|02[0-9]{1}|0[3-9]{2,3})?[-]?[0-9]{6,8} 和 1\\d{10} 两个正则表达式其中一个
	 * @param mobilePhone
	 * @return
	 */
	public boolean validateMobilePhone(String mobilePhone);

	/**
	 * 手机和固定电话号码验证, 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param mobilePhone
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validateMobilePhone(String mobilePhone, int error, String message);

	/**
	 * 匹配正则表达式: \\w+
	 * @param letter
	 * @return
	 */
	public boolean validateLetter(String letter);

	/**
	 * 匹配正则表达式: \\w+, 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param letter
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validateLetter(String letter, int error, String message);

	/**
	 * 纯数字验证, 匹配: \\d+
	 * @param number
	 * @return
	 */
	public boolean validateNumber(String number);

	/**
	 * 纯数字验证, 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param number
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validateNumber(String number, int error, String message);

	/**
	 * 纯汉字验证, 匹配: [\u4E00-\u9FA5]+
	 * @param chinese
	 * @return
	 */
	public boolean validateChinese(String chinese);

	/**
	 * 纯汉字验证, 匹配: [\u4E00-\u9FA5]+ , 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param chinese
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validateChinese(String chinese, int error, String message);

	/**
	 * 验证身分证 匹配: \\d{15}(\\d{2}[A-Za-z0-9])?
	 * @param card
	 * @return
	 */
	public boolean validateCard(String card);

	/**
	 * 验证身分证 匹配: \\d{15}(\\d{2}[A-Za-z0-9])? , 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param card
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validateCard(String card, int error, String message);

	/**
	 * 自定义正则表达式验证
	 * @param string 验证的值
	 * @param regex 正则表达式
	 * @return
	 */
	public boolean validate(String string, String regex);

	/**
	 * 自定义正则表达式验证, 该方法在验证不通过时, 会抛出RuntimeException, 系统已经处理该异常无需自己处理
	 * @param string 验证的值
	 * @param regex 正则表达式
	 * @param error 返回错误码
	 * @param message 提示消息字符串
	 */
	public void validate(String string, String regex, int error, String message);

	/**
	 * 处理验证结果, 验证未通过时, 终止程序向下执行
	 * @param validate 验证通过状态为true; 未通过状态为false, 终止程序执行
	 * @param error
	 * @param message
	 */
	public void validate(boolean validate, int error, String message);

	/**
	 * 直接终止程序向下执行
	 * @param error
	 * @param message
	 */
	public void validate(int error, String message);

}
