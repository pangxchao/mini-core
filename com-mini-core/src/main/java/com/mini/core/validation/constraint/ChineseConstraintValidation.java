package com.mini.core.validation.constraint;

import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Chinese;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.util.StringUtil.isChinese;
import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须只包含中文字符
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class ChineseConstraintValidation implements ConstraintValidation<Chinese> {
	@Override
	public final void validate(Chinese annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof String) {
				String v = (String) val;
				validator.is(isChinese(v));
			}
		}, () -> validator.is(annotation.require()));
	}
}
