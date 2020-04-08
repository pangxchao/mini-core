package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Range;

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
 * 带注解的元素必须是大于或者等于指定的最小值并且小于或者等于指定的最大值之间的数字
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
public final class RangeConstraintValidation implements ConstraintValidation<Range> {
	@Override
	public final void validate(Range annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof BigDecimal) {
				var n = new BigDecimal(annotation.max());
				var v = (BigDecimal) val;
				var r = v.compareTo(n);
				validator.is(r <= 0);
				
				n = new BigDecimal(annotation.min());
				r = v.compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof BigInteger) {
				var n = new BigInteger(annotation.max());
				var v = (BigInteger) val;
				var r = v.compareTo(n);
				validator.is(r <= 0);
				
				n = new BigInteger(annotation.min());
				r = v.compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Long) {
				var n = parseLong(annotation.max());
				var r = ((Long) val).compareTo(n);
				validator.is(r <= 0);
				
				n = parseLong(annotation.min());
				r = ((Long) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Integer) {
				var n = parseInt(annotation.max());
				var r = ((Integer) val).compareTo(n);
				validator.is(r <= 0);
				
				n = parseInt(annotation.min());
				r = ((Integer) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Short) {
				var n = parseShort(annotation.max());
				var r = ((Short) val).compareTo(n);
				validator.is(r <= 0);
				
				n = parseShort(annotation.min());
				r = ((Short) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Byte) {
				var n = parseByte(annotation.max());
				var r = ((Byte) val).compareTo(n);
				validator.is(r <= 0);
				
				n = parseByte(annotation.min());
				r = ((Byte) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Double) {
				var n = parseDouble(annotation.max());
				var r = ((Double) val).compareTo(n);
				validator.is(r <= 0);
				
				n = parseDouble(annotation.min());
				r = ((Double) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			if (val instanceof java.lang.Float) {
				var n = parseFloat(annotation.max());
				var r = ((Float) val).compareTo(n);
				validator.is(r <= 0);
				
				n = parseFloat(annotation.min());
				r = ((Float) val).compareTo(n);
				validator.is(r >= 0);
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(!annotation.require()));
	}
}
