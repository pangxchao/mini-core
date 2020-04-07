package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Pattern;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.util.StringUtil.isPattern;
import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须符合指定的正则表达式
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class PatternConstraintValidation implements ConstraintValidation<Pattern> {
	@Override
	public final void validate(Pattern annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof String) {
				String v = (String) val;
				var r = annotation.regex();
				validator.is(isPattern(v, r));
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(annotation.require()));
	}
}
