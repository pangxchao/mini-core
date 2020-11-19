package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.AssertTrue;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须为“True”
 * <br />
 * 支持的数据类型：
 * <ul>
 *     <li>{@code Boolean}</li>
 *     <li>{@code boolean}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class AssertTrueConstraintValidation implements ConstraintValidation<AssertTrue> {
	@Override
	public final void validate(AssertTrue annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof Boolean) {
				Boolean v = (Boolean) val;
				validator.is(v);
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(!annotation.require()));
	}
}
