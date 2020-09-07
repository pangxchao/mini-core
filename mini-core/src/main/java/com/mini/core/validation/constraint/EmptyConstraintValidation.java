package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Empty;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * 带注解的元素必须为空或者长度为0
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code CharSequence}</li>
 *     <li>{@code Collection}</li>
 *     <li>{@code Map}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class EmptyConstraintValidation implements ConstraintValidation<Empty> {
	@Override
	public final void validate(Empty annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresent(val -> {
			if (val instanceof CharSequence) {
				var v = (CharSequence) val;
				validator.is(isEmpty(v));
				return;
			}
			if (val instanceof Collection) {
				var v = (Collection<?>) val;
				validator.is(v.isEmpty());
				return;
			}
			if (val instanceof Map) {
				var v = (Map<?, ?>) val;
				validator.is(v.isEmpty());
				return;
			}
			Assert.error("Unsupported type");
		});
	}
}
