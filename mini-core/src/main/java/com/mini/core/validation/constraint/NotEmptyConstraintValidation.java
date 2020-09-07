package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.NotEmpty;

import javax.inject.Singleton;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 带注解的元素不能为空并且长度或者大小必须大于0
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code CharSequence}</li>
 *     <li>{@code Collection}</li>
 *     <li>{@code Array}</li>
 *     <li>{@code Map}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class NotEmptyConstraintValidation implements ConstraintValidation<NotEmpty> {
	@Override
	public final void validate(NotEmpty annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof CharSequence) {
				var v = (CharSequence) val;
				validator.is(isNotEmpty(v));
				return;
			}
			if (val instanceof Collection) {
				var v = (Collection<?>) val;
				validator.is(!v.isEmpty());
				return;
			}
			if (val.getClass().isArray()) {
				var v = Array.getLength(val);
				validator.is(v > 0);
				return;
			}
			if (val instanceof Map) {
				var v = (Map<?, ?>) val;
				validator.is(!v.isEmpty());
				return;
			}
			Assert.error("Unsupported type");
		}, validator::send);
	}
}
