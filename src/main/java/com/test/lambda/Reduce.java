package com.test.lambda;

import java.util.Optional;
import java.util.stream.Stream;

public class Reduce {

  public static void main(String[] args) {
    Optional<Integer> sum = Stream.of(1,2,3).reduce(Integer::sum);
    System.out.println(sum.get());
  }
}
