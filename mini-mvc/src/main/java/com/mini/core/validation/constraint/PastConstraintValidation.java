package com.mini.core.validation.constraint;

import com.mini.core.util.Assert;
import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.Validator;
import com.mini.core.validation.annotation.Past;

import javax.inject.Singleton;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;
import static java.util.Calendar.getInstance;

/**
 * 带注解的元素必须是过去的一个瞬间、日期或时间
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code java.util.Date}</li>
 *     <li>{@code java.util.Calendar}</li>
 *     <li>{@code java.time.Instant}</li>
 *     <li>{@code java.time.LocalDate}</li>
 *     <li>{@code java.time.LocalDateTime}</li>
 *     <li>{@code java.time.LocalTime}</li>
 *     <li>{@code java.time.MonthDay}</li>
 *     <li>{@code java.time.OffsetDateTime}</li>
 *     <li>{@code java.time.OffsetTime}</li>
 *     <li>{@code java.time.Year}</li>
 *     <li>{@code java.time.YearMonth}</li>
 *     <li>{@code java.time.ZonedDateTime}</li>
 * </ul>
 * @author xchao
 */
@Singleton
public final class PastConstraintValidation implements ConstraintValidation<Past> {
	@Override
	public final void validate(Past annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof java.util.Date) {
				var v = (java.util.Date) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.util.Calendar) {
				var v = (java.util.Calendar) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.Instant) {
				var v = (java.time.Instant) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.LocalDateTime) {
				var v = (java.time.LocalDateTime) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.LocalDate) {
				var v = (java.time.LocalDate) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.LocalTime) {
				var v = (java.time.LocalTime) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.MonthDay) {
				var v = (java.time.MonthDay) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.OffsetDateTime) {
				var v = (java.time.OffsetDateTime) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.OffsetTime) {
				var v = (java.time.OffsetTime) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.Year) {
				var v = (java.time.Year) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.YearMonth) {
				var v = (java.time.YearMonth) val;
				validate(validator, v, annotation);
				return;
			}
			if (val instanceof java.time.ZonedDateTime) {
				var v = (java.time.ZonedDateTime) val;
				validate(validator, v, annotation);
				return;
			}
			Assert.error("Unsupported type");
		}, () -> validator.is(!annotation.require()));
	}
	
	private static void validate(Validator validator, Date v, Past annotation) {
		validator.is(annotation.present() ? !v.after(new Date()) :
				v.before(new Date()));
	}
	
	private static void validate(Validator validator, Calendar v, Past annotation) {
		validator.is(annotation.present() ? !v.after(getInstance()) :
				v.before(getInstance()));
	}
	
	private static void validate(Validator validator, Instant v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(Instant.now()) :
				v.isBefore(Instant.now()));
	}
	
	private static void validate(Validator validator, LocalDateTime v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(LocalDateTime.now()) :
				v.isBefore(LocalDateTime.now()));
	}
	
	private static void validate(Validator validator, LocalDate v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(LocalDate.now()) :
				v.isBefore(LocalDate.now()));
	}
	
	private static void validate(Validator validator, LocalTime v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(LocalTime.now()) :
				v.isBefore(LocalTime.now()));
	}
	
	private static void validate(Validator validator, MonthDay v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(MonthDay.now()) :
				v.isBefore(MonthDay.now()));
	}
	
	private static void validate(Validator validator, OffsetDateTime v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(OffsetDateTime.now()) :
				v.isBefore(OffsetDateTime.now()));
	}
	
	private static void validate(Validator validator, OffsetTime v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(OffsetTime.now()) :
				v.isBefore(OffsetTime.now()));
	}
	
	private static void validate(Validator validator, Year v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(Year.now()) :
				v.isBefore(Year.now()));
	}
	
	private static void validate(Validator validator, YearMonth v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(YearMonth.now()) :
				v.isBefore(YearMonth.now()));
	}
	
	private static void validate(Validator validator, ZonedDateTime v, Past annotation) {
		validator.is(annotation.present() ? !v.isAfter(ZonedDateTime.now()) :
				v.isBefore(ZonedDateTime.now()));
	}
}
