package com.ipso.jdbc.dao;

import com.ipso.jdbc.domain.Student;

import java.util.List;

public interface IStudentDao {
   void save(Student stu);
   void update(int id, Student stu);
   Boolean delete(int id);
   Student getOne(int id);
   List<Student> getAll();
}
