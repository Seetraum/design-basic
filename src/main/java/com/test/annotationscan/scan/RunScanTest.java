package com.test.annotationscan.scan;

import com.test.annotationscan.annotation1.ServiceDemo;

import java.lang.reflect.InvocationTargetException;

public class RunScanTest {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        BeanFactory factory = new BeanFactory();
        var packageNames = new String[]{"com.test.annotationscan.annotation1","com.test.annotationscan.annotation2"};
        factory.init(packageNames);
        String result = factory.getBean("Service", ServiceDemo.class).execute("Get name excute end");
        System.out.println(result);

        var arr = new String[]{"demo1","demo2","demo3","demo4","Service"};
        factory.examinerChain(arr).forEach((k,v) -> {
            System.out.println(k);
            v.run();
        });
    }
}
