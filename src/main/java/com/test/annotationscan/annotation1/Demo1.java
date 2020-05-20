package com.test.annotationscan.annotation1;

import com.test.annotationscan.Handle;
import com.test.annotationscan.Event;

@Handle(value = "demo1")
public class Demo1 implements Event {

    @Override
    public void run() {
        System.out.println("Hello Handle!,I am Demo1......excute method Event.run()");
    }
}
