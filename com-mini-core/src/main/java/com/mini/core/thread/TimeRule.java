package com.mini.core.thread;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public final class TimeRule implements Serializable, EventListener {
	private static final long serialVersionUID = -797416894L;
	private final List<Integer> second = new ArrayList<>();
	private final List<Integer> minute = new ArrayList<>();
	private final List<Integer> month = new ArrayList<>();
	private final List<Integer> hour = new ArrayList<>();
	private final List<Integer> year = new ArrayList<>();
	private final List<Integer> week = new ArrayList<>();
	private final List<Integer> day = new ArrayList<>();

	private TimeRule() {
	}

	public final boolean secondMatch(int value) {
		return second.isEmpty() || second.stream() //
				.anyMatch(v -> v == value);
	}

	public final boolean minuteMatch(int value) {
		return minute.isEmpty() || minute.stream() //
				.anyMatch(v -> v == value);
	}

	public final boolean monthMatch(int value) {
		return month.isEmpty() || month.stream() //
				.anyMatch(v -> v == value);
	}

	public final boolean hourMatch(int value) {
		return hour.isEmpty() || hour.stream() //
				.anyMatch(v -> v == value);
	}

	public final boolean yearMatch(int value) {
		return year.isEmpty() || year.stream() //
				.anyMatch(v -> v == value);
	}

	public final boolean weekMatch(int value) {
		return week.isEmpty() || week.stream() //
				.anyMatch(v -> v == value);
	}

	public final boolean dayMatch(int value) {
		return day.isEmpty() || day.stream() //
				.anyMatch(v -> v == value);
	}

	@Nonnull
	public static Builder builder() {
		TimeRule r = new TimeRule();
		return new Builder(r);
	}

	public static class Builder {
		private final TimeRule rule;

		private Builder(TimeRule rule) {
			this.rule = rule;
		}

		public Builder second(int... values) {
			for (int value : values) {
				rule.second.add(value);
			}
			return this;
		}

		public Builder minute(int... values) {
			for (int value : values) {
				rule.minute.add(value);
			}
			return this;
		}

		public Builder month(int... values) {
			for (int value : values) {
				rule.month.add(value);
			}
			return this;
		}

		public Builder hour(int... values) {
			for (int value : values) {
				rule.hour.add(value);
			}
			return this;
		}

		public Builder year(int... values) {
			for (int value : values) {
				rule.year.add(value);
			}
			return this;
		}

		public Builder week(int... values) {
			for (int value : values) {
				rule.week.add(value);
			}
			return this;
		}

		public Builder day(int... values) {
			for (int value : values) {
				rule.day.add(value);
			}
			return this;
		}

		public TimeRule builder() {
			return rule;
		}
	}
}
