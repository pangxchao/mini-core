package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Require;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.util.StringUtil.isRequire;
import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须符合正则表达式“[a-z_][a-z0-9_]*”规则
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class RequireConstraintValidation implements ConstraintValidation<Require> {
	@Override
	public final void validate(Require annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof String) {
				String v = (String) val;
				validator.is(isRequire(v));
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(!annotation.require()));
	}
}
