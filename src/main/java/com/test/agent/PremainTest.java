package com.test.agent;

import java.lang.instrument.Instrumentation;

public class PremainTest {
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("premain start....");
        System.out.println(agentArgs);
    };
}
