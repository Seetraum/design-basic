package com.test.random;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class RandomTest {

  public static void main(String[] args) {
    RandomGenerator generator = RandomGeneratorFactory.all()
        .filter(RandomGeneratorFactory::isJumpable)
        .filter(factory -> factory.stateBits() > 128)
        .findAny()
        .map(RandomGeneratorFactory::create)
        //  if you need a `JumpableGenerator`:
        //  .map(JumpableGenerator.class::cast)
        .orElseThrow();
    System.out.println(generator.nextInt(10));
    Object i = 10;
    if (i instanceof Integer c){
      System.out.println(c);
    }
  }

}
