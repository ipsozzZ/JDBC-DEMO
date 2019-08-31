package com.ipso.jdbc.domain;

public class Student {
   Integer id;
   Integer age;
   String name;

   public void setAge(Integer age) {
      this.age = age;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getAge() {
      return age;
   }

   public String getName() {
      return name;
   }

   @Override
   public String toString() {
      return "Student{" + "id=" + id + ", age=" + age + ", name='" + name + '\'' + '}';
   }
}
