package com.mini.core.jdbc.builder.support;

import com.mini.core.jdbc.builder.statement.JoinOnStatement;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface On {
    String column();

    String target();

    OnJoin join() default OnJoin.AND;

    enum OnJoin {
        AND {
            public void exe(JoinOnStatement st, On on) {
                st.and(on.column(), on.target());
            }
        }, OR {
            public void exe(JoinOnStatement st, On on) {
                st.or(on.column(), on.target());
            }
        };

        public abstract void exe(JoinOnStatement st, On on);
    }
}
