package com.test.annotationscan.annotation2;

import com.test.annotationscan.Handle;
import com.test.annotationscan.Event;

@Handle("demo3")
public class Demo3 implements Event {

    @Override
    public void run() {
        System.out.println("Hello Handle!,I am Demo3......excute method Event.run()");
    }
}
