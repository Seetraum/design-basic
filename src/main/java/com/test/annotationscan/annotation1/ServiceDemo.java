package com.test.annotationscan.annotation1;

import com.test.annotationscan.Handle;

@Handle("Service")
public class ServiceDemo {
    public String execute(String name) {
        return "ServiceDemo: " + name;
    }
}
