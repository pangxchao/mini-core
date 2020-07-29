package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.Validator;
import com.mini.core.validation.annotation.Positive;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;

/**
 * 带注解的元素必须是一个有效的正整数
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code Long}</li>
 *     <li>{@code Integer}</li>
 *     <li>{@code Short}</li>
 *     <li>{@code Byte}</li>
 *     <li>{@code long}</li>
 *     <li>{@code int}</li>
 *     <li>{@code short}</li>
 *     <li>{@code byte}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class PositiveConstraintValidation implements ConstraintValidation<Positive> {
	@Override
	public final void validate(Positive annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof BigDecimal) {
				var n = BigDecimal.valueOf(0);
				var v = (BigDecimal) val;
				var r = v.compareTo(n);
				validate(validator, r, annotation);
				return;
			}
			if (val instanceof BigInteger) {
				var n = BigInteger.valueOf(0);
				var v = (BigInteger) val;
				var r = v.compareTo(n);
				validate(validator, r, annotation);
				return;
			}
			if (val instanceof java.lang.Long) {
				var v = (java.lang.Long) val;
				var r = v.compareTo(0L);
				validate(validator, r, annotation);
				return;
			}
			if (val instanceof java.lang.Integer) {
				var v = (java.lang.Integer) val;
				var r = v.compareTo(0);
				validate(validator, r, annotation);
				return;
			}
			if (val instanceof java.lang.Short) {
				var v = (java.lang.Short) val;
				var r = v.compareTo((short) 0);
				validate(validator, r, annotation);
				return;
			}
			if (val instanceof java.lang.Byte) {
				var v = (java.lang.Byte) val;
				var r = v.compareTo((byte) 0);
				validate(validator, r, annotation);
				return;
			}
			if (val instanceof java.lang.Double) {
				var v = (java.lang.Double) val;
				var r = v.compareTo(0D);
				validate(validator, r, annotation);
				return;
			}
			if (val instanceof java.lang.Float) {
				var v = (java.lang.Float) val;
				var r = v.compareTo(0F);
				validate(validator, r, annotation);
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(!annotation.require()));
	}
	
	private static void validate(Validator validator, int v, Positive annotation) {
		validator.is(annotation.zero() ? v >= 0 : v > 0);
	}
}
