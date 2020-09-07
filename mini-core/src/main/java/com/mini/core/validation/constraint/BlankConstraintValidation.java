package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Blank;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * 带注解的元素为空或者只包含空白字符
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code CharSequence}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class BlankConstraintValidation implements ConstraintValidation<Blank> {
	@Override
	public final void validate(Blank annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresent(val -> {
			if (val instanceof CharSequence) {
				var v = (CharSequence) val;
				validator.is(isBlank(v));
				return;
			}
			Assert.error("Unsupported type");
		});
	}
}
