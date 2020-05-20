package com.test.annotationscan;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface ExaminerProvider {
    Map<String,Event> examinerChain(String... conditions) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
