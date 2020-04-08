package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Length;

import javax.inject.Singleton;
import java.lang.reflect.Array;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;


/**
 * 带注解的元素必须在指定的长度之间
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code CharSequence}</li>
 *     <li>{@code Array}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class LengthConstraintValidation implements ConstraintValidation<Length> {
	@Override
	public final void validate(Length annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		int min = annotation.min(), max = annotation.max();
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val.getClass().isArray()) {
				int length = Array.getLength(val);
				validator.is(min <= length && length <= max);
				return;
			}
			if (val instanceof CharSequence) {
				int length = ((CharSequence) val).length();
				validator.is(min <= length && length <= max);
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(!annotation.require()));
	}
}
