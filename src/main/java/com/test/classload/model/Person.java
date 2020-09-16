package com.test.classload.model;

import com.test.classload.annotation.Column;
import com.test.classload.annotation.Table;

@Table("person")
public class Person {

  @Column("name")
  private String name;
  @Column("gender")
  private String gender;
  @Column("age")
  private Integer age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
}
