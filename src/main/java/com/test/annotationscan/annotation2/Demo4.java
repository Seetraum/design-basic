package com.test.annotationscan.annotation2;

import com.test.annotationscan.Handle;
import com.test.annotationscan.Event;

@Handle(value = "demo4")
public class Demo4 implements Event {
    @Override
    public void run() {
        System.out.println("Hello Handle!,I am Demo4......excute method Event.run()");
    }
}
