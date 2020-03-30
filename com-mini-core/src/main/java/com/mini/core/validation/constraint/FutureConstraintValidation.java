package com.mini.core.validation.constraint;

import com.mini.core.validation.ConstraintValidation;
import com.mini.core.validation.Validator;
import com.mini.core.validation.annotation.Future;

import javax.inject.Singleton;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static com.mini.core.validation.Validator.status;
import static java.util.Calendar.getInstance;

/**
 * 带注解的元素必须是将来的一个瞬间、日期或时间
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
public final class FutureConstraintValidation implements ConstraintValidation<Future> {
	@Override
	public final void validate(Future annotation, Object value) {
		var validator = status(annotation.error());
		validator.message(annotation.message());
		// 数据验证
		Optional.ofNullable(value).ifPresentOrElse(val -> {
			if (val instanceof java.util.Date) {
				var v = (java.util.Date) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.util.Calendar) {
				var v = (java.util.Calendar) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.Instant) {
				var v = (java.time.Instant) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.LocalDateTime) {
				var v = (java.time.LocalDateTime) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.LocalDate) {
				var v = (java.time.LocalDate) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.LocalTime) {
				var v = (java.time.LocalTime) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.MonthDay) {
				var v = (java.time.MonthDay) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.OffsetDateTime) {
				var v = (java.time.OffsetDateTime) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.OffsetTime) {
				var v = (java.time.OffsetTime) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.Year) {
				var v = (java.time.Year) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.YearMonth) {
				var v = (java.time.YearMonth) val;
				validate(validator, v, annotation);
			}
			if (val instanceof java.time.ZonedDateTime) {
				var v = (java.time.ZonedDateTime) val;
				validate(validator, v, annotation);
			}
		}, () -> validator.is(annotation.require()));
	}
	
	private static void validate(Validator validator, Date v, Future annotation) {
		validator.is(annotation.present() ? !v.before(new Date()) :
				v.after(new Date()));
	}
	
	private static void validate(Validator validator, Calendar v, Future annotation) {
		validator.is(annotation.present() ? !v.before(getInstance()) :
				v.after(getInstance()));
	}
	
	private static void validate(Validator validator, Instant v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(Instant.now()) :
				v.isAfter(Instant.now()));
	}
	
	private static void validate(Validator validator, LocalDateTime v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(LocalDateTime.now()) :
				v.isAfter(LocalDateTime.now()));
	}
	
	private static void validate(Validator validator, LocalDate v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(LocalDate.now()) :
				v.isAfter(LocalDate.now()));
	}
	
	private static void validate(Validator validator, LocalTime v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(LocalTime.now()) :
				v.isAfter(LocalTime.now()));
	}
	
	private static void validate(Validator validator, MonthDay v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(MonthDay.now()) :
				v.isAfter(MonthDay.now()));
	}
	
	private static void validate(Validator validator, OffsetDateTime v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(OffsetDateTime.now()) :
				v.isAfter(OffsetDateTime.now()));
	}
	
	private static void validate(Validator validator, OffsetTime v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(OffsetTime.now()) :
				v.isAfter(OffsetTime.now()));
	}
	
	private static void validate(Validator validator, Year v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(Year.now()) :
				v.isAfter(Year.now()));
	}
	
	private static void validate(Validator validator, YearMonth v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(YearMonth.now()) :
				v.isAfter(YearMonth.now()));
	}
	
	private static void validate(Validator validator, ZonedDateTime v, Future annotation) {
		validator.is(annotation.present() ? !v.isBefore(ZonedDateTime.now()) :
				v.isAfter(ZonedDateTime.now()));
	}
}
