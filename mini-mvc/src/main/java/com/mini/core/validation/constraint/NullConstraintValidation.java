package com.mini.core.validation.constraint;

import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Null;

import javax.inject.Singleton;
import java.util.Objects;

import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须为空
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code Object}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class NullConstraintValidation implements ConstraintValidation<Null> {
	@Override
	public final void validate(Null annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		validator.is(Objects.isNull(value));
	}
}
