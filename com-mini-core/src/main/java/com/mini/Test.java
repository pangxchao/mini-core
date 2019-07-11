package com.mini;

import com.mini.util.ClassUtil;
import com.mini.util.reflect.MiniParameter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Test {

    public interface A {
        default void m2(String text3, String txt4){}
    }

   public static class B implements A {

   }

    protected Test(String text5, String txt6) {}

    public static void main(String[] args) throws NoSuchMethodException {
        Method m1 = Test.class.getDeclaredMethod("m1", String.class, String.class);
        MiniParameter[] parameters1 = ClassUtil.getParameter(m1);
        System.out.println(Arrays.toString(parameters1));


        Method m2 = Test.class.getDeclaredMethod("m2", String.class, String.class);
        MiniParameter[] parameters2 = ClassUtil.getParameter(m2);
        System.out.println(Arrays.toString(parameters2));

        Constructor<?> m3 = Test.class.getDeclaredConstructor(String.class, String.class);
        MiniParameter[] parameters3 = ClassUtil.getParameter(m3);
        System.out.println(Arrays.toString(parameters3));
    }
}
