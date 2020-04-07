package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Min;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;
import static java.lang.Byte.parseByte;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Short.parseShort;

/**
 * 带注解的元素必须是一个大于或者等于指定最小值的数字
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code BigDecimal}</li>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code Long}</li>
 *     <li>{@code Integer}</li>
 *     <li>{@code Short}</li>
 *     <li>{@code Byte}</li>
 *     <li>{@code long}</li>
 *     <li>{@code int}</li>
 *     <li>{@code short}</li>
 *     <li>{@code byte}</li>
 *     <li>{@code Double}</li>
 *     <li>{@code Float}</li>
 *     <li>{@code double}</li>
 *     <li>{@code float}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class MinConstraintValidation implements ConstraintValidation<Min> {
	@Override
	public final void validate(Min annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof BigDecimal) {
				var n = new BigDecimal(annotation.value());
				var v = (BigDecimal) val;
				var r = v.compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof BigInteger) {
				var n = new BigInteger(annotation.value());
				var v = (BigInteger) val;
				var r = v.compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Long) {
				var n = parseLong(annotation.value());
				var r = ((Long) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Integer) {
				var n = parseInt(annotation.value());
				var r = ((Integer) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Short) {
				var n = parseShort(annotation.value());
				var r = ((Short) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Byte) {
				var n = parseByte(annotation.value());
				var r = ((Byte) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Double) {
				var n = parseDouble(annotation.value());
				var r = ((Double) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Float) {
				var n = parseFloat(annotation.value());
				var r = ((Float) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(annotation.require()));
	}
}
