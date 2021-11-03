package com.test.annotationscan.annotation1;

import com.test.annotationscan.Handle;
import com.test.annotationscan.Event;

@Handle("demo2")
public class Demo2 implements Event {

    @Override
    public void run() {
        System.out.println("Hello Handle!,I am Demo2......excute method Event.run()");
    }
}
