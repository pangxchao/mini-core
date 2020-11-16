package com.mini.core.data.builder.support;

import com.mini.core.data.builder.statement.JoinStatement.JoinOnStatement;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface On {
    String column();

    String paramName();

    OnCondition condition() default OnCondition.EQUALS;

    OnJoin join() default OnJoin.ON;

    enum OnJoin {
        ON {
            public void execute(JoinOnStatement jos, On on) {
                super.execute(jos, on);
            }
        },
        AND {
            public void execute(JoinOnStatement jos, On on) {
                jos.AND();
                super.execute(jos, on);
            }
        },
        OR {
            public void execute(JoinOnStatement jos, On on) {
                jos.OR();
                super.execute(jos, on);
            }
        };

        public void execute(JoinOnStatement jos, On on) {
            final String c = on.condition().value;
            jos.ON(on.column() + c + on.paramName());
        }
    }

    enum OnCondition {
        EQUALS(" = "),
        NOT_EQUALS(" <> "),
        GREATER_THAN(" > "),
        LESS_THAN(" < "),
        GREATER_THAN_EQUALS(" >= "),
        LESS_THAN_EQUALS(" <= ");

        private final String value;

        OnCondition(String val) {
            value = val;
        }

        public String getValue() {
            return value;
        }
    }
}
