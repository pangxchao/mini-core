package com.mini.util.lang;

public interface Function {

    interface F0 {
        void apply();
    }

    interface F1<T> {
        void apply(T t);
    }

    interface F2<T1, T2> {
        void apply(T1 t1, T2 t2);
    }

    interface F3<T1, T2, T3> {
        void apply(T1 t1, T2 t2, T3 t3);
    }

    interface F4<T1, T2, T3, T4> {
        void apply(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    interface FR0<R> {
        R apply();
    }

    interface FR1<R, T> {
        R apply(T t);
    }

    interface FR2<R, T1, T2> {
        R apply(T1 t1, T2 t2);
    }

    interface FR3<R, T1, T2, T3> {
        R apply(T1 t1, T2 t2, T3 t3);
    }

    interface FR4<R, T1, T2, T3, T4> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4);
    }
}
