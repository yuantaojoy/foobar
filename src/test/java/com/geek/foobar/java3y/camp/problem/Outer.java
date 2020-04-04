package com.geek.foobar.java3y.camp.problem;

/**
 * @author 曲元涛
 * @date 2020/4/3 10:24
 */
public class Outer {

    String string = "";

    void outerTest(final char ch) {
        Object obj = new Object();
        new Inner() {
            void innerTest() {
                System.out.println(string);
                System.out.println(ch);
                System.out.println(obj);
            }
        }.innerTest();
    }

    public static void main(String[] args) {
        new Outer().outerTest('a');
    }

    class Inner {

    }
}
