package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Size;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须在大于或者等于指定最后值并且小于指定最大值的元素个数
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code List}</li>
 *     <li>{@code Map}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class SizeConstraintValidation implements ConstraintValidation<Size> {
	@Override
	public final void validate(Size annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		
		int min = annotation.min(), max = annotation.max();
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof List) {
				var size = ((List<?>) val).size();
				validator.is(min <= size && size <= max);
				return;
			}
			if (val instanceof Map) {
				var size = ((Map<?, ?>) val).size();
				validator.is(min <= size && size <= max);
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(!annotation.require()));
	}
}
