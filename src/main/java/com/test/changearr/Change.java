package com.test.changearr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Change {
    public static void main(String[] args) {
        String[] s = {"1","2","3","4"};
        //List<Integer> list = new ArrayList<>();
        //Arrays.stream(s).forEach(s1 -> list.add(Integer.valueOf(s1)));
        System.out.println(Arrays.toString(Arrays.stream(s).mapToInt(Integer::parseInt).toArray()));
    }
}
