package com.test.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayInit {
    public static void main(String[] args) {
        var arr = new int[10];
        Arrays.parallelSetAll(arr,i -> i);
        System.out.println(Arrays.toString(arr));
        var list = IntStream.range(1,10).boxed().collect(Collectors.toList());
        System.out.println(Arrays.toString(list.toArray()));
        System.out.println(List.of(8));

        StringBuilder sb = new StringBuilder();
        sb.append("123").append("66");
        System.out.println("sb1 : == " + sb.toString());
        sb.delete(0,sb.length());
        System.out.println("sb2 : ==" + sb.toString());
        for (;;) {
            System.out.println("11111");
        }
    }
}
