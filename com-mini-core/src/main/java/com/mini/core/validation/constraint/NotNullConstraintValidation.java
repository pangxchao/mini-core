package com.mini.core.validation.constraint;

import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.NotNull;

import javax.inject.Singleton;
import java.util.Objects;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素不能为空
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code Object}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class NotNullConstraintValidation implements ConstraintValidation<NotNull> {
	@Override
	public final void validate(NotNull annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		validator.is(Objects.nonNull(value));
	}
}
