package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Email;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.util.StringUtil.isEmail;
import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须是一个合法的“Email”格式
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class EmailConstraintValidation implements ConstraintValidation<Email> {
	@Override
	public final void validate(Email annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof String) {
				String v = (String) val;
				validator.is(isEmail(v));
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(annotation.require()));
	}
}
