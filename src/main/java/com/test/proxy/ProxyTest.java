package com.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

/**
 * This program demonstrates the use of proxies
 * */
public class ProxyTest {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];
        for (int i = 0; i < elements.length; i++){
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            Object proxy = Proxy.newProxyInstance(null,new Class[]{Comparable.class},handler);
            elements[i] = proxy;
        }

        //construct random a integer
        Integer key = new Random().nextInt(elements.length) + 1;
        // Search for the key
        int result = Arrays.binarySearch(elements,key);
        //print match id found
        if(result >= 0) System.out.println(elements[result]);
    }
    static class TraceHandler implements InvocationHandler{
        private Object target;
        /**
         * Constructs a TraceHandler
         * @param t the implicit  parameter of the method call
         * */
        public TraceHandler(Object t){
            this.target = t;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //print implicit argument
            System.out.println(target);
            //print method name
            System.out.println("." + method.getName() + "(");
            //print explicit arguments
            if (args != null){
                for (int i = 0; i < args.length; i++) {
                    System.out.println(args[i]);
                    if (i < args.length -1) System.out.println(", ");
                }
            }
            System.out.println(")");
            // invoke actual method
            return method.invoke(target,args);
        }
    }
}
