package com.test.file;

import java.util.Objects;

public record UserResp(Integer id, String name, Integer age) {

  public UserResp(Integer id,String name){
    this(id,name,null);
  }

  public UserResp(Integer id, String name, Integer age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserResp userResp = (UserResp) o;
    return id.equals(userResp.id) && name.equals(userResp.name) && Objects
        .equals(age, userResp.age);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, age);
  }
}
