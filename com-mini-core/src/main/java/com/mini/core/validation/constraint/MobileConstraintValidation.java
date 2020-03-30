package com.mini.core.validation.constraint;

import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Mobile;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.util.StringUtil.isMobile;
import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须符合手机号码格式
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class MobileConstraintValidation implements ConstraintValidation<Mobile> {
	@Override
	public final void validate(Mobile annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof String) {
				String v = (String) val;
				validator.is(isMobile(v));
			}
		}, () -> validator.is(annotation.require()));
	}
}
