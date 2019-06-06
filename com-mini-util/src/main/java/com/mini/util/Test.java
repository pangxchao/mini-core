package com.mini.util;

import com.mini.util.ioc.ApplicationContext;
import com.mini.util.ioc.ApplicationContextAnnotation;
import com.mini.util.ioc.annotation.Autowired;
import com.mini.util.ioc.annotation.Component;

public class Test {

    public interface A {
        void println_a();
    }

    @Component("aa")
    public static class AA implements A {

        @Autowired
        private C cc;

        /**
         * Sets the value of cc.
         * @param cc The value of cc
         * @return {@Code #this}
         */

        public AA setCc(C cc) {
            this.cc = cc;
            return this;
        }

        @Override
        public void println_a() {
            System.out.println("AA");
            System.out.println(cc);
        }
    }

    public interface B {
        void println_b();
    }

    @Component("bb")
    public static class BB implements B {
        private A aa;

        public BB(A aa) {
            this.aa = aa;
        }

        @Override
        public void println_b() {
            System.out.println("BB");
            System.out.println(aa);
        }
    }

    public interface C {
        void println_c();
    }

    @Component("cc")
    public static class CC implements C {
        private B bb;

        public CC(B bb) {
            this.bb = bb;
        }

        @Override
        public void println_c() {
            System.out.println("CC");
            System.out.println(bb);
        }
    }

    public static void main(String[] args) {
        //for (Class<?> clazz : ClassUtil.scanner("com.mini", null)) {
        //    System.out.println(clazz.getName());
        //}
        String[] packageNames = new String[]{"com.mini.util"};
        ApplicationContext context = new ApplicationContextAnnotation(packageNames);

        A a = context.getBean("aa", A.class);
        a.println_a();

        B b = context.getBean("bb",B.class);
        b.println_b();

        C c = context.getBean("cc", C.class);
        c.println_c();

        System.out.println(context);
    }
}
