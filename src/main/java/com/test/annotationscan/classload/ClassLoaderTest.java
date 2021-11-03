package com.test.annotationscan.classload;

import com.test.annotationscan.annotation1.Demo1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        String classPath = "com.test.annotationscan.annotation1.Demo1";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class c = classLoader.loadClass(classPath);
        Field[] fields = c.getDeclaredFields();
        String t = fields[0].getType().toString();
        String name = fields[0].getName();
        Method method = c.getMethod("get" + getMethodName(fields[0].getName()));
        System.out.println(method.invoke(new Demo1()));
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
