package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.annotation.Include;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;
import static java.util.Arrays.stream;

/**
 * 带注解的元素必须只包含指定的值
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code CharSequence}</li>
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
public final class IncludeConstraintValidation implements ConstraintValidation<Include> {
	@Override
	public final void validate(Include annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof CharSequence) {
				validator.is(stream(annotation.value())
						.filter(Objects::nonNull)
						.anyMatch(n -> n.equals(val))
				);
				return;
			}
			if (val instanceof BigDecimal) {
				validator.is(stream(annotation.value())
						.filter(Objects::nonNull)
						.map(BigDecimal::new)
						.anyMatch(n -> n.equals(val))
				);
				return;
			}
			if (val instanceof BigInteger) {
				validator.is(stream(annotation.value())
						.filter(Objects::nonNull)
						.map(BigInteger::new)
						.anyMatch(n -> n.equals(val))
				);
				return;
			}
			if (val instanceof java.lang.Long) {
				validator.is(stream(annotation.value())
						.filter(Objects::nonNull)
						.map(Long::parseLong)
						.anyMatch(n -> n.equals(val))
				);
				return;
			}
			if (val instanceof java.lang.Integer) {
				validator.is(stream(annotation.value())
						.filter(Objects::nonNull)
						.map(Integer::parseInt)
						.anyMatch(n -> n.equals(val))
				);
				return;
			}
			if (val instanceof java.lang.Short) {
				validator.is(stream(annotation.value())
						.filter(Objects::nonNull)
						.map(Short::parseShort)
						.anyMatch(n -> n.equals(val))
				);
				return;
			}
			if (val instanceof java.lang.Byte) {
				validator.is(stream(annotation.value())
						.filter(Objects::nonNull)
						.map(Byte::parseByte)
						.anyMatch(n -> n.equals(val))
				);
				return;
			}
			if (val instanceof java.lang.Double) {
				validator.is(stream(annotation.value())
						.filter(Objects::nonNull)
						.map(Double::parseDouble)
						.anyMatch(n -> n.equals(val))
				);
				return;
			}
			if (val instanceof java.lang.Float) {
				validator.is(stream(annotation.value())
						.filter(Objects::nonNull)
						.map(Float::parseFloat)
						.anyMatch(n -> n.equals(val))
				);
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(!annotation.require()));
	}
}
