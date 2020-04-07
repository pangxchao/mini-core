package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.Validator;
import com.mini.core.validation.annotation.Digits;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须是可接受范围内的数字
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code BigDecimal}</li>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code String}</li>
 *     <li>{@code Byte}</li>
 *     <li>{@code Short}</li>
 *     <li>{@code Integer}</li>
 *     <li>{@code Long}</li>
 *     <li>{@code byte}</li>
 *     <li>{@code short}</li>
 *     <li>{@code int}</li>
 *     <li>{@code long}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class DigitsConstraintValidation implements ConstraintValidation<Digits> {
	@Override
	public final void validate(Digits annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		int f = annotation.fraction(), i = annotation.integer();
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof String) {
				var v = new BigDecimal((String) val);
				validate(validator, v, f, i);
				return;
			}
			if (val instanceof BigDecimal) {
				var v = (BigDecimal) val;
				validate(validator, v, f, i);
				return;
			}
			if (val instanceof BigInteger) {
				var v = (BigInteger) val;
				validate(validator, v, i);
				return;
			}
			if (val instanceof Long) {
				long v = (Long) val;
				validate(validator, v, i);
				return;
			}
			if (val instanceof Integer) {
				var v = (Integer) val;
				validate(validator, v, i);
				return;
			}
			if (val instanceof Short) {
				short v = (Short) val;
				validate(validator, v, i);
				return;
			}
			if (val instanceof Byte) {
				byte v = (Byte) val;
				validate(validator, v, i);
				return;
			}
			if (val instanceof Double) {
				double v = (Double) val;
				var b = BigDecimal.valueOf(v);
				validate(validator, b, f, i);
				return;
			}
			if (val instanceof Float) {
				float v = (Float) val;
				var b = BigDecimal.valueOf(v);
				validate(validator, b, f, i);
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(annotation.require()));
	}
	
	private static void validate(Validator validator, BigDecimal v, int f, int i) {
		validator.is(v.scale() <= f && (v.precision() - v.scale()) <= i);
	}
	
	private static void validate(Validator validator, Number v, int i) {
		validator.is(v.toString().length() <= i);
	}
}
