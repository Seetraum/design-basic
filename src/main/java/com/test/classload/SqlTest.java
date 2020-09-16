package com.test.classload;

import com.test.classload.model.Person;

public class SqlTest {

  public static void main(String[] args) {
    Person p1 = new  Person();
    p1.setGender("男");
    p1.setName("张三");
    p1.setAge(30);
    Person p2 = new  Person();
    p2.setGender("女");
    p2.setName("陈红");
    p2.setAge(20);
    Person p3 = new  Person();
    p3.setAge(30);
    System.out.println(GeneratorSql.query(p1));
    System.out.println(GeneratorSql.query(p2));
    System.out.println(GeneratorSql.query(p3));
  }
}
