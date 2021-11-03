package com.test.script;

public class JavaTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            test();
        }
        System.out.println("java total:" + (System.currentTimeMillis() - start));
    }

    public static double test() {
        long start = System.currentTimeMillis();
        int r = 50000;
        double a = 0.0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                a = a + 3.1415926;
            }
        }
        System.out.println("java method:" + (System.currentTimeMillis() - start));
        return a;
    }
}
