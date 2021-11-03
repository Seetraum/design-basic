package com.test.annotationscan.annotation1;

import com.test.annotationscan.Handle;
import com.test.annotationscan.Event;

@Handle(value = "demo1")
public class Demo1 implements Event {
    private String a = "123";

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    @Override
    public void run() {
        System.out.println("Hello Handle!,I am Demo1......excute method Event.run()");
    }
}
