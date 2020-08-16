package com.test.lambda;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class GroupBy {
  private static List<Person> personList;

  static {
    personList.add(new Person("小张",10));
    personList.add(new Person("小张",11));
    personList.add(new Person("小王",12));
    personList.add(new Person("小王",10));
    personList.add(new Person("小李",13));
    personList.add(new Person("小李",15));
    personList.add(new Person("小曦",15));
    personList.add(new Person("小曦",12));
    personList.add(new Person("小婷",13));
  }

  protected static class Person{
    private String name;
    private int age;

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }

  public static void main(String[] args) {
    personList.stream().collect(groupingBy(Person::getName, counting()));
    Comparator<Person> ageHight = Comparator.comparingInt(Person::getAge);
    personList.stream().collect(groupingBy(Person::getName, reducing(BinaryOperator.maxBy(ageHight))));
    personList.stream().collect(groupingBy(Person::getName, summarizingInt(Person::getAge)));
    personList.stream().collect(groupingBy(Person::getName, mapping(Person::getAge,toList())));
  }
}
