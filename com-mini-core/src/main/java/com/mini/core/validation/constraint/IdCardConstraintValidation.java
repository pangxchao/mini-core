package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.IdCard;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.util.StringUtil.isIdCard;
import static com.mini.core.validation.Validator.status;


/**
 * 带注解的元素必须符合中国身份证号码格式
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class IdCardConstraintValidation implements ConstraintValidation<IdCard> {
	@Override
	public final void validate(IdCard annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (value instanceof String) {
				String v = (String) val;
				validator.is(isIdCard(v));
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(annotation.require()));
	}
}
