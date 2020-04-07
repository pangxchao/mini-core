package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Letter;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.util.StringUtil.isLetter;
import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素只能包含英文字母
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class LetterConstraintValidation implements ConstraintValidation<Letter> {
	@Override
	public final void validate(Letter annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof String) {
				String v = (String) val;
				validator.is(isLetter(v));
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(annotation.require()));
	}
}
