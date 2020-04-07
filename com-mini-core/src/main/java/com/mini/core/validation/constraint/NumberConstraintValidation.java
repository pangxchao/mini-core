package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Number;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.util.StringUtil.isNumber;
import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须只包含数字
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class NumberConstraintValidation implements ConstraintValidation<Number> {
	@Override
	public final void validate(Number annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof String) {
				String v = (String) val;
				validator.is(isNumber(v));
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(annotation.require()));
	}
}
