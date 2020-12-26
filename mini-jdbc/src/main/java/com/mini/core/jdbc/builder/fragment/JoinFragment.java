package com.mini.core.jdbc.builder.fragment;

import com.mini.core.jdbc.builder.AbstractSql;
import com.mini.core.jdbc.builder.statement.JoinOnStatement;
import com.mini.core.jdbc.builder.statement.JoinStatement;
import com.mini.core.jdbc.builder.statement.JoinStatement.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public interface JoinFragment<T extends JoinFragment<T>> {

    T join(String join);

    T join(String table, String column, String target);

    T join(String table, Consumer<JoinOnStatement> consumer);

    T innerJoin(String join);

    T innerJoin(String table, String column, String target);

    T innerJoin(String table, Consumer<JoinOnStatement> consumer);

    T leftJoin(String join);

    T leftJoin(String table, String column, String target);

    T leftJoin(String table, Consumer<JoinOnStatement> consumer);

    T rightJoin(String join);

    T rightJoin(String table, String column, String target);

    T rightJoin(String table, Consumer<JoinOnStatement> consumer);

    T leftOuterJoin(String join);

    T leftOuterJoin(String table, String column, String target);

    T leftOuterJoin(String table, Consumer<JoinOnStatement> consumer);

    T rightOuterJoin(String join);

    T rightOuterJoin(String table, String column, String target);

    T rightOuterJoin(String table, Consumer<JoinOnStatement> consumer);

    T crossJoin(String join);

    T crossJoin(String table, String column, String target);

    T crossJoin(String table, Consumer<JoinOnStatement> consumer);


    final class JoinFragmentImpl implements BaseFragment, JoinFragment<JoinFragmentImpl> {
        private final List<JoinStatement> joinList = new ArrayList<>();

        private final AbstractSql<?> sql;

        public JoinFragmentImpl(AbstractSql<?> sql) {
            this.sql = sql;
        }

        @Override
        public JoinFragmentImpl join(String join) {
            var impl = new JoinStatementImpl(sql);
            impl.addValues(join);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl join(String table, String column, String target) {
            var impl = new JoinStatementImpl(sql);
            impl.join(table, column, target);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl join(String table, Consumer<JoinOnStatement> consumer) {
            var impl = new JoinStatementImpl(sql);
            impl.join(table, consumer);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl innerJoin(String join) {
            var impl = new InnerJoinStatementImpl(sql);
            impl.addValues(join);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl innerJoin(String table, String column, String target) {
            var impl = new InnerJoinStatementImpl(sql);
            impl.join(table, column, target);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl innerJoin(String table, Consumer<JoinOnStatement> consumer) {
            var impl = new InnerJoinStatementImpl(sql);
            impl.join(table, consumer);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl leftJoin(String join) {
            var impl = new LeftJoinStatementImpl(sql);
            impl.addValues(join);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl leftJoin(String table, String column, String target) {
            var impl = new LeftJoinStatementImpl(sql);
            impl.join(table, column, target);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl leftJoin(String table, Consumer<JoinOnStatement> consumer) {
            var impl = new LeftJoinStatementImpl(sql);
            impl.join(table, consumer);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl rightJoin(String join) {
            var impl = new RightJoinStatementImpl(sql);
            impl.addValues(join);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl rightJoin(String table, String column, String target) {
            var impl = new RightJoinStatementImpl(sql);
            impl.join(table, column, target);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl rightJoin(String table, Consumer<JoinOnStatement> consumer) {
            var impl = new RightJoinStatementImpl(sql);
            impl.join(table, consumer);
            joinList.add(impl);
            return this;
        }


        @Override
        public JoinFragmentImpl leftOuterJoin(String join) {
            var impl = new LeftOuterJoinStatementImpl(sql);
            impl.addValues(join);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl leftOuterJoin(String table, String column, String target) {
            var impl = new LeftOuterJoinStatementImpl(sql);
            impl.join(table, column, target);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl leftOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
            var impl = new LeftOuterJoinStatementImpl(sql);
            impl.join(table, consumer);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl rightOuterJoin(String join) {
            var impl = new RightOuterJoinStatementImpl(sql);
            impl.addValues(join);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl rightOuterJoin(String table, String column, String target) {
            var impl = new RightOuterJoinStatementImpl(sql);
            impl.join(table, column, target);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl rightOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
            var impl = new RightOuterJoinStatementImpl(sql);
            impl.join(table, consumer);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl crossJoin(String join) {
            var impl = new CrossJoinStatementImpl(sql);
            impl.addValues(join);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl crossJoin(String table, String column, String target) {
            var impl = new CrossJoinStatementImpl(sql);
            impl.join(table, column, target);
            joinList.add(impl);
            return this;
        }

        @Override
        public JoinFragmentImpl crossJoin(String table, Consumer<JoinOnStatement> consumer) {
            var impl = new CrossJoinStatementImpl(sql);
            impl.join(table, consumer);
            joinList.add(impl);
            return this;
        }

        @Override
        public final void builder(StringBuilder builder) {
            joinList.forEach(it -> it.builder(builder));
        }

    }
}
