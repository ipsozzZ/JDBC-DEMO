package com.ipso.jdbc.test;

import com.ipso.jdbc.dao.IStudentDao;
import com.ipso.jdbc.dao.impl.StudentDaoImpl;
import com.ipso.jdbc.domain.Student;
import org.junit.Test;

import java.util.List;

public class StudentTest {

   /**
    * 测试方法
    * @author ipso
    */
   @Test
   public void test(){

      // 插入数据
      // this.save();

      // 更新数据
      // this.update();

      // 删除数据
      delete(2);

      // 查询一条数据
      // this.getOne(5);

      // 查询所有记录
      // this.getAll();
   }
   void save(){
      Student student = new Student();
      student.setId(6);
      student.setAge(66);
      student.setName("test1");

      IStudentDao dao = new StudentDaoImpl();
      dao.save(student);
      System.out.println("插入数据成功");
   }

   void update(){
      Student student = new Student();
      int id = 6;
      student.setAge(55);
      student.setName("ipso1");

      IStudentDao dao = new StudentDaoImpl();
      dao.update(id, student);
      System.out.println("更新数据成功");
   }

   void delete(int id){

      IStudentDao dao = new StudentDaoImpl();
      Boolean isDelete = dao.delete(id);
      if (isDelete)
         System.out.println("删除成功");
      else System.out.println("删除失败");
   }

   void getOne(int id){
      IStudentDao dao = new StudentDaoImpl();
      Student student = dao.getOne(id);
      System.out.println(student);
   }

   void getAll(){
      IStudentDao dao = new StudentDaoImpl();
      List<Student>  students = dao.getAll();
      System.out.println(students);
   }
}
