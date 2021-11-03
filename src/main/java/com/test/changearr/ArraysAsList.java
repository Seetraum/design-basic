package com.test.changearr;

import java.util.Arrays;
import java.util.List;

public class ArraysAsList {
    public static void main(String[] args) {
        String[] strings = new String[3];
        strings[0] = "0";
        strings[1] = "1";
        strings[2] = "2";
        List<String> stringList = Arrays.asList(strings);
        //修改转换后的集合，成功把第一个元素“0”改为“oneList”
        stringList.set(0 , "oneList");
        //数组strings第一个元素随之改变
        System.out.println(strings[0]);
        //以下编译正确 但都会抛出运行时异常
        stringList.add("3");
        stringList.remove(2);
        stringList.clear();
    }
}
