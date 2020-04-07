package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.NotBlank;

import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 带注解的元素不能为空并且必须包含至少一个非空白字符
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class NotBlankConstraintValidation implements ConstraintValidation<NotBlank> {
	@Override
	public final void validate(NotBlank annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof String) {
				String v = (String) val;
				validator.is(isNotBlank(v));
				return;
			}
			Assert.error("Unsupported type");
		}, validator::send);
	}
}
