package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Prime {
    public static void main(String[] args) {
        System.out.println(new Prime().primeCount(100));
        List<Integer> ls = new ArrayList<>();
        ls.add(1);
        ls.add(2);
        ls.add(3);
        System.out.println(Arrays.binarySearch(ls.toArray(),2));
        System.out.println(Collections.max(ls).toString());
    }

    private Long primeCount(int n){
        return IntStream.range(2,n)
                .parallel()
                .filter(this::isPrime)
                .count();
    }

    private boolean isPrime(int number){
        return IntStream.range(2,number)
                .allMatch(x -> (number%x) != 0);
    }
}
