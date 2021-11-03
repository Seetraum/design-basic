package com.test.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * parallelStream 并行流处理  异步操作  无序
 * */
public class Parallel {

  public static void main(String[] args) {
    List<Integer> listOfIntegers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
    Comparator<Integer> normal = Integer::compare;
    Comparator<Integer> reversed = normal.reversed();
    Collections.sort(listOfIntegers,reversed);
    listOfIntegers
        .stream()
        .forEach(e -> System.out.print(e + " "));
    System.out.println("");

    System.out.println("Parallel stream");
    listOfIntegers
        .parallelStream()
        .forEach(e -> System.out.print(e + " "));
    System.out.println("");

    System.out.println("Another parallel stream:");
    listOfIntegers
        .parallelStream()
        .forEach(e -> System.out.print(e + " "));
    System.out.println("");

    System.out.println("With forEachOrdered:");
    //并行流顺序输出
    listOfIntegers
        .parallelStream()
        .forEachOrdered(e -> System.out.print(e + " "));
    System.out.println("");
  }
}
