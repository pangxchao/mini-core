package com.mini.util.lang;

public interface Function {

	@FunctionalInterface
	interface F0 {
		void apply();
	}

	@FunctionalInterface
	interface F1<T> {
		void apply(T t);
	}

	@FunctionalInterface
	interface F2<T1, T2> {
		void apply(T1 t1, T2 t2);
	}

	@FunctionalInterface
	interface F3<T1, T2, T3> {
		void apply(T1 t1, T2 t2, T3 t3);
	}

	@FunctionalInterface
	interface F4<T1, T2, T3, T4> {
		void apply(T1 t1, T2 t2, T3 t3, T4 t4);
	}

	@FunctionalInterface
	interface F5<T1, T2, T3, T4, T5> {
		void apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
	}

	@FunctionalInterface
	interface F6<T1, T2, T3, T4, T5, T6> {
		void apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
	}

	@FunctionalInterface
	interface F7<T1, T2, T3, T4, T5, T6, T7> {
		void apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
	}

	@FunctionalInterface
	interface F8<T1, T2, T3, T4, T5, T6, T7, T8> {
		void apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
	}

	@FunctionalInterface
	interface F9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
		void apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9);
	}

	@FunctionalInterface
	interface FR0<R> {
		R apply();
	}

	@FunctionalInterface
	interface FR1<R, T> {
		R apply(T t);
	}

	@FunctionalInterface
	interface FR2<R, T1, T2> {
		R apply(T1 t1, T2 t2);
	}

	@FunctionalInterface
	interface FR3<R, T1, T2, T3> {
		R apply(T1 t1, T2 t2, T3 t3);
	}

	@FunctionalInterface
	interface FR4<R, T1, T2, T3, T4> {
		R apply(T1 t1, T2 t2, T3 t3, T4 t4);
	}

	@FunctionalInterface
	interface FR5<R, T1, T2, T3, T4, T5> {
		R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
	}

	@FunctionalInterface
	interface FR6<R, T1, T2, T3, T4, T5, T6> {
		R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
	}

	@FunctionalInterface
	interface FR7<R, T1, T2, T3, T4, T5, T6, T7> {
		R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
	}

	@FunctionalInterface
	interface FR8<R, T1, T2, T3, T4, T5, T6, T7, T8> {
		R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
	}

	@FunctionalInterface
	interface FR9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> {
		R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9);
	}
}
