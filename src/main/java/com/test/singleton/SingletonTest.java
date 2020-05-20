package com.test.singleton;

public class SingletonTest {
    public static void main(String[] args) {
        EnumSingleton enumSingleton1 = EnumSingleton.getInstance();
        enumSingleton1.setData("");
        EnumSingleton enumSingleton2 = EnumSingleton.getInstance();
        enumSingleton2.setData("21515");
        System.out.println(enumSingleton1 == enumSingleton2);
    }
}
